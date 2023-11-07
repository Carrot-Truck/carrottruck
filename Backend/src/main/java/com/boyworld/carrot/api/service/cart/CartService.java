package com.boyworld.carrot.api.service.cart;

import com.boyworld.carrot.api.controller.cart.response.CartOrderResponse;
import com.boyworld.carrot.api.controller.cart.response.CartResponse;
import com.boyworld.carrot.api.service.cart.dto.CreateCartMenuDto;
import com.boyworld.carrot.domain.cart.Cart;
import com.boyworld.carrot.domain.cart.CartMenu;
import com.boyworld.carrot.domain.cart.CartMenuOption;
import com.boyworld.carrot.domain.cart.CartType;
import com.boyworld.carrot.domain.cart.repository.CartRepository;
import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import com.boyworld.carrot.domain.foodtruck.repository.command.FoodTruckRepository;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
import com.boyworld.carrot.domain.menu.Menu;
import com.boyworld.carrot.domain.menu.MenuOption;
import com.boyworld.carrot.domain.menu.repository.command.MenuOptionRepository;
import com.boyworld.carrot.domain.menu.repository.command.MenuRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.boyworld.carrot.domain.cart.CartType.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final FoodTruckRepository foodTruckRepository;
    private final MemberRepository memberRepository;
    private final MenuRepository menuRepository;
    private final MenuOptionRepository menuOptionRepository;

    public Long createCart(CreateCartMenuDto createCartMenuDto, String email) throws JsonProcessingException {

        Cart cart = Cart.builder()
                .id(email)
                .foodTruckId(1l)
                .foodTruckName("푸드트럭이름")
                .totalPrice(1000)
                .build();
        Cart cart1 = Cart.builder()
                .id("email")
                .foodTruckId(1l)
                .foodTruckName("푸드트럭이름2")
                .totalPrice(2000)
                .build();
        List<Cart> list = new ArrayList<>();
        list.add(cart);
        list.add(cart1);

        saveData(email, CARTMENU.name(), list);


        return 0l;

//        Member member = getMemberByEmail(email);
//
//        FoodTruck foodTruck = getFoodTruckById(createCartMenuDto.getFoodTruckId());
//
//        Menu menu = getMenuById(createCartMenuDto.getMenuId());
//
//        List<MenuOption> menuOptions = createCartMenuDto.getCartMenuOptionIds().stream()
//                .map(this::getMenuOptionById)
//                .collect(Collectors.toList());
//
//        if(redisTemplate.hasKey(email)) {
//            Cart cart = getData(CARTMENU.name(), email, Cart.class);
//            if(cart.getFoodTruckId().equals(createCartMenuDto.getFoodTruckId())) {
//                // 회원 카트가 존재하고 푸드트럭이 같으면 카트 총금액 변경, 메뉴 추가, 메뉴 옵션 추가
//                cart.updateCartTotalPrice(menu.getMenuInfo().getPrice());
//            }
//            else {
//                // 회원 카트가 존재하지만 푸드트럭이 다르면 메뉴 삭제, 메뉴 옵션 삭제
////                redisTemplate.opsForValue().multiGet();
//
//                // 카트 추가, 메뉴 추가, 메뉴 옵션 추가
//            }
//        }
//        else {
//            // 회원 카트가 존재하지 않으면 카트 추가, 메뉴 추가, 메뉴 옵션 추가
//            Cart cart = Cart.builder()
//                    .id(email)
//                    .foodTruckId(foodTruck.getId())
//                    .foodTruckName(foodTruck.getName())
//                    .totalPrice(menu.getMenuInfo().getPrice() * createCartMenuDto.getCartMenuQuantity())
//                    .build();
//
//            saveData(email, cart);
//            log.debug("cart 추가: {}", cart.getId());
//        }
//
//
//        // 기존의 메뉴가 있으며 옵션도 같으면 수량추가
//        // 기존의 메뉴가 있지만 옵션이 다르면 메뉴 추가
//        // 기존의 메뉴가 없으면 메뉴 추가
//
//        RedisAtomicLong cartMenuIndex = new RedisAtomicLong("cartMenuId", redisTemplate.getConnectionFactory());
//        String cartMenuId = email + ":" + cartMenuIndex.incrementAndGet();
//        // 카트메뉴 pk 생성
//        saveData(cartMenuId, createCartMenuDto.toEntity(menu, cartMenuId, email));
//        log.debug("cartMenu 추가: {}", cartMenuId);
//
//        if(createCartMenuDto.getCartMenuOptionIds() != null) {
//            RedisAtomicLong cartMenuOptionIndex = new RedisAtomicLong("cartMenuOptionId", redisTemplate.getConnectionFactory());
//            for(MenuOption menuOption : menuOptions) {
//                String cartMenuOptionId = cartMenuId + ":" + cartMenuOptionIndex.incrementAndGet();
//                // 카트메뉴옵션 pk 생성
//                CartMenuOption cartMenuOption = CartMenuOption.builder()
//                        .id(cartMenuOptionId)
//                        .cartMenuId(cartMenuId)
//                        .menuOptionId(menuOption.getId())
//                        .name(menuOption.getMenuInfo().getName())
//                        .price(menuOption.getMenuInfo().getPrice())
//                        .build();
//                saveData(cartMenuOptionId, cartMenuOption);
//                log.debug("cartMenuOption 추가: {}", cartMenuOptionId);
//            }
//        }
//
//        return foodTruck.getId();
    }

    public CartResponse getCart(String email) {
        return null;
    }

    public Long editCartMenu(Long cartMenuId, String email) {
        return null;
    }

    public Long removeCartMenu(Long cartMenuId, String email) {
        return null;
    }

    public CartOrderResponse getCartOrder(String email) {
        return null;
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

    public <T> T getData(String key, String field, Class<T> classType) throws JsonProcessingException {

        String jsonResult = (String) redisTemplate.opsForHash().get(key, field);
        if (jsonResult.isEmpty()) {
            return null;
        } else {
            T obj = objectMapper.readValue(jsonResult, classType);
            return obj;
        }
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
