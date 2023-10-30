package com.boyworld.carrot.api.controller.cart;

import com.boyworld.carrot.api.ApiResponse;
import com.boyworld.carrot.api.controller.cart.request.CartMenuRequest;
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

    // 장바구니 추가
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Long> createCart(@Valid @RequestBody CartMenuRequest request) {
        log.debug("CartController#createCart called");
        log.debug("CreateCartRequest={}", request);

        Long saveId = 0l;
        return ApiResponse.created(saveId);
    }
    // 장바구니 조회
    // 장바구니 수정
    // 장바구니 삭제


}
