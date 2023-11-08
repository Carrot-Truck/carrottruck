package com.boyworld.carrot.api.service.order;

import com.boyworld.carrot.api.controller.order.response.OrderResponse;
import com.boyworld.carrot.api.controller.order.response.OrdersResponse;
import com.boyworld.carrot.api.service.member.error.InValidAccessException;
import com.boyworld.carrot.api.service.order.dto.CreateOrderDto;
import com.boyworld.carrot.api.service.order.dto.OrderItem;
import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import com.boyworld.carrot.domain.foodtruck.repository.command.FoodTruckRepository;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
import com.boyworld.carrot.domain.order.Order;
import com.boyworld.carrot.domain.order.Status;
import com.boyworld.carrot.domain.order.repository.command.OrderRepository;
import com.boyworld.carrot.domain.order.repository.query.OrderQueryRepository;
import com.boyworld.carrot.domain.sale.repository.query.SaleQueryRepository;
import java.util.List;
import java.util.NoSuchElementException;
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
    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;
    private final SaleQueryRepository saleQueryRepository;

    /**
     * 전체 주문내역 조회 API
     *
     * @param email     현재 로그인 중인 사용자 이메일
     * @return 사용자의 전체 주문 내역
     */
    public OrdersResponse getOrders(String email) {

        Long memberId = memberRepository.findByEmail(email).map(Member::getId).orElse(null);

        List<OrderItem> orderItems = orderQueryRepository.getClientOrderItems(memberId);

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

        List<OrderItem> orderItems = orderQueryRepository.getVendorOrderItems(foodTruckId, member.getId(), Status.PROCESSING);

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

        Long memberId = memberRepository.findByEmail(email).map(Member::getId).orElse(null);

        List<OrderItem> orderItems = orderQueryRepository.getVendorOrderItems(foodTruckId, memberId, Status.COMPLETE);

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

        Long memberId = memberRepository.findByEmail(email).map(Member::getId).orElse(null);

        OrderItem orderItem = orderQueryRepository.getOrder(orderId, memberId, role);

        return OrderResponse.builder()
            .orderItem(orderItem)
            .build();
    }

    /**
     * 주문 생성 API
     *
     * @param dto 주문 정보
     * @param email 로그인 중인 회원 이메일
     * @return 생성된 주문 식별키
     */
    public Long createOrder(CreateOrderDto dto, String email) {

        Member member = getMemberByEmail(email);
        FoodTruck foodTruck = getFoodTruckById(dto.getFoodTruckId());
        checkOwnerAccess(member, foodTruck);

        Order order = Order.builder()
                .member(memberRepository.findByEmail(email).orElse(null))
                .sale(saleQueryRepository.getLatestSale(dto.getFoodTruckId()).orElse(null))
                .status(Status.PENDING)
                .totalPrice(dto.getTotalPrice())
                .active(true)
                .build();

        Order createdOrder = orderRepository.save(order);

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
     * 이메일로 회원 엔티티 조회
     *
     * @param orderId 주문 식별 키
     * @return 주문 식별 키에 해당하는 주문 엔티티
     */
    private Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 주문입니다."));
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
}
