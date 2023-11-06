package com.boyworld.carrot.api.service.cart;

import com.boyworld.carrot.api.controller.cart.response.CartOrderResponse;
import com.boyworld.carrot.api.controller.cart.response.CartResponse;
import com.boyworld.carrot.api.service.cart.dto.CreateCartMenuDto;
import com.boyworld.carrot.api.service.cart.dto.CreateCartMenuOptionDto;
import com.boyworld.carrot.domain.cart.Cart;
import com.boyworld.carrot.domain.cart.CartMenu;
import com.boyworld.carrot.domain.cart.CartMenuOption;
import com.boyworld.carrot.domain.cart.repository.RedisCartRepository;
import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import com.boyworld.carrot.domain.foodtruck.repository.command.FoodTruckRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {
    private final RedisCartRepository redisCartRepository;
    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final FoodTruckRepository foodTruckRepository;

    public Long createCart(CreateCartMenuDto createCartMenuDto, String email) {

        FoodTruck foodTruck = foodTruckRepository.findById(createCartMenuDto.getFoodTruckId())
                .orElseThrow( () -> new NoSuchElementException("존재하지 않는 푸드트럭 입니다."));

        // 회원의 카트가 존재하는지?
            // 존재하지 않으면 새로 장바구니 새로 생성
                // 이메일이 id인 장바구니 생성
                // cartMenuId 데이터 생성
                // cartMenuOptionId 데이터 생성
            // 회원의 장바구니가 있으면 푸드트럭 Id whghl
        // 회원의 카트가 존재하면 같은 푸드트럭의 카트가 존재하는 지?
        // 카트 및 카트 메뉴 추가

        try {
            Long foodTruckId = getData(email, Cart.class).getFoodTruckId();

            if(!foodTruckId.equals(createCartMenuDto.getFoodTruckId())) {
                // 이전의 푸드트럭과 다르면 장바구니 비우기
                // cartMenuId인 cartMenuOption 삭제
                // cartId인 cartMenu 삭제
            }
        }
        catch (Exception e) {
            log.debug("장바구니가 비어있습니다 : {}", e.getMessage());
            Cart cart = Cart.builder()
                    .id(email)
                    .foodTruckId(createCartMenuDto.getFoodTruckId())
                    .foodTruckName("테스트 푸드트럭이름")
                    .totalPrice(createCartMenuDto.getMenuPrice() * createCartMenuDto.getCartMenuQuantity())
                    .build();
            saveData(email, cart); // toEntity 사용 saveData(email,
            log.debug("create new cart : {}", cart.getId());
        }

        RedisAtomicLong cartMenuIndex = new RedisAtomicLong("cartMenuId", redisTemplate.getConnectionFactory());
        Long cartMenuId = cartMenuIndex.incrementAndGet();
        // 메뉴 pk 생성 및 증가

        CartMenu cartMenu = CartMenu.builder()
                .id(cartMenuId)
                .cartId(email)
                .menuId(createCartMenuDto.getMenuId())
                .name(createCartMenuDto.getMenuName())
                .price(createCartMenuDto.getMenuPrice())
                .soldOut(createCartMenuDto.getMenuSoldOut())
                .quantity(createCartMenuDto.getCartMenuQuantity())
                .build();
        saveData(String.valueOf(cartMenuId), cartMenu); // Id는 고유가 될 수 없음..
        log.debug("cartMenu : {}", cartMenu.getName());

        if(!createCartMenuDto.getCartMenuOptionDtos().isEmpty()) {

            RedisAtomicLong cartMenuOptionIndex = new RedisAtomicLong("cartMenuOptionId", redisTemplate.getConnectionFactory());
            for(CreateCartMenuOptionDto createCartMenuOptionDto : createCartMenuDto.getCartMenuOptionDtos()){
                Long cartMenuOptionId = cartMenuOptionIndex.incrementAndGet();
                // 메뉴옵션 pk 생성 및 증가

                CartMenuOption cartMenuOption = CartMenuOption.builder()
                        .id(cartMenuOptionId)
                        .cartMenuId(cartMenuId)
                        .menuOptionId(createCartMenuOptionDto.getMenuOptionId())
                        .name(createCartMenuOptionDto.getMenuOptionName())
                        .price(createCartMenuOptionDto.getMenuOptionPrice())
                        .soldOut(createCartMenuOptionDto.getMenuOptionSoldOut())
                        .build();
                saveData(String.valueOf(cartMenuOptionId), cartMenuOption); // Id는 고유가 될 수 없음.. 2
                log.debug("cartMenuOption : {}", cartMenuOption.getName());
            }
        }

//        redisCartRepository.save(cart);
//        log.debug("CartService#saveCart : {}", cart);

        return cartMenuId;
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

    public <T> boolean saveData(String key, T data) {
        try {
            String value = objectMapper.writeValueAsString(data);
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch(Exception e){
//            log.error(e);
            return false;
        }
    }
public <T> T getData(String key, Class<T> classType) throws Exception {

    String jsonResult = (String) redisTemplate.opsForValue().get(key);
    if (jsonResult.isEmpty()) {
        return null;
    } else {
        T obj = objectMapper.readValue(jsonResult, classType);
        return obj;
    }

}
}
