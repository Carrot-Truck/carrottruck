package com.boyworld.carrot.domain.order.repository.command;

import com.boyworld.carrot.domain.order.OrderMenuOption;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 주문-메뉴-옵션 CRUD 레포지토리
 *
 * @author 박은규
 */
public interface OrderMenuOptionRepository extends JpaRepository<OrderMenuOption, Long> {

}
