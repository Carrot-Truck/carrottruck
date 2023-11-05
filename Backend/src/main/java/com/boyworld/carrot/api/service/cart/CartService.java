package com.boyworld.carrot.api.service.cart;

import com.boyworld.carrot.api.controller.cart.response.CartOrderResponse;
import com.boyworld.carrot.api.controller.cart.response.CartResponse;
import com.boyworld.carrot.api.service.cart.dto.CreateCartMenuDto;
import com.boyworld.carrot.domain.cart.Cart;
import com.boyworld.carrot.domain.cart.repository.RedisCartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final RedisCartRepository redisCartRepository;

    public Long createCart(CreateCartMenuDto createCartMenuDto, String email) {
        // 회원의 카트가 존재하는지?
        // 회원의 카트가 존재하면 같은 푸드트럭의 카트가 존재하는 지?
        // 카트 및 카트 메뉴 추가
        Cart cart = Cart.builder()
                .id(email)
                .foodTruckId(createCartMenuDto.getFoodTruckId())
                .build();

        redisCartRepository.save(cart);
        return null;

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
}
