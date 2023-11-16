package com.boyworld.carrot.domain.order.repository.query;

import com.boyworld.carrot.api.service.order.dto.OrderItem;
import com.boyworld.carrot.api.service.order.dto.OrderMenuItem;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.order.Status;
import com.boyworld.carrot.domain.sale.repository.query.SaleQueryRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.boyworld.carrot.domain.order.QOrder.order;
import static com.boyworld.carrot.domain.order.QOrderMenu.orderMenu;
import static com.boyworld.carrot.domain.order.QOrderMenuOption.orderMenuOption;
import static com.boyworld.carrot.domain.sale.QSale.sale;

@Slf4j
@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final SaleQueryRepository saleQueryRepository;

    public List<OrderItem> getClientOrderItems(Long memberId) {
        List<OrderItem> orderItems = queryFactory
                .select(Projections.constructor(OrderItem.class,
                        order.id.as("orderId"),
                        order.status,
                        order.totalPrice,
                        order.createdDate.as("createdTime"),
                        order.expectTime
                ))
                .from(order)
                .where(
                        order.member.id.eq(memberId),
                        order.active
                )
                .orderBy(order.createdDate.asc())
                .fetch();

        int count = 1;
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrderCnt(count++);
            List<OrderMenuItem> orderMenuItems = queryFactory
                    .select(Projections.constructor(OrderMenuItem.class,
                            orderMenu.id,
                            orderMenu.menu.id.as("menuId"),
                            orderMenu.menu.menuInfo.name,
                            orderMenu.menu.menuInfo.price,
                            orderMenu.quantity
                    ))
                    .from(orderMenu)
                    .where(orderMenu.order.id.eq(orderItem.getOrderId()))
                    .fetch();

            List<Long> menuOptionIds = new ArrayList<>();

            for (OrderMenuItem orderMenuItem : orderMenuItems) {
                menuOptionIds.addAll(queryFactory
                        .select(orderMenuOption.menuOption.id)
                        .from(orderMenuOption)
                        .where(orderMenuOption.orderMenu.id.eq(orderMenuItem.getId()))
                        .fetch());
                orderMenuItem.setMenuOptionList(menuOptionIds);
            }
            orderItem.setOrderMenuItems(orderMenuItems);
        }

        return orderItems;
    }

    public List<OrderItem> getVendorOrderItems(Long foodTruckId, Status[] status) {
        List<OrderItem> orderItems = queryFactory
                .select(Projections.constructor(OrderItem.class,
                        order.id,
                        order.status,
                        order.totalPrice,
                        order.createdDate,
                        order.expectTime
                ))
                .from(order)
                .leftJoin(order.sale, sale)
                .where(
                        order.sale.foodTruck.id.eq(foodTruckId),
                        order.sale.id.eq(queryFactory
                                .select(sale.id)
                                .from(sale)
                                .where(sale.foodTruck.id.eq(foodTruckId))
                                .orderBy(sale.createdDate.desc())
                                .fetchFirst()),
                        order.status.in(status)
                )
                .orderBy(order.createdDate.asc())
                .fetch();

        int count = 1;
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrderCnt(count++);
            List<OrderMenuItem> orderMenuItems = queryFactory
                    .select(Projections.bean(OrderMenuItem.class,
                            orderMenu.id,
                            orderMenu.menu.id.as("menuId"),
                            orderMenu.quantity
                    ))
                    .from(orderMenu)
                    .where(orderMenu.order.id.eq(orderItem.getOrderId()))
                    .fetch();

            for (OrderMenuItem orderMenuItem : orderMenuItems) {
                List<Long> menuOptionIds = new ArrayList<>(queryFactory
                        .select(orderMenuOption.menuOption.id)
                        .from(orderMenuOption)
                        .where(orderMenuOption.orderMenu.id.eq(orderMenuItem.getId()))
                        .fetch());
                orderMenuItem.setMenuOptionList(menuOptionIds);
            }
            orderItem.setOrderMenuItems(orderMenuItems);
        }

        return orderItems;
    }

    public OrderItem getOrder(Long orderId, Role role) {

        OrderItem orderItem = queryFactory
                .select(Projections.constructor(OrderItem.class,
                        order.id,
                        order.status,
                        order.totalPrice,
                        order.createdDate,
                        order.expectTime
                ))
                .from(order)
                .where(
                        order.id.eq(orderId)
                )
                .fetchOne();

        List<OrderMenuItem> orderMenuItems = queryFactory
                .select(Projections.constructor(OrderMenuItem.class,
                        orderMenu.menu.id,
                        orderMenu.quantity,
                        orderMenu.menu.menuInfo.name,
                        orderMenu.menu.menuInfo.price
                ))
                .from(orderMenu)
                .where(orderMenu.order.id.eq(orderId))
                .fetch();

        if (orderMenuItems == null || orderMenuItems.isEmpty()) {
            orderMenuItems = new ArrayList<>();
        }

        List<Long> menuOptionIds = new ArrayList<>();

        for (OrderMenuItem orderMenuItem : orderMenuItems) {
            menuOptionIds.addAll(queryFactory
                    .select(
                            orderMenuOption.menuOption.id
                    )
                    .from(orderMenuOption)
                    .where(orderMenuOption.orderMenu.id.eq(orderMenuItem.getMenuId()))
                    .fetch());
            orderMenuItem.setMenuOptionList(menuOptionIds);
        }

        return orderItem;
    }

    public Boolean isOrdersExploded(Long foodTruckId, Integer waitLimit) {
        return queryFactory
                .select(order.id.count().goe(waitLimit))
                .from(order)
                .where(
                        order.sale.id.eq(saleQueryRepository.getLatestSale(foodTruckId).orElseThrow().getId()),
                        order.sale.foodTruck.id.eq(foodTruckId),
                        order.status.eq(Status.PENDING)
                )
                .fetchFirst();
    }
}
