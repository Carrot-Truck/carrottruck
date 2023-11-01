package com.boyworld.carrot.api.controller.sale;

import com.boyworld.carrot.api.ApiResponse;
import com.boyworld.carrot.api.controller.order.response.VendorOrderResponse;
import com.boyworld.carrot.api.controller.sale.request.AcceptOrderRequest;
import com.boyworld.carrot.api.controller.sale.request.DeclineOrderRequest;
import com.boyworld.carrot.api.controller.sale.request.OpenSaleRequest;
import com.boyworld.carrot.api.controller.sale.response.CloseSaleResponse;
import com.boyworld.carrot.api.controller.sale.response.OpenSaleResponse;
import com.boyworld.carrot.api.service.order.OrderService;
import com.boyworld.carrot.api.service.sale.SaleService;
import com.boyworld.carrot.security.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 영업 관련 API 컨트롤러
 *
 * @author 박은규
 *
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sale")
public class SaleController {

    private final SaleService saleService;
    private final OrderService orderService;

    /**
     * 영업 개시 API
     *
     * @param request 개시할 영업 정보
     * @return 개시한 영업 정보
     */
    @PostMapping("/open")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<OpenSaleResponse> openSale(@Valid @RequestBody OpenSaleRequest request) {
        log.debug("SaleController#open called");
        log.debug("OpenSaleRequest={}", request);

        // 생성한 영업 정보 반환
        OpenSaleResponse response = saleService.openSale(request.toOpenSaleDto());
        log.debug("OpenSaleResponse={}", response);
        return ApiResponse.created(response);
    }

    /**
     * 진행 중인 주문 조회 API
     *
     * @param foodTruckId 푸드트럭 id
     * @return 진행 중인 주문 정보
     */
    @GetMapping("/processing/{foodTruckId}")
    public ApiResponse<VendorOrderResponse> getProcessingOrders(@PathVariable Long foodTruckId) {
        log.debug("SaleController#getProcessingOrders called");
        log.debug("foodTruckId={}", foodTruckId);

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        // 입력받은 푸드트럭 id로 해당 푸드트럭의 현재 영업 id 조회
        VendorOrderResponse response = orderService.getProcessingOrders(foodTruckId, email);
        log.debug("OrderResponse={}", response);
        // 해당 영업 id의 (pending & processing) 상태인 order 정보 반환

        return ApiResponse.ok(response);
    }

    /**
     * 완료된 주문 조회 API
     *
     * @param foodTruckId 푸드트럭 id
     * @return 완료된 주문 정보
     */
    @GetMapping("/complete/{foodTruckId}")
    public ApiResponse<VendorOrderResponse> getCompleteOrders(@PathVariable Long foodTruckId) {
        log.debug("SaleController#getCompleteOrders called");
        log.debug("foodTruckId={}", foodTruckId);

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        // 입력받은 푸드트럭 id로 해당 푸드트럭의 현재 영업 id 조회
        VendorOrderResponse response = orderService.getCompleteOrders(foodTruckId, email);
        log.debug("OrderResponse={}", response);

        // 해당 영업 id의 complete 상태인 order 정보 반환
        return ApiResponse.ok(response);
    }

    /**
     * 주문 수락 API
     *
     * @param request 주문 완료 소요 시간
     * @return 수락한 주문 식별키
     */
    @PostMapping("/accept")
    public ApiResponse<Long> accept(@Valid @RequestBody AcceptOrderRequest request) {
        log.debug("SaleControlelr#accept called");
        log.debug("AcceptOrderRequest={}", request);

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        Long acceptId = saleService.acceptOrder(request.toAcceptOrderDto(), email);
        log.debug("AccceptId={}", acceptId);

        return ApiResponse.ok(acceptId);
    }

    /**
     * 주문 거절 API
     *
     * @param request 주문 거절 사유
     * @return 거절 처리 여부
     */
    @PostMapping("decline")
    public ApiResponse<Long> decline(@Valid @RequestBody DeclineOrderRequest request) {
        log.debug("SaleController#decline called");
        log.debug("DeclineOrderRequest={}", request);

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        Long declineId = saleService.declineOrder(request.toDeclineOrderDto(), email);
        log.debug("declineId={}", declineId);

        return ApiResponse.ok(declineId);
    }

    /**
     * 주문 일시 정지 API
     *
     * @param foodTruckId
     * 일시 정지할 푸드트럭 id
     * @return 일시 정지 처리 여부
     */
    @PutMapping("/pause/{foodTruckId}")
    public ApiResponse<Long> pause(@PathVariable Long foodTruckId) {
        log.debug("SaleController#pause called");
        log.debug("foodTruckId={}", foodTruckId);

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        Long pauseId = saleService.pauseOrder(foodTruckId, email);
        log.debug("pauseId={}", pauseId);

        return ApiResponse.ok(pauseId);
    }

    /**
     * 품절 메뉴 등록 API
     *
     * @param menuId 품절 등록할 메뉴 식별키
     * @return 품절 등록한 메뉴 식별키
     */
    @PutMapping("soldout/{menuId}")
    public ApiResponse<Long> soldout(@PathVariable Long menuId) {
        log.debug("SaleController#soldout called");
        log.debug("menuId={}", menuId);

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        Long soldOutId = saleService.soldOutMenu(menuId, email);
        log.debug("menuId={}", soldOutId);

        return ApiResponse.ok(soldOutId);
    }

    /**
     * 영업 종료 API
     * 
     * @param foodTruckId 종료할 푸드트럭 id
     * @return 종료한 영업 정보
     */
    @PutMapping("/close/{foodTruckId}")
    public ApiResponse<CloseSaleResponse> closeSale(@PathVariable Long foodTruckId) {
        log.debug("SaleController#close called");
        log.debug("foodTruckId={}", foodTruckId);

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        CloseSaleResponse response = saleService.closeSale(foodTruckId, email);
        log.debug("CloseSaleResponse={}", response);

        return ApiResponse.ok(response);
    }
}
