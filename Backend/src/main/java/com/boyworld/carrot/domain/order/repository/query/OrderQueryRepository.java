package com.boyworld.carrot.domain.order.repository.query;

import static com.boyworld.carrot.domain.order.QOrder.order;
import static com.boyworld.carrot.domain.order.QOrderMenu.orderMenu;
import static com.boyworld.carrot.domain.order.QOrderMenuOption.orderMenuOption;

import com.boyworld.carrot.api.service.order.dto.OrderItem;
import com.boyworld.carrot.api.service.order.dto.OrderMenuItem;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<OrderItem> getOrderItems(Long memberId) {
        List<OrderItem> orderItems = queryFactory
            .select(Projections.constructor(OrderItem.class,
                order.id,
                order.member.id,
                order.totalPrice,
                order.createdDate,
                order.expectTime
                ))
                .from(order)
                .where(order.member.id.eq(memberId))
                .fetch();

        for (OrderItem orderItem: orderItems) {
            List<OrderMenuItem> orderMenuItems = queryFactory
                    .select(Projections.constructor(OrderMenuItem.class,
                            orderMenu.menu.id.as("menuId"),
                            orderMenu.quantity
                            ))
                    .from(orderMenu)
                    .where(orderMenu.order.id.eq(orderItem.getOrderId()))
                    .fetch();

            List<Long> menuOptionIds = new ArrayList<>();

            for (OrderMenuItem orderMenuItem: orderMenuItems) {
                menuOptionIds.addAll(queryFactory
                        .select(orderMenuOption.menuOption.id)
                        .from(orderMenuOption)
                        .where(orderMenuOption.orderMenu.id.eq(orderMenuItem.getMenuId()))
                        .fetch());
                orderMenuItem.setMenuOptionIdList(menuOptionIds);
            }
            orderItem.setOrderMenuItems(orderMenuItems);
        }

        return orderItems;
    }
}
