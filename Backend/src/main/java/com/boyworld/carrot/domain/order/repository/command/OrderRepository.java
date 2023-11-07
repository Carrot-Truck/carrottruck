package com.boyworld.carrot.domain.order.repository.command;

import com.boyworld.carrot.domain.order.Order;
import com.boyworld.carrot.domain.order.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 주문 CRUD 레포지토리
 *
 * @author 박은규
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}
