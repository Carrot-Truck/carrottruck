package com.boyworld.carrot.api.service.cart;

import com.boyworld.carrot.api.controller.cart.response.CartResponse;
import com.boyworld.carrot.api.service.cart.dto.CreateCartMenuDto;
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
}
