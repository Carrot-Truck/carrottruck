package com.boyworld.carrot.domain.order.repository.query;

import static com.boyworld.carrot.domain.order.QOrder.order;

import com.boyworld.carrot.domain.order.Status;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Long updateOrderAccepted(Long orderId, Integer prepareTime) {
        queryFactory
            .update(order)
            .set(order.status, Status.PROCESSING)
            .set(order.expectTime, LocalDateTime.now().plusMinutes(prepareTime))
            .where(order.id.eq(orderId))
            .execute();

        return orderId;
    }

    public Long updateOrderDeclined(Long orderId, String reason) {
        queryFactory
            .update(order)
            .set(order.status, Status.DECLINED)
            .where(order.id.eq(orderId))
            .execute();

        return orderId;
    }

}
