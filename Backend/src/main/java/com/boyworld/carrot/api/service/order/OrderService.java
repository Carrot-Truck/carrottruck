package com.boyworld.carrot.api.service.order;

import com.boyworld.carrot.api.controller.order.response.OrderDetailResponse;
import com.boyworld.carrot.api.controller.order.response.OrderResponse;
import com.boyworld.carrot.api.service.order.dto.CreateOrderDto;
import com.boyworld.carrot.domain.order.repository.OrderRepository;
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

    private final OrderRepository orderRepository;

    /**
     * 전체 주문내역 조회 API
     *
     * @param email     현재 로그인 중인 사용자 이메일
     * @return 사용자의 전체 주문 내역
     */
    public OrderResponse getOrders(String email) {
        return null;
    }

    /**
     * 내 푸드트럭의 진행 중인 주문내역 조회 API
     *
     * @param foodTruckId   내 푸드트럭 식별키
     * @param email         현재 로그인 중인 사용자 이메일
     * @return 진행 중인 주문 내역
     */
    public OrderResponse getProcessingOrders(Long foodTruckId, String email) {
        return null;
    }

    /**
     * 내 푸드트럭의 완료된 주문내역 조회 API
     *
     * @param foodTruckId   내 푸드트럭 식별키
     * @param email         현재 로그인 중인 사용자 이메일
     * @return 완료된 주문 내역
     */
    public OrderResponse getCompleteOrders(Long foodTruckId, String email) {
        return null;
    }

    /**
     * (주문 내역에서) 주문 삭제 API
     *
     * @param orderId   삭제할 주문 식별키
     * @param email     현재 로그인 중인 사용자 이메일
     * @return 삭제된 푸드트럭 식별키                  
     */
    public Long deleteOrder(Long orderId, String email) {
        return null;
    }

    /**
     * 주문 상세 조회 API
     *
     * @param orderId   조회할 주문 식별키
     * @param email     현재 로그인 중인 사용자 이메일
     * @return 주문 상세 정보
     */
    public OrderDetailResponse getOrder(Long orderId, String email) {
        return null;
    }

    /**
     * 주문 생성 API
     * 
     * @param dto 주문 정보
     * @param email 로그인 중인 회원 이메일
     * @return 생성된 주문 식별키
     */
    public Long createOrder(CreateOrderDto dto, String email) {
        return null;
    }

    /**
     * 주문 취소 API -- 주문이 처리 대기 중인 상태인 경우에 한해 가능
     *
     * @param orderId 취소할 주문 식별키
     * @param email 로그인 중인 회원 이메일
     * @return 취소된 주문 식별키
     */
    public Long cancelOrder(Long orderId, String email) {
        return null;
    }
}
