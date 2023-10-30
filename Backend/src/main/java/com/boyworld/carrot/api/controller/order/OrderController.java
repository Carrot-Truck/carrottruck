package com.boyworld.carrot.api.controller.order;

import com.boyworld.carrot.api.ApiResponse;
import com.boyworld.carrot.api.controller.order.request.CreateOrderRequest;
import com.boyworld.carrot.api.controller.order.response.OrderDetailResponse;
import com.boyworld.carrot.api.controller.order.response.OrderResponse;
import com.boyworld.carrot.api.service.order.OrderService;
import com.boyworld.carrot.security.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 주문 관련 API 컨트롤러
 *
 * @author 박은규
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    /**
     * 사용자 전체 주문 내역 조회 API
     *
     * @return 사용자 전체 주문 내역
     */
    @GetMapping
    public ApiResponse<OrderResponse> getOrders() {
        log.debug("OrderController:getOrders called");
        
        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);
        
        OrderResponse response = orderService.getOrders(email);
        log.debug("ClientOrderResponse={}", response);

        return ApiResponse.ok(response);
    }

    /**
     * 주문 내역 삭제 API
     *
     * @param orderId 삭제할 주문 id
     * @return 삭제 처리 여부
     */
    @DeleteMapping("/{orderId}")
    public ApiResponse<Long> deleteOrder(@PathVariable Long orderId) {
        log.debug("OrderController:deleteOrder called");

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        Long deleteId = orderService.deleteOrder(orderId, email);
        log.debug("deleteId={}", deleteId);

        return ApiResponse.found(deleteId);
    }

    /**
     * 주문 상세 조회 API
     *
     * @param orderId 주문 식별키
     * @return 주문 상세 정보
     */

    @GetMapping("/{orderId}}")
    public ApiResponse<OrderDetailResponse> getOrder(@PathVariable Long orderId) {
        log.debug("OrderController#getOrder called");
        log.debug("orderId={}", orderId);

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        OrderDetailResponse response = orderService.getOrder(orderId, email);
        log.debug("ClientOrderResponseDetail={}", response);

        return ApiResponse.ok(response);
    }

    /**
     * 주문 생성 API
     *
     * @param request 생성할 주문 정보
     * @return 생성된 주문 식별키
     */
    @PostMapping("create")
    public ApiResponse<Long> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        log.debug("OrderController#createOrder called");

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        Long saveId = orderService.createOrder(request.toCreateOrderDto(), email);
        log.debug("saveId={}", saveId);

        return ApiResponse.created(saveId);
    }

    /**
     * 주문 취소 API
     *
     * @param orderId 취소할 주문 정보
     * @return 취소된 주문 식별키
     */
    @PatchMapping("/{orderId}")
    public ApiResponse<Long> cancelOrder(@PathVariable Long orderId) {
        log.debug("OrderController#cancelOrder called");

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        Long cancelId = orderService.cancelOrder(orderId, email);

        return ApiResponse.found(cancelId);
    }
}
