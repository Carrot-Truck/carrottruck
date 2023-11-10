package com.boyworld.carrot.api.controller.cart;

import com.boyworld.carrot.api.ApiResponse;
import com.boyworld.carrot.api.controller.cart.request.CreateCartMenuRequest;
import com.boyworld.carrot.api.controller.cart.response.CartOrderResponse;
import com.boyworld.carrot.api.controller.cart.response.CartResponse;
import com.boyworld.carrot.api.service.cart.CartService;
import com.boyworld.carrot.security.SecurityUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 장바구니 관련 API 컨트롤러
 *
 * @author 김동현
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    // 장바구니 추가
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Long> createCart(@Valid @RequestBody CreateCartMenuRequest request) throws JsonProcessingException {
        log.debug("CartController#createCart called");
        log.debug("CreateCartRequest = {}", request);

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        Long saveId = cartService.createCart(request.toCreateMenuDto(), email);
        return ApiResponse.created(saveId);
    }

    // 장바구니 조회
    @GetMapping
    public ApiResponse<CartResponse> getShoppingCart() throws JsonProcessingException {
        log.debug("CartController#getCart called");

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email = {}", email);

        CartResponse response = cartService.getShoppingCart(email);
        log.debug("CartResponse = {}", response);

        return ApiResponse.ok(response);
    }

    // 장바구니 1 추가
    @PatchMapping("increment/{cartMenuId}")
    public ApiResponse<String> incrementCartMenu(@PathVariable String cartMenuId) throws JsonProcessingException {
        log.debug("CartController#incrementCartMenu called");

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email = {}", email);

        String editId = cartService.incrementCartMenu(cartMenuId, email);
        log.debug("editId = {}", editId);

        return ApiResponse.ok(editId);
    }

    // 장바구니 1 감소
    @PatchMapping("decrement/{cartMenuId}")
    public ApiResponse<String> decrementCartMenu(@PathVariable String cartMenuId) throws JsonProcessingException {
        log.debug("CartController#decrementCartMenu called");

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email = {}", email);

        String editId = cartService.decrementCartMenu(cartMenuId, email);
        log.debug("editId = {}", editId);

        return ApiResponse.ok(editId);
    }

    // 장바구니 삭제
    @DeleteMapping("/{cartMenuId}")
    @ResponseStatus(HttpStatus.FOUND)
    public ApiResponse<String> removeCartMenu(@PathVariable String cartMenuId) throws JsonProcessingException {
        log.debug("CartController#removeCart called");

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email = {}", email);

        String removeCartId = cartService.removeCartMenu(cartMenuId, email);
        log.debug("removeCartId = {}", removeCartId);

        return ApiResponse.found(removeCartId);
    }

    // 주문하기 페이지 조회
    @GetMapping("/order")
    public ApiResponse<CartOrderResponse> getCartOrder() throws JsonProcessingException {
        log.debug("CartController#getCartOrder called");

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email = {}", email);

        CartOrderResponse response = cartService.getCartOrder(email);
        log.debug("CartOrderResponse = {}", response);

        return ApiResponse.ok(response);
    }
}
