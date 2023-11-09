package com.boyworld.carrot.api.service.sale;

import com.boyworld.carrot.api.controller.sale.response.CloseSaleResponse;
import com.boyworld.carrot.api.controller.sale.response.OpenSaleResponse;
import com.boyworld.carrot.api.service.fcm.FCMNotificationService;
import com.boyworld.carrot.api.service.fcm.dto.FCMNotificationRequestDto;
import com.boyworld.carrot.api.service.member.error.InValidAccessException;
import com.boyworld.carrot.api.service.order.dto.CompleteOrderDto;
import com.boyworld.carrot.api.service.sale.dto.AcceptOrderDto;
import com.boyworld.carrot.api.service.sale.dto.DeclineOrderDto;
import com.boyworld.carrot.api.service.sale.dto.OpenSaleDto;
import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import com.boyworld.carrot.domain.foodtruck.repository.command.FoodTruckRepository;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
import com.boyworld.carrot.domain.menu.Menu;
import com.boyworld.carrot.domain.menu.repository.command.MenuRepository;
import com.boyworld.carrot.domain.menu.repository.query.MenuQueryRepository;
import com.boyworld.carrot.domain.order.Order;
import com.boyworld.carrot.domain.order.Status;
import com.boyworld.carrot.domain.order.repository.command.OrderRepository;
import com.boyworld.carrot.domain.order.repository.query.OrderQueryRepository;
import com.boyworld.carrot.domain.sale.Sale;
import com.boyworld.carrot.domain.sale.repository.command.SaleRepository;
import com.boyworld.carrot.domain.sale.repository.query.SaleQueryRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
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

    private final MemberRepository memberRepository;
    private final SaleRepository saleRepository;
    private final SaleQueryRepository saleQueryRepository;
    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;
    private final FoodTruckRepository foodTruckRepository;
    private final FCMNotificationService fcmNotificationService;
    private final MenuRepository menuRepository;
    private final MenuQueryRepository menuQueryRepository;

    /**
     * 영업 개시 API
     *
     * @param dto 개시한 영업 정보
     * @return response 개시된 영업 정보
     */
    public OpenSaleResponse openSale(OpenSaleDto dto, String email) {

        Member member = getMemberByEmail(email);
        FoodTruck foodTruck = getFoodTruckById(dto.getFoodTruckId());
        checkOwnerAccess(member, foodTruck);

        // foodTruckId에 해당하는 푸드트럭에 현재 종료하지 않은 영업이 있는지 확인
        if (hasActiveSale(dto.getFoodTruckId())) return null;

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
        result.editOrderable(true);

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
        Member member = getMemberByEmail(email);
        Order order = getOrderById(dto.getOrderId());
        checkOwnerAccess(member, order);

        // 2. 주문번호로 주문 조회, 상태 STATUS.PROCESSING 으로 변경
        order = order.editOrderStatusAndExpectTime(Status.PROCESSING,
                LocalDateTime.now().plusHours(9).plusMinutes(dto.getPrepareTime()));

        FoodTruck foodTruck = order.getSale().getFoodTruck();
        pauseByWaitLimit(foodTruck.getId(), foodTruck.getWaitLimits());

        return order.getId();
    }

    /**
     * 주문 거절 API
     * 
     * @param dto 거절할 주문 정보 및 거절 사유
     * @return 거절한 주문 식별키
     */
    public Long declineOrder(DeclineOrderDto dto, String email) {
        // 1. 푸드트럭 소유주인지 확인
        Member member = getMemberByEmail(email);
        Order order = getOrderById(dto.getOrderId());
        checkOwnerAccess(member, order);

        // 2. 주문 번호로 주문 조회, 상태 STATUS.DECLINED 로 변경
        log.debug("{}", dto.getOrderId());
        order.editOrderStatus(Status.DECLINED);

        FoodTruck foodTruck = order.getSale().getFoodTruck();
        pauseByWaitLimit(foodTruck.getId(), foodTruck.getWaitLimits());

        return order.getId();
    }

    /**
     * 주문 완료 API
     *
     * @param dto 완료할 주문 정보
     * @return 완료한 주문 식별키
     */
    public Long completeOrder(CompleteOrderDto dto, String email) {
        // 1. 푸드트럭 소유주인지 확인
        Member member = getMemberByEmail(email);
        Order order = getOrderById(dto.getOrderId());
        checkOwnerAccess(member, order);

        // 2. 주문 번호로 주문 조회, 상태 STATUS.DECLINED 로 변경
        log.debug("{}", dto.getOrderId());
        order.editOrderStatus(Status.COMPLETE);

        FoodTruck foodTruck = order.getSale().getFoodTruck();
        pauseByWaitLimit(foodTruck.getId(), foodTruck.getWaitLimits());

        return order.getId();
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
         Member member = getMemberByEmail(email);
         FoodTruck foodTruck = getFoodTruckById(foodTruckId);
         checkOwnerAccess(member, foodTruck);

        // 2. 영업 비활성화
        return saleQueryRepository.getLatestSale(foodTruckId).map(
            sale -> sale.editOrderable(false).getId()).orElse(null);
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
        Member member = getMemberByEmail(email);
        FoodTruck foodTruck = getFoodTruckById(foodTruckId);
        checkOwnerAccess(member, foodTruck);

        // 2. 영업 활성화
        return saleQueryRepository.getLatestSale(foodTruckId).map(
            sale -> sale.editOrderable(true).getId()).orElse(null);

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
        Member member = getMemberByEmail(email);
        Menu menu = getMenuById(menuId);
        checkOwnerAccess(member, menu);

        // 2. 맞으면 해당 menuId 비활성화
        return menu.deActivate().getId();
    }

    /**
     * 영업 종료 API
     * 
     * @param foodTruckId    영업 종료할 푸드트럭 식별키
     * @param email     로그인한 사용자 이메일
     * @return 종료한 영업 식별키
     */
    public CloseSaleResponse closeSale(Long foodTruckId, String email) {

        // 1. 로그인한 사용자가 해당 영업의 푸드트럭을 보유한 사업자인지 확인
        Member member = getMemberByEmail(email);
        FoodTruck foodTruck = getFoodTruckById(foodTruckId);
        checkOwnerAccess(member, foodTruck);

        // 2. saleId로 영업 찾아서 endTime 저장
        Sale sale = saleQueryRepository.getLatestSale(foodTruckId).orElseThrow(
            () -> new NoSuchElementException("존재하지 않은 영업입니다.")
        );
        LocalDateTime now = LocalDateTime.now().plusHours(9);
        sale.editEndTime(now);
        sale.editOrderable(false);

        return CloseSaleResponse.builder()
                .saleId(sale.getId())
                .orderNumber(sale.getOrderNumber())
                .totalAmount(sale.getTotalAmount())
                .createdTime(sale.getCreatedDate())
                .endTime(now)
                .build();
    }

    private Boolean hasActiveSale(Long foodTruckId) {
        return saleQueryRepository.getLatestSale(foodTruckId).map(Sale::getEndTime).orElse(null) == null;
    }

    private void checkOwnerAccess(Member member, FoodTruck foodTruck) {
        if (!foodTruck.getVendor().getId().equals(member.getId())) {
            throw new InValidAccessException("잘못된 접근입니다.");
        }
    }

    private void checkOwnerAccess(Member member, Order order) {
        if (!order.getSale().getFoodTruck().getVendor().getId().equals(member.getId())) {
            throw new InValidAccessException("잘못된 접근입니다.");
        }
    }

    private void checkOwnerAccess(Member member, Menu menu) {
        if (!menu.getFoodTruck().getVendor().getId().equals(member.getId())) {
            throw new InValidAccessException("잘못된 접근입니다.");
        }
    }

    /**
     * 이메일로 회원 엔티티 조회
     *
     * @param email 현재 로그인한 사용자 이메일
     * @return 이메일에 해당하는 회원 엔티티
     * @throws NoSuchElementException 식별키에 해당하는 회원이 없는 경우
     */
    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
    }

    /**
     * 푸드트럭 식별키로 푸드트럭 조회
     *
     * @param foodTruckId 푸드트럭 식별키
     * @return 푸드트럭 엔티티
     * @throws NoSuchElementException 식별키에 해당하는 푸드트럭이 없는 경우
     */
    private FoodTruck getFoodTruckById(Long foodTruckId) {
        return foodTruckRepository.findById(foodTruckId)
            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 푸드트럭입니다."));
    }

    /**
     * 영업 식별키로 영업 조회
     *
     * @param saleId 영업 식별키
     * @return 영업 엔티티
     * @throws NoSuchElementException 식별키에 해당하는 영업이 없는 경우
     */
    private Sale getSaleById(Long saleId) {
        return saleRepository.findById(saleId)
            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 영업입니다."));
    }
    
    /**
     * 주문 식별키로 주문 조회
     *
     * @param orderId 주문 식별키
     * @return 주문 엔티티
     * @throws NoSuchElementException 식별키에 해당하는 주문이 없는 경우
     */
    private Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 주문입니다."));
    }

    /**
     * 메뉴 식별키로 메뉴 조회
     *
     * @param menuId 메뉴 식별키
     * @return 메뉴 엔티티
     * @throws NoSuchElementException 식별키에 해당하는 메뉴가 없는 경우
     */
    private Menu getMenuById(Long menuId) {
        return menuRepository.findById(menuId)
            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 메뉴입니다."));
    }

    private void pauseByWaitLimit(Long foodTruckId, Integer waitLimit) {
        if (orderQueryRepository.isOrdersExploded(foodTruckId, waitLimit)) {
            saleQueryRepository.getLatestSale(foodTruckId).ifPresent(
                sale -> sale.editOrderable(false));
        }
    }
}
