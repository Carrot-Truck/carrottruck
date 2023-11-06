package com.boyworld.carrot.domain.sale.repository.query;

import com.boyworld.carrot.api.service.sale.dto.MonthlyStatisticsDto;
import com.boyworld.carrot.api.service.sale.dto.SalesStatisticsDto;
import com.boyworld.carrot.api.service.sale.dto.WeeklyStatisticsDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
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

    public List<SalesStatisticsDto> getSaleList(Long foodTruckId, Integer year, Integer month, Long lastSalesId) {
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
                .select(Projections.constructor(SalesStatisticsDto.class,
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

    public List<WeeklyStatisticsDto> getWeeklyList(Long foodTruckId, Integer year, Integer lastWeek) {
        LocalDate startOfYearDate = LocalDate.of(year, Month.JANUARY, 1);

        while (startOfYearDate.getDayOfWeek() != DayOfWeek.WEDNESDAY) {
            startOfYearDate = startOfYearDate.plusDays(1);
        }
        LocalDateTime startOfYearDateTime = startOfYearDate.minusDays(3).atStartOfDay();
        LocalDateTime lastOfYearDateTime = null;
        if (lastWeek == null) {
            LocalDate lastOfYearDate = LocalDate.of(year+1, Month.JANUARY, 1);

            while (lastOfYearDate.getDayOfWeek() != DayOfWeek.WEDNESDAY) {
                lastOfYearDate = lastOfYearDate.plusDays(1);
            }
            lastOfYearDateTime = lastOfYearDate.atStartOfDay().minusDays(3);
        } else {
            lastOfYearDateTime = lastOfYearDateTime.plusWeeks(lastWeek).minusDays(3);
        }

        List<Long> weeks = queryFactory
                .selectDistinct(getWeek(startOfYearDateTime).floor())
                .from(sale)
                .where(sale.foodTruck.id.eq(foodTruckId)
                                .and(sale.startTime.goe(startOfYearDateTime)),
                        isLessThanLastStartDate(lastOfYearDateTime),
                        isActiveSale()
                )
                .orderBy(getWeek(startOfYearDateTime).floor().desc())
                .limit(PAGE_SIZE + 1)
                .fetch();

        if (weeks == null || weeks.isEmpty()) {
            return new ArrayList<>();
        }

        return queryFactory
                .select(Projections.constructor(WeeklyStatisticsDto.class,
                        getWeek(startOfYearDateTime).floor(),
                        Expressions.numberTemplate(Integer.class, "TIMESTAMPDIFF(MINUTE, {0}, {1})",
                                sale.startTime, sale.endTime).sum(),
                        sale.totalAmount.sum()
                ))
                .from(sale)
                .where(getWeek(startOfYearDateTime).floor().in(weeks))
                .groupBy(getWeek(startOfYearDateTime).floor())
                .orderBy(getWeek(startOfYearDateTime).floor().desc())
                .fetch();
    }

    public List<MonthlyStatisticsDto> getStatisticsByMonth(Long foodTruckId, Integer year) {
        List<Long> ids = queryFactory
                .select(sale.id)
                .from(sale)
                .where(sale.foodTruck.id.eq(foodTruckId)
                        .and(sale.startTime.year().eq(year)),
                        isActiveSale()
                )
                .fetch();

        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }

        return queryFactory
                .select(Projections.constructor(MonthlyStatisticsDto.class,
                        sale.startTime.month(),
                        Expressions.numberTemplate(Integer.class, "TIMESTAMPDIFF(MINUTE, {0}, {1})",
                                sale.startTime, sale.endTime).sum(),
                        sale.totalAmount.sum()
                ))
                .from(sale)
                .where(sale.id.in(ids))
                .groupBy(sale.startTime.month())
                .orderBy(sale.startTime.month().desc())
                .fetch();
    }

    private BooleanExpression isLessThanLastSalesId(Long lastSalesId) {
        return lastSalesId != null ? sale.id.lt(lastSalesId) : null;
    }

    private BooleanExpression isLessThanLastStartDate(LocalDateTime lastSundayOfWeek) {
        return lastSundayOfWeek != null ? sale.startTime.lt(lastSundayOfWeek) : null;
    }

    private NumberExpression<Long> getWeek(LocalDateTime startOfYearDateTime) {
        return Expressions.numberTemplate(
                Long.class,
                "TIMESTAMPDIFF(DAY, {0}, {1}) / 7",
                startOfYearDateTime,
                sale.startTime
        );
    }

    private BooleanExpression isActiveSale() {
        return sale.active.isTrue();
    }
}
