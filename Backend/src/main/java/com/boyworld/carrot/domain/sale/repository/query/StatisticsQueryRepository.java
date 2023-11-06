package com.boyworld.carrot.domain.sale.repository.query;

import com.boyworld.carrot.api.service.sale.dto.SalesDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.boyworld.carrot.domain.SizeConstants.PAGE_SIZE;
import static com.boyworld.carrot.domain.sale.QSale.sale;

/**
 * 통계 조회 레포지토리
 *
 * @author 양진형
 */
@Repository
@RequiredArgsConstructor
public class StatisticsQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<SalesDto> getSaleList(Long foodTruckId, Integer year, Integer month, Long lastSalesId) {
        List<Long> ids = queryFactory
                .select(sale.id)
                .from(sale)
                .where(sale.foodTruck.id.eq(foodTruckId)
                                .and(sale.startTime.year().eq(year))
                                .and(sale.startTime.month().eq(month)),
                        isLessThanLastSalesId(lastSalesId),
                        isActiveSale()
                )
                .limit(PAGE_SIZE + 1)
                .fetch();

        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }

        return queryFactory
                .select(Projections.constructor(SalesDto.class,
                        sale.id,
                        sale.startTime,
                        sale.endTime,
                        sale.address,
                        sale.totalAmount
                ))
                .from(sale)
                .where(sale.id.in(ids))
                .orderBy(sale.id.desc())
                .fetch();
    }

    private BooleanExpression isLessThanLastSalesId(Long lastSalesId) {
        return lastSalesId != null ? sale.id.lt(lastSalesId) : null;
    }

    private BooleanExpression isActiveSale() {
        return sale.active.isTrue();
    }
}
