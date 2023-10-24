package com.boyworld.carrot.domain.order.repository;

import com.boyworld.carrot.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 주문 CRUD 레포지토리
 *
 * @author 박은규
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

}
