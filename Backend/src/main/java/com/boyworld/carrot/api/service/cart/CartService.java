package com.boyworld.carrot.api.service.cart;

import static com.boyworld.carrot.domain.StringConstants.NO_IMG;
import static com.boyworld.carrot.domain.cart.CartType.CART;
import static com.boyworld.carrot.domain.cart.CartType.CARTMENU;
import static com.boyworld.carrot.domain.cart.CartType.CARTMENUOPTION;

import com.boyworld.carrot.api.controller.cart.response.CartOrderResponse;
import com.boyworld.carrot.api.controller.cart.response.CartResponse;
import com.boyworld.carrot.api.service.cart.dto.CartMenuDto;
import com.boyworld.carrot.api.service.cart.dto.CartMenuOptionDto;
import com.boyworld.carrot.api.service.cart.dto.CreateCartMenuDto;
import com.boyworld.carrot.api.service.order.dto.CreateOrderDto;
import com.boyworld.carrot.api.service.order.dto.OrderMenuItem;
import com.boyworld.carrot.domain.cart.Cart;
import com.boyworld.carrot.domain.cart.CartMenu;
import com.boyworld.carrot.domain.cart.CartMenuOption;
import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import com.boyworld.carrot.domain.foodtruck.repository.command.FoodTruckRepository;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
import com.boyworld.carrot.domain.menu.Menu;
import com.boyworld.carrot.domain.menu.MenuImage;
import com.boyworld.carrot.domain.menu.MenuOption;
import com.boyworld.carrot.domain.menu.repository.command.MenuOptionRepository;
import com.boyworld.carrot.domain.menu.repository.command.MenuRepository;
import com.boyworld.carrot.domain.menu.repository.query.MenuImageQueryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {
    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final FoodTruckRepository foodTruckRepository;
    private final MemberRepository memberRepository;
    private final MenuRepository menuRepository;
    private final MenuOptionRepository menuOptionRepository;
    private final MenuImageQueryRepository menuImageQueryRepository;

    public Long createCart(CreateCartMenuDto createCartMenuDto, String email) throws JsonProcessingException {

        FoodTruck foodTruck = getFoodTruckById(createCartMenuDto.getFoodTruckId());

        Menu menu = getMenuById(createCartMenuDto.getMenuId());

        Integer cartMenuTotalPrice = getCartMenuTotalPrice(createCartMenuDto);

        if (checkFieldKey(CART.getText(), email)) {
            // 회원 카트가 존재하는 경우
            log.debug("사용자의 회원 카트가 존재합니다: {}", email);

            Cart cart = getCart(email);
            if (cart.getFoodTruckId().equals(createCartMenuDto.getFoodTruckId())) {
                // 푸드트럭이 같은 경우
                // 장바구니의 총 금액 및 cartMenuIds 업데이트
                log.debug("같은 푸드트럭의 메뉴 입니다!");
                String sameMenuId = checkSameMenu(createCartMenuDto, email);
                // 같은 메뉴가 있을 때 실행

                if(sameMenuId != null) {
                    log.debug("같은 메뉴 및 메뉴옵션 입니다!");
                    CartMenu cartMenu = getCartMenu((sameMenuId));

                    log.debug("before cartTotalPrice: {}, before cartMenuQuantity: {}", cart.getTotalPrice(), cartMenu.getQuantity());
                    cart.incrementCartTotalPrice(cartMenuTotalPrice);
                    cartMenu.incrementCartMenuQuantity(createCartMenuDto.getCartMenuQuantity());
                    log.debug("after cartTotalPrice: {}, after cartMenuQuantity: {}", cart.getTotalPrice(), cartMenu.getQuantity());

                    saveCart(email, cart);
                    saveCartMenu(sameMenuId, cartMenu);
                }
                else {
                    String cartMenuId = saveCartMenuAndMenuOption(createCartMenuDto, email, menu, cartMenuTotalPrice);
                    saveUpdateCart(createCartMenuDto, cartMenuId, cart, email, cartMenuTotalPrice);
                }

                log.debug("장바구니에 메뉴가 추가되었습니다: {}", email);

            } else {
                // 장바구니에 추가한 메뉴의 푸드트럭이 다른 경우
                // 관련 메뉴 및 메뉴옵션 들 삭제하기
                log.debug("다른 푸드트럭의 메뉴 입니다!");
                for (String cartMenuPk : cart.getCartMenuIds()) {
                    CartMenu cartMenu = getCartMenu(cartMenuPk);
                    List<String> cartMenuOptionIds = new ArrayList<>();
                    if (cartMenu.getCartMenuOptionIds() != null) {
                        cartMenuOptionIds = cartMenu.getCartMenuOptionIds();
                    }
                    for (String cartMenuOptionPk : cartMenuOptionIds) {
                        deleteCartMenuOption(cartMenuOptionPk);
                        log.debug("카트메뉴옵션을 삭제했습니다: {}", cartMenuOptionPk);
                    }
                    deleteCartMenu(cartMenuPk);
                    log.debug("카트메뉴를 삭제했습니다: {}", cartMenuPk);
                }
                String cartMenuId = saveCartMenuAndMenuOption(createCartMenuDto, email, menu, cartMenuTotalPrice);
                saveNewCart(createCartMenuDto, cartMenuId, email, foodTruck, cartMenuTotalPrice);
                log.debug("장바구니에 메뉴가 추가되었습니다: {}", email);
            }
        } else {
            // 회원 카트가 존재하지 않으면 카트 추가, 메뉴 추가, 메뉴 옵션 추가
            log.debug("사용자의 장바구니는 비어있습니다: {}", email);
            String cartMenuId = saveCartMenuAndMenuOption(createCartMenuDto, email, menu, cartMenuTotalPrice);
            saveNewCart(createCartMenuDto, cartMenuId, email, foodTruck, cartMenuTotalPrice);
            log.debug("장바구니에 메뉴가 추가되었습니다: {}", email);
        }

        return createCartMenuDto.getMenuId();
    }

    public CartResponse getShoppingCart(String email) throws JsonProcessingException {
        Cart cart;
        if (checkFieldKey(CART.getText(), email)) {
            cart = getCart(email);
        } else {
            log.debug("카트가 비어있습니다: {}", email);
            return null;
        }
//        List<CartMenu> cartMenuList = Optional.ofNullable(cart.getCartMenuIds())
//                .orElseGet(ArrayList::new)
//                .stream()
//                .map(this::getCartMenu)
//                .collect(Collectors.toList());
        List<CartMenuDto> cartMenuList = getCartMenuDto(cart.getCartMenuIds());

        return CartResponse.of(cart, cartMenuList);
    }


    public String incrementCartMenu(String cartMenuId, String email) throws JsonProcessingException {
        Cart cart = getCart(email);
        CartMenu cartMenu = getCartMenu(cartMenuId);
        log.debug("increment before cartTotalPrice: {}, before cartMenuQuantity: {}", cart.getTotalPrice(), cartMenu.getQuantity());
        cart.incrementCartTotalPrice(cartMenu.getCartMenuTotalPrice());
        cartMenu.incrementCartMenuQuantity(1);
        log.debug("increment after cartTotalPrice: {}, after cartMenuQuantity: {}", cart.getTotalPrice(), cartMenu.getQuantity());
        saveCart(email, cart);
        saveCartMenu(cartMenuId, cartMenu);
        return cartMenuId;
    }

    public String decrementCartMenu(String cartMenuId, String email) throws JsonProcessingException {
        Cart cart = getCart(email);
        CartMenu cartMenu = getCartMenu(cartMenuId);

        if (cartMenu.getQuantity().equals(1)) {
            return cartMenuId;
        }
        // 1이면 감소 불가

        log.debug("decrement before cartTotalPrice: {}, before cartMenuQuantity: {}", cart.getTotalPrice(), cartMenu.getQuantity());
        cart.decrementCartTotalPrice(cartMenu.getCartMenuTotalPrice());
        cartMenu.decrementCartMenuQuantity();
        log.debug("decrement after cartTotalPrice: {}, after cartMenuQuantity: {}", cart.getTotalPrice(), cartMenu.getQuantity());
        saveCart(email, cart);
        saveCartMenu(cartMenuId, cartMenu);
        return cartMenuId;
    }


    public String removeCartMenu(String cartMenuId, String email) throws JsonProcessingException {
        Cart cart = getCart(email);
        CartMenu cartMenu = getCartMenu(cartMenuId);
        List<String> cartMenuOptionIds = Optional.ofNullable(cartMenu.getCartMenuOptionIds())
                .orElseGet(ArrayList::new);

        for (String cartMenuOptionId : cartMenuOptionIds) {
            deleteCartMenuOption(cartMenuOptionId);
            log.debug("카트메뉴옵션을 삭제했습니다: {}", cartMenuOptionId);
        }

        deleteCartMenu(cartMenuId);

        cart.removeCartMenuIds(cartMenuId);
        cart.decrementCartTotalPrice(cartMenu.getCartMenuTotalPrice()*cartMenu.getQuantity());
        log.debug("카트메뉴를 삭제했습니다: {}", cartMenuId);
        if (cart.getCartMenuIds().isEmpty()) {
            log.debug("카트에 메뉴가 없어 카트를 삭제합니다");
            deleteCart(email);
        } else {
            saveCart(email, cart);
        }
        return cartMenuId;
    }

    public CartOrderResponse getCartOrder(String email) throws JsonProcessingException {
        Cart cart = getCart(email);
        FoodTruck foodTruck = getFoodTruckById(cart.getFoodTruckId());
        Member member = getMemberByEmail(email);
        return CartOrderResponse.of(foodTruck, member, cart);
    }

    public CreateOrderDto createOrderByCart(String email) throws JsonProcessingException {
        Cart cart = getCart(email);

        List<OrderMenuItem> orderMenuItems = new ArrayList<>();
        for (String cartMenuId : cart.getCartMenuIds()) {
            CartMenu cartMenu = getCartMenu(cartMenuId);
            List<String> cartMenuOptionIds = Optional.ofNullable(cartMenu.getCartMenuOptionIds())
                    .orElseGet(ArrayList::new);
            List<Long> menuOptionIdList = new ArrayList<>();
            for (String cartMenuOptionId : cartMenuOptionIds) {
                CartMenuOption cartMenuOption = getCartMenuOption(cartMenuOptionId);
                menuOptionIdList.add(cartMenuOption.getMenuOptionId());
            }
            orderMenuItems.add(OrderMenuItem.of(cartMenu, menuOptionIdList));
        }

        return CreateOrderDto.of(cart, orderMenuItems);
    }

    /**
     * (；′⌒`)
     **/

    public List<CartMenuDto> getCartMenuDto(List<String> cartMenuIds) throws JsonProcessingException {
        List<CartMenuDto> cartMenuDtos = new ArrayList<>();
        for (String cartMenuId : cartMenuIds) {
            CartMenu cartMenu = getCartMenu(cartMenuId);
            List<String> cartMenuOptionIds = Optional.ofNullable(cartMenu.getCartMenuOptionIds())
                    .orElseGet(ArrayList::new);
            cartMenuDtos.add(CartMenuDto.of(cartMenu, getCartMenuOptionDto(cartMenuOptionIds)));
        }
        return cartMenuDtos;
    }

    public List<CartMenuOptionDto> getCartMenuOptionDto(List<String> cartMenuOptionIds) throws JsonProcessingException {
        List<CartMenuOptionDto> cartMenuOptionDtos = new ArrayList<>();
        for (String cartMenuOptionId : cartMenuOptionIds) {
            cartMenuOptionDtos.add(CartMenuOptionDto.of(getCartMenuOption(cartMenuOptionId)));
        }
        return cartMenuOptionDtos;
    }

    public String checkSameMenu(CreateCartMenuDto createCartMenuDto, String email) throws JsonProcessingException {
        Cart cart = getCart(email);

        for (String cartMenuId : cart.getCartMenuIds()) {
            CartMenu cartMenu = getCartMenu(cartMenuId);
            List<String> cartMenuOptionIds = Optional.ofNullable(cartMenu.getCartMenuOptionIds())
                    .orElseGet(ArrayList::new);
            List<Long> menuOptionIdList = new ArrayList<>();
            for (String cartMenuOptionId : cartMenuOptionIds) {
                CartMenuOption cartMenuOption = getCartMenuOption(cartMenuOptionId);
                menuOptionIdList.add(cartMenuOption.getMenuOptionId());
            }

            List<Long> menuOptionIds = new ArrayList<>();
            if (createCartMenuDto.getMenuOptionIds() != null) {
                menuOptionIds = createCartMenuDto.getMenuOptionIds();
            }

            if(createCartMenuDto.getMenuId().equals(cartMenu.getMenuId()) &&
                    Arrays.equals(menuOptionIds.toArray(), menuOptionIdList.toArray())) {
                log.debug("장바구니에 같은 메뉴가 담겨있습니다: {}", cartMenu.getMenuId());
                return cartMenuId;
            }
        }
        return null;
    }

    public Integer getCartMenuTotalPrice(CreateCartMenuDto createCartMenuDto) {
        Integer cartMenuTotalPrice = 0;

        List<Long> menuOptionIds = new ArrayList<>();
        if (createCartMenuDto.getMenuOptionIds() != null) {
            menuOptionIds = createCartMenuDto.getMenuOptionIds();
        }

        for (Long menuOptionId : menuOptionIds) {
            cartMenuTotalPrice += getMenuOptionById(menuOptionId).getMenuInfo().getPrice();
        }
        cartMenuTotalPrice += getMenuById(createCartMenuDto.getMenuId()).getMenuInfo().getPrice();
        return cartMenuTotalPrice;
    }
    public String saveCartMenuAndMenuOption(CreateCartMenuDto createCartMenuDto, String email, Menu menu, Integer cartMenuTotalPrice) {

        RedisAtomicLong cartMenuIndex = new RedisAtomicLong("cartMenuId", redisTemplate.getConnectionFactory());
        String cartMenuId = String.valueOf(cartMenuIndex.incrementAndGet());
        // 메뉴 고유 pk값 생성
        List<String> cartMenuOptionIds = new ArrayList<>();

        // 옵션이 널일때 처리
        List<Long> menuOptionIds = new ArrayList<>();
        if (createCartMenuDto.getMenuOptionIds() != null) {
            menuOptionIds = createCartMenuDto.getMenuOptionIds();
        }

        for (Long menuOptionId : menuOptionIds) {
            RedisAtomicLong cartMenuOptionIndex = new RedisAtomicLong("cartMenuOptionId", redisTemplate.getConnectionFactory());
            String cartMenuOptionId = String.valueOf(cartMenuOptionIndex.incrementAndGet());
            // 메뉴 옵션 고유 pk값 생성
            cartMenuOptionIds.add(cartMenuOptionId);

            MenuOption menuOption = getMenuOptionById(menuOptionId);
            // 메뉴 옵션 생성
            CartMenuOption cartMenuOption = CartMenuOption.builder()
                    .id(cartMenuOptionId)
                    .cartMenuId(cartMenuId)
                    .menuOptionId(menuOptionId)
                    .name(menuOption.getMenuInfo().getName())
                    .price(menuOption.getMenuInfo().getPrice())
                    .build();

            saveCartMenuOption(cartMenuOptionId, cartMenuOption);
            log.debug("cartMenuOption을 저장합니다: {}", cartMenuOptionId);
        }
        MenuImage menuImage = menuImageQueryRepository.getMenuImageByMenuId(menu.getId());
        String image = NO_IMG; // TODO: 2023-11-10 이미지가 없으면 없는 이미지의 S3주소 넣기
        if(menuImage != null) {
            image = menuImage.getUploadFile().getStoreFileName();
        }
        CartMenu cartMenu = CartMenu.builder()
                .id(cartMenuId)
                .cartId(email)
                .menuId(createCartMenuDto.getMenuId())
                .name(menu.getMenuInfo().getName())
                .price(menu.getMenuInfo().getPrice())
                .cartMenuTotalPrice(cartMenuTotalPrice)
                .quantity(createCartMenuDto.getCartMenuQuantity())
                .menuImageUrl(image)
                .cartMenuOptionIds(cartMenuOptionIds)
                .build();

        saveCartMenu(cartMenuId, cartMenu);
        log.debug("cartMenu를 저장합니다: {}", cartMenuId);
        return cartMenuId;
    }

    public void saveNewCart(CreateCartMenuDto createCartMenuDto, String cartMenuId, String email, FoodTruck foodTruck, Integer cartMenuTotalPrice) {
        Cart cart = Cart.builder()
                .id(email)
                .foodTruckId(createCartMenuDto.getFoodTruckId())
                .foodTruckName(foodTruck.getName())
                .totalPrice(cartMenuTotalPrice*createCartMenuDto.getCartMenuQuantity())
                .cartMenuIds(Arrays.asList(cartMenuId))
                .build();
        saveCart(email, cart);
        log.debug("새 장바구니에 메뉴를 추가합니다: {}", cartMenuId);
    }

    public void saveUpdateCart(CreateCartMenuDto createCartMenuDto, String CartMemberId, Cart cart, String email, Integer cartMenuTotalPrice) {

        log.debug("before cartTotalPrice: {}, before cartMenuIds: {}", cart.getTotalPrice(), cart.getCartMenuIds().toString());
        cart.incrementCartTotalPrice(cartMenuTotalPrice);
        cart.addCartMenuIds(CartMemberId);
        log.debug("after cartTotalPrice: {}, after cartMenuIds: {}", cart.getTotalPrice(), cart.getCartMenuIds().toString());
        saveCart(email, cart);
    }


    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
    }

    private FoodTruck getFoodTruckById(Long foodTruckId) {
        return foodTruckRepository.findById(foodTruckId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 푸드트럭입니다."));
    }

    private Menu getMenuById(Long menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 메뉴입니다."));
    }

    private MenuOption getMenuOptionById(Long menuOptionId) {
        return menuOptionRepository.findById(menuOptionId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 메뉴옵션입니다."));
    }


    public boolean checkFieldKey(String hashKey, String fieldkey) {
        return redisTemplate.opsForHash().hasKey(hashKey, fieldkey);
    }

    public void deleteCartMenuOption(String fieldkey) {
        redisTemplate.opsForHash().delete(CARTMENUOPTION.getText(), fieldkey);
    }

    public void deleteCartMenu(String fieldkey) {
        redisTemplate.opsForHash().delete(CARTMENU.getText(), fieldkey);
    }

    public void deleteCart(String fieldkey) {
        redisTemplate.opsForHash().delete(CART.getText(), fieldkey);
    }


    public <T> boolean saveData(String key, String field, T data) {
        try {
            String value = objectMapper.writeValueAsString(data);
            redisTemplate.opsForHash().put(key, field, value);
            return true;
        } catch (Exception e) {
//            log.error(e);
            return false;
        }
    }

    public <T> void saveCart(String field, T data) {
        saveData(CART.getText(), field, data);
    }

    public <T> void saveCartMenu(String field, T data) {
        saveData(CARTMENU.getText(), field, data);
    }

    public <T> void saveCartMenuOption(String field, T data) {
        saveData(CARTMENUOPTION.getText(), field, data);
    }

    public <T> T getData(String key, String field, Class<T> classType) throws JsonProcessingException {

        String jsonResult = (String) redisTemplate.opsForHash().get(key, field);
        if (jsonResult.isEmpty()) {
            return null;
        } else {
            T obj = objectMapper.readValue(jsonResult, classType);
            return obj;
        }
    }

    public Cart getCart(String field) throws JsonProcessingException {
        return getData(CART.getText(), field, Cart.class);

    }

    public CartMenu getCartMenu(String field) throws JsonProcessingException {
        return getData(CARTMENU.getText(), field, CartMenu.class);
    }

    public CartMenuOption getCartMenuOption(String field) throws JsonProcessingException {
        return getData(CARTMENUOPTION.getText(), field, CartMenuOption.class);
    }

    public Set getField(String key) throws JsonProcessingException {

        Set jsonResult = redisTemplate.opsForHash().keys(key);
        if (jsonResult.isEmpty()) {
            return null;
        } else {
            return jsonResult;
        }
    }

    public Map getEntries(String key) throws JsonProcessingException {

        Map jsonResult = redisTemplate.opsForHash().entries(key);
        if (jsonResult.isEmpty()) {
            return null;
        } else {
            return jsonResult;
        }
    }


}
