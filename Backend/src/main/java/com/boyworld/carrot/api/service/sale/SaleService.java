package com.boyworld.carrot.api.service.sale;

import com.boyworld.carrot.api.controller.sale.response.CloseSaleResponse;
import com.boyworld.carrot.api.controller.sale.response.OpenSaleResponse;
import com.boyworld.carrot.api.service.fcm.FCMNotificationService;
import com.boyworld.carrot.api.service.fcm.dto.FCMNotificationRequestDto;
import com.boyworld.carrot.api.service.sale.dto.AcceptOrderDto;
import com.boyworld.carrot.api.service.sale.dto.DeclineOrderDto;
import com.boyworld.carrot.api.service.sale.dto.OpenSaleDto;
import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import com.boyworld.carrot.domain.foodtruck.repository.command.FoodTruckRepository;
import com.boyworld.carrot.domain.foodtruck.repository.query.FoodTruckQueryRepository;
import com.boyworld.carrot.domain.menu.repository.command.MenuRepository;
import com.boyworld.carrot.domain.menu.repository.query.MenuQueryRepository;
import com.boyworld.carrot.domain.order.Status;
import com.boyworld.carrot.domain.order.repository.command.OrderRepository;
import com.boyworld.carrot.domain.sale.Sale;
import com.boyworld.carrot.domain.sale.repository.command.SaleRepository;
import com.boyworld.carrot.domain.sale.repository.query.SaleQueryRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
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
    private final SaleQueryRepository saleQueryRepository;
    private final OrderRepository orderRepository;
    private final FoodTruckRepository foodTruckRepository;
    private final FoodTruckQueryRepository foodTruckQueryRepository;
    private final FCMNotificationService fcmNotificationService;
    private final MenuRepository menuRepository;
    private final MenuQueryRepository menuQueryRepository;

    /**
     * 영업 개시 API
     *
     * @param dto 개시한 영업 정보
     * @return response 개시된 영업 정보
     */
    public OpenSaleResponse openSale(OpenSaleDto dto) {

        // foodTruckId에 해당하는 푸드트럭에 현재 종료하지 않은 영업이 있는지 확인
        if (saleQueryRepository.hasActiveSale(dto.getFoodTruckId())) {
            return null;
        }

        // 판매하지 않을 메뉴는 비활성화
        menuQueryRepository.setSaleMenuActive(dto.getFoodTruckId(), dto.getSaleMenuItems());

        // 새로운 영업 등록
        Sale sale = Sale.builder()
            .foodTruck(foodTruckRepository.findById(dto.getFoodTruckId()).orElse(null))
            .address(dto.getAddress())
            .latitude(dto.getLatitude())
            .longitude(dto.getLongitude())
            .orderNumber(0)
            .totalAmount(0)
            .startTime(LocalDateTime.now())
            .orderable(true)
            .active(true)
            .build();

        Sale result = saleRepository.save(sale);

        // 내 푸드트럭 찜한 사용자에게 알림 발송
        Map<String, String> data = new HashMap<>();
        data.put("foodTruckId", String.valueOf(dto.getFoodTruckId()));

        // 내 푸드트럭 찜한 사용자에게 알림 발송
        fcmNotificationService.sendNotificationByToken(
            FCMNotificationRequestDto.builder()
                .foodTruckId(dto.getFoodTruckId())
                .title("하이 동현~")
                .body("오늘 날씨 알려줘~")
                .data(data)
                .build());

        return OpenSaleResponse.builder()
            .saleId(result.getId())
            .saleMenuItems(dto.getSaleMenuItems())
            .build();
    }

    /**
     * 주문 수락 API
     *
     * @param dto 수락할 주문 정보 및 예상 소요 시간
     * @return 수락한 주문 식별키
     */
    public Long acceptOrder(AcceptOrderDto dto, String email) {
        // 1. 푸드트럭 소유주인지 확인
        if (!saleQueryRepository.isOrderOwner(dto.getOrderId(), email)) {
            return null;
        }

        // 2. 주문번호로 주문 조회, 상태 STATUS.PROCESSING 으로 변경
        orderRepository.findById(dto.getOrderId())
            .ifPresent(order -> order.updateOrderStatusAndExpectTime(Status.PROCESSING,
                LocalDateTime.now().plusHours(9).plusMinutes(dto.getPrepareTime())));
        return dto.getOrderId();
    }

    /**
     * 주문 거절 API
     * 
     * @param dto 거절할 주문 정보 및 거절 사유
     * @return 거절한 주문 식별키
     */
    public Long declineOrder(DeclineOrderDto dto, String email) {
        // 1. 푸드트럭 활성화 상태인지 먼저 확인
        if (!saleQueryRepository.isOrderOwner(dto.getOrderId(), email)) {
            return null;
        }

        // 2. 주문 번호로 주문 조회, 상태 STATUS.DECLINED 로 변경
        log.debug("{}", dto.getOrderId());
        orderRepository.findById(dto.getOrderId()).ifPresent(order -> order.updateOrderStatus(Status.DECLINED));
        return dto.getOrderId();
    }

    /**
     * 주문 일시 정지 API
     *
     * @param foodTruckId   주문 일시 정지할 푸드트럭 식별키
     * @param email         로그인한 사용자 이메일
     * @return 주문 일시 정지한 푸드트럭 식별키
     */
    public Long pauseOrder(Long foodTruckId, String email) {
        // 1. 로그인한 사용자가 해당 푸드트럭을 보유한 사업자인지 확인
        if (!foodTruckQueryRepository.isFoodTruckOwner(foodTruckId, email)) {
            return null;
        }

        // 2. 푸드트럭 비활성화
        foodTruckRepository.findById(foodTruckId).ifPresent(FoodTruck::deActivate);
        return foodTruckId;
    }

    /**
     * 주문 일시 정지 해제 API
     *
     * @param foodTruckId   주문 일시 정지 해제할 푸드트럭 식별키
     * @param email         로그인한 사용자 이메일
     * @return 주문 일시 정지 해제한 푸드트럭 식별키
     */

    public Long restartOrder(Long foodTruckId, String email) {
        // 1. 로그인한 사용자가 해당 푸드트럭을 보유한 사업자인지 확인
        if (!foodTruckQueryRepository.isFoodTruckOwner(foodTruckId, email)) {
            return null;
        }

        // 2. 푸드트럭 활성화
        foodTruckRepository.findById(foodTruckId).ifPresent(FoodTruck::activate);
        return foodTruckId;
    }

    /**
     * 메뉴 품절 등록 API
     *
     * @param menuId    품절 등록할 메뉴 식별키
     * @param email     로그인한 사용자 이메일
     * @return 품절 등록한 메뉴 식별키
     */
    public Long soldOutMenu(Long menuId, String email) {
        // 1. 로그인한 사용자가 menuId에 해당하는 메뉴를 가진
        // 푸드트럭 보유한 사업자인지 확인
        if (!menuQueryRepository.isMenuOwner(menuId, email)) {
            return null;
        }

        // 2. 맞으면 해당 menuId 비활성화
        menuRepository.findById(menuId).ifPresent(menu -> menu.editMenuActive(false));
        return menuId;
    }

    /**
     * 영업 종료 API
     * 
     * @param foodTruckId    영업 종료할 푸드트럭 식별키
     * @param email     로그인한 사용자 이메일
     * @return 종료한 영업 식별키
     */
    public CloseSaleResponse closeSale(Long foodTruckId, String email) {

        Sale sale = saleQueryRepository.getLatestSale(foodTruckId).orElse(null);
        // 1. 로그인한 사용자가 해당 영업의 푸드트럭을 보유한 사업자인지 확인
        if (sale == null || !saleQueryRepository.isSaleOwner(sale.getId(), email)) {
            log.debug("test");
            return null;
        }
        Long saleId = sale.getId();

        // 2. saleId로 영업 찾아서 endTime 저장
        LocalDateTime now = LocalDateTime.now().plusHours(9);
        saleQueryRepository.closeSale(saleId, now);

        return CloseSaleResponse.builder()
                .saleId(saleId)
                .orderNumber(sale.getOrderNumber())
                .totalAmount(sale.getTotalAmount())
                .createdTime(sale.getCreatedDate())
                .endTime(now)
                .build();
    }
}
