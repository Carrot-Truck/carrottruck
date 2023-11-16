package com.boyworld.carrot.api.service.order;

import com.boyworld.carrot.api.controller.order.response.OrderResponse;
import com.boyworld.carrot.api.controller.order.response.OrdersResponse;
import com.boyworld.carrot.api.service.member.error.InValidAccessException;
import com.boyworld.carrot.api.service.cart.CartService;
import com.boyworld.carrot.api.service.order.dto.CreateOrderDto;
import com.boyworld.carrot.api.service.order.dto.CreateOrderMenuDto;
import com.boyworld.carrot.api.service.order.dto.OrderItem;
import com.boyworld.carrot.api.service.order.dto.OrderMenuItem;
import com.boyworld.carrot.client.Sse.SseUtil;
import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import com.boyworld.carrot.domain.foodtruck.repository.command.FoodTruckRepository;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
import com.boyworld.carrot.domain.menu.Menu;
import com.boyworld.carrot.domain.menu.repository.command.MenuOptionRepository;
import com.boyworld.carrot.domain.menu.repository.command.MenuRepository;
import com.boyworld.carrot.domain.order.Order;
import com.boyworld.carrot.domain.order.OrderMenu;
import com.boyworld.carrot.domain.order.OrderMenuOption;
import com.boyworld.carrot.domain.order.Status;
import com.boyworld.carrot.domain.order.repository.command.OrderMenuOptionRepository;
import com.boyworld.carrot.domain.order.repository.command.OrderMenuRepository;
import com.boyworld.carrot.domain.order.repository.command.OrderRepository;
import com.boyworld.carrot.domain.order.repository.query.OrderQueryRepository;
import com.boyworld.carrot.domain.sale.Sale;
import com.boyworld.carrot.domain.sale.repository.command.SaleRepository;
import com.boyworld.carrot.domain.sale.repository.query.SaleQueryRepository;
import java.util.List;
import java.util.NoSuchElementException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 주문 서비스
 *
 * @author 박은규
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final FoodTruckRepository foodTruckRepository;
    private final MemberRepository memberRepository;
    private final MenuRepository menuRepository;
    private final MenuOptionRepository menuOptionRepository;
    private final OrderRepository orderRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final OrderMenuOptionRepository orderMenuOptionRepository;
    private final OrderQueryRepository orderQueryRepository;
    private final SaleRepository saleRepository;
    private final SaleQueryRepository saleQueryRepository;
    private final CartService cartService;
    private final SseUtil sseUtil;

    /**
     * 전체 주문내역 조회 API
     *
     * @param email     현재 로그인 중인 사용자 이메일
     * @return 사용자의 전체 주문 내역
     */
    public OrdersResponse getOrders(String email) {

        List<OrderItem> orderItems = memberRepository.findByEmail(email).map(Member::getId).map(
            orderQueryRepository::getClientOrderItems
        ).orElseThrow();

        return OrdersResponse.builder()
                .orderItems(orderItems)
                .build();
    }

    /**
     * 내 푸드트럭의 진행 중인 주문내역 조회 API
     *
     * @param foodTruckId   내 푸드트럭 식별키
     * @param email         현재 로그인 중인 사용자 이메일
     * @return 진행 중인 주문 내역
     */
    public OrdersResponse getProcessingOrders(Long foodTruckId, String email) {

        Member member = getMemberByEmail(email);
        FoodTruck foodTruck = getFoodTruckById(foodTruckId);
        checkOwnerAccess(member, foodTruck);

        List<OrderItem> orderItems = orderQueryRepository.getVendorOrderItems(foodTruckId, new Status[] {Status.PENDING, Status.PROCESSING});

        return OrdersResponse.builder()
            .orderItems(orderItems)
            .build();
    }

    /**
     * 내 푸드트럭의 완료된 주문내역 조회 API
     *
     * @param foodTruckId   내 푸드트럭 식별키
     * @param email         현재 로그인 중인 사용자 이메일
     * @return 완료된 주문 내역
     */
    public OrdersResponse getCompleteOrders(Long foodTruckId, String email) {

        Member member = getMemberByEmail(email);
        FoodTruck foodTruck = getFoodTruckById(foodTruckId);
        checkOwnerAccess(member, foodTruck);

        List<OrderItem> orderItems = orderQueryRepository.getVendorOrderItems(foodTruckId, new Status[] {Status.COMPLETE});

        return OrdersResponse.builder()
            .orderItems(orderItems)
            .build();
    }

    /**
     * 고객 - 주문 상세 조회 API
     *
     * @param orderId   조회할 주문 식별키
     * @param email     현재 로그인 중인 사용자 이메일
     * @param role      요청 주체
     * @return 주문 상세 정보
     */

    public OrderResponse getOrder(Long orderId, String email, Role role) {

        Order order = getOrderById(orderId);
        Member member = getMemberByEmail(email);
        if (role.equals(Role.CLIENT)) {
            checkOwnerAccess(member, order);
        } else if (role.equals(Role.VENDOR)) {
            checkOwnerAccess(member, order.getSale().getFoodTruck());
        }

        OrderItem orderItem = orderQueryRepository.getOrder(orderId, role);

        return OrderResponse.builder()
            .orderItem(orderItem)
            .build();
    }

    /**
     * 주문 생성 API
     *
     * @param email 로그인 중인 회원 이메일
     * @return 생성된 주문 식별키
     */
    public Long createOrder(String email) throws JsonProcessingException {

        CreateOrderDto dto = cartService.createOrderByCart(email);
        Member member = getMemberByEmail(email);
        FoodTruck foodTruck = getFoodTruckById(dto.getFoodTruckId());

        Order order = Order.builder()
                .member(member)
                .sale(saleQueryRepository.getLatestSale(dto.getFoodTruckId()).orElse(null))
                .status(Status.PENDING)
                .totalPrice(dto.getTotalPrice())
                .active(true)
                .build();

        Order createdOrder = orderRepository.save(order);

        for (CreateOrderMenuDto item: dto.getOrderMenuItems()) {
            OrderMenu orderMenu = OrderMenu.builder()
                .order(getOrderById(order.getId()))
                .menu(getMenuById(item.getMenuId()))
                .quantity(item.getQuantity())
                .active(true)
                .build();

            OrderMenu createdOrderMenu = orderMenuRepository.save(orderMenu);

            for (Long optionId: item.getMenuOptionList()) {
                OrderMenuOption orderMenuOption = OrderMenuOption.builder()
                    .menuOption(menuOptionRepository.findById(optionId).orElseThrow())
                    .orderMenu(orderMenuRepository.findById(createdOrderMenu.getId()).orElseThrow())
                    .quantity(1)
                    .active(true)
                    .build();

                orderMenuOptionRepository.save(orderMenuOption);
            }
        }

        getSaleById(order.getSale().getId()).incrementOrderNumber();

        pauseByWaitLimit(dto.getFoodTruckId(), foodTruck.getWaitLimits());

        sseUtil.send(foodTruck.getVendor().getEmail(), 1);

        return createdOrder.getId();
    }

    /**
     * 주문 취소 API -- 주문이 처리 대기 중인 상태인 경우에 한해 가능
     *
     * @param orderId 취소할 주문 식별키
     * @param email 로그인 중인 회원 이메일
     * @return 취소된 주문 식별키
     */
    public Long cancelOrder(Long orderId, String email) {

        Member member = getMemberByEmail(email);
        Order order = getOrderById(orderId);
        checkOwnerAccess(member, order);

        if (!order.getStatus().equals(Status.PENDING)) {
            throw new InValidAccessException("처리 중인 주문은 취소할 수 없습니다.");
        }

        FoodTruck foodTruck = order.getSale().getFoodTruck();
        pauseByWaitLimit(foodTruck.getId(), foodTruck.getWaitLimits());
        return order.editOrderStatus(Status.CANCELLED).getId();
    }

    /**
     * (주문 내역에서) 주문 삭제 API
     *
     * @param orderId   삭제할 주문 식별키
     * @param email     현재 로그인 중인 사용자 이메일
     * @return 삭제된 푸드트럭 식별키
     */
    public Long deleteOrder(Long orderId, String email) {

        Member member = getMemberByEmail(email);
        Order order = getOrderById(orderId);
        checkOwnerAccess(member, order);

        return order.editOrderActive(false).getId();
    }

    private void checkOwnerAccess(Member member, Order order) {
        if (!order.getMember().getId().equals(member.getId())) {
            throw new InValidAccessException("잘못된 접근입니다.");
        }
    }

    private void checkOwnerAccess(Member member, FoodTruck foodTruck) {
        if (!foodTruck.getVendor().getId().equals(member.getId())) {
            throw new InValidAccessException("잘못된 접근입니다.");
        }
    }

    /**
     * 이메일로 회원 엔티티 조회
     *
     * @param email 현재 로그인한 사용자 이메일
     * @return 이메일에 해당하는 회원 엔티티
     */
    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
    }

    /**
     * 주문 식별키로 주문 엔티티 조회
     *
     * @param orderId 주문 식별 키
     * @return 주문 식별 키에 해당하는 주문 엔티티
     */
    private Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 주문입니다."));
    }

    /**
     * 영업 식별키로 영업 엔티티 조회
     *
     * @param saleId 영업 식별 키
     * @return 영업 식별 키에 해당하는 영업 엔티티
     */
    private Sale getSaleById(Long saleId) {
        return saleRepository.findById(saleId)
            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 영업입니다."));
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
     *  메뉴 식별키로 메뉴 엔티티 조회
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
        Sale sale = saleQueryRepository.getLatestSale(foodTruckId).orElseThrow();
        Boolean isOrderable = sale.getOrderable();
        Boolean isExploded = orderQueryRepository.isOrdersExploded(foodTruckId, waitLimit);
        if (isOrderable && isExploded) {
            sale.editOrderable(false);
        } else if (!isOrderable && !isExploded) {
            sale.editOrderable(true);
        }
    }
}
