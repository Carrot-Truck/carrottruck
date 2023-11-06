package com.boyworld.carrot.domain.order.repository.command;

import com.boyworld.carrot.domain.order.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 주문-메뉴 CRUD 레포지토리
 * 
 * @author 박은규
 */
public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {

}
