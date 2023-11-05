package com.boyworld.carrot.api.service.sale;

import com.boyworld.carrot.api.controller.sale.response.CloseSaleResponse;
import com.boyworld.carrot.api.controller.sale.response.OpenSaleResponse;
import com.boyworld.carrot.api.service.sale.dto.AcceptOrderDto;
import com.boyworld.carrot.api.service.sale.dto.DeclineOrderDto;
import com.boyworld.carrot.api.service.sale.dto.OpenSaleDto;
import com.boyworld.carrot.domain.foodtruck.repository.command.FoodTruckRepository;
import com.boyworld.carrot.domain.sale.Sale;
import com.boyworld.carrot.domain.sale.repository.command.SaleRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 영업 서비스
 *
 * @author 박은규
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SaleService {

    private final SaleRepository saleRepository;
    private final FoodTruckRepository foodTruckRepository;

    /**
     * 영업 개시 API
     *
     * @param dto 개시한 영업 정보
     * @return response 개시된 영업 정보
     */
    public OpenSaleResponse openSale(OpenSaleDto dto) {

        // 새로운 영업 등록
        Sale sale = Sale.builder()
            .foodTruck(foodTruckRepository.findById(dto.getFoodTruckId()).orElse(null))
            .latitude(dto.getLatitude())
            .longitude(dto.getLongitude())
            .orderNumber(0)
            .totalAmount(0)
            .startTime(LocalDateTime.now())
            .orderable(true)
            .active(true)
            .build();

        saleRepository.save(sale);

        // 판매하지 않을 메뉴는 비활성화

        // 내 푸드트럭 찜한 사용자에게 알림 발송

        // 영업 중인 트럭 업데이트(메인페이지 지도에 띄울)

        return null;
    }

    /**
     * 주문 수락 API
     *
     * @param dto 수락할 주문 정보 및 예상 소요 시간
     * @return 수락한 주문 식별키
     */
    public Long acceptOrder(AcceptOrderDto dto, String email) {
        return null;
    }

    /**
     * 주문 거절 API
     * 
     * @param dto 거절할 주문 정보 및 거절 사유
     * @return 거절한 주문 식별키
     */
    public Long declineOrder(DeclineOrderDto dto, String email) {
        return null;
    }

    /**
     * 주문 일시 정지 API
     *
     * @param foodTruckId   주문 일시 정지할 푸드트럭 식별키
     * @param email         로그인한 사용자 이메일
     * @return 주문 일시 정지한 푸드트럭 식별키
     */
    public Long pauseOrder(Long foodTruckId, String email) {
        return null;
    }

    /**
     * 메뉴 품절 등록 API
     *
     * @param menuId    품절 등록할 메뉴 식별키
     * @param email     로그인한 사용자 이메일
     * @return 품절 등록한 메뉴 식별키
     */
    public Long soldOutMenu(Long menuId, String email) {
        // email에 해당하는 회원이 실제 해당 메뉴를 갖고 있는
        // 푸드트럭의 보유자인지 확인하는 로직 필요
        return null;
    }

    /**
     * 영업 종료 API
     * 
     * @param saleId    종료할 영업 식별키
     * @param email     로그인한 사용자 이메일
     * @return 종료한 영업 식별키
     */
    public CloseSaleResponse closeSale(Long saleId, String email) {
        return null;
    }
}
