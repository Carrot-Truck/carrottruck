package com.boyworld.carrot.domain.sale.repository.query;

import com.boyworld.carrot.api.controller.statistics.response.StatisticsByMonthDetailsResponse;
import com.boyworld.carrot.api.controller.statistics.response.StatisticsBySalesDetailsResponse;
import com.boyworld.carrot.api.controller.statistics.response.StatisticsByWeekDetailsResponse;
import com.boyworld.carrot.api.service.statistics.dto.details.LocationDto;
import com.boyworld.carrot.api.service.statistics.dto.details.SalesByDayDto;
import com.boyworld.carrot.api.service.statistics.dto.details.SalesByHourDto;
import com.boyworld.carrot.api.service.statistics.dto.list.SummaryByMonthDto;
import com.boyworld.carrot.api.service.statistics.dto.list.SummaryBySalesDto;
import com.boyworld.carrot.api.service.statistics.dto.list.SummaryByWeekDto;
import com.boyworld.carrot.api.service.statistics.dto.details.SalesByMenuDto;
import com.boyworld.carrot.domain.order.Status;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.boyworld.carrot.domain.SizeConstants.PAGE_SIZE;
import static com.boyworld.carrot.domain.order.QOrder.order;
import static com.boyworld.carrot.domain.order.QOrderMenu.orderMenu;
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

    public List<SummaryBySalesDto> getSaleList(Long foodTruckId, Integer year, Integer month, Long lastSalesId) {
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
                .select(Projections.constructor(SummaryBySalesDto.class,
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

    public StatisticsBySalesDetailsResponse getSaleDetail(Long foodTruckId, Long salesId) {
        Long fid = queryFactory
                .select(sale.foodTruck.id)
                .from(sale)
                .where(sale.id.eq(salesId))
                .fetchOne();

        if (fid == null || fid.longValue() != foodTruckId.longValue()) {
            return null;
        }

        List<Long> orderIds = queryFactory
                .select(order.id)
                .from(order)
                .where(order.sale.id.eq(salesId),
                        isCompleteOrder())
                .fetch();

        LocationDto locations = queryFactory
                .select(Projections.constructor(LocationDto.class,
                        sale.latitude,
                        sale.longitude
                ))
                .from(sale)
                .where(sale.id.eq(salesId))
                .fetchOne();

        if (locations == null) {
            return null;
        }

        List<SalesByMenuDto> salesByMenu = getSalesByMenu(orderIds);

        List<SalesByHourDto> salesByHour = getSalesByHour(orderIds);

        return StatisticsBySalesDetailsResponse.of(locations, salesByMenu, salesByHour);
    }

    public List<SummaryByWeekDto> getWeeklyList(Long foodTruckId, Integer year, Integer lastWeek) {
        LocalDate startOfYearDate = LocalDate.of(year, Month.JANUARY, 1);

        while (startOfYearDate.getDayOfWeek() != DayOfWeek.WEDNESDAY) {
            startOfYearDate = startOfYearDate.plusDays(1);
        }
        LocalDateTime startOfYearDateTime = startOfYearDate.minusDays(3).atStartOfDay();
        LocalDateTime lastOfYearDateTime;
        if (lastWeek == null) {
            LocalDate lastOfYearDate = LocalDate.of(year+1, Month.JANUARY, 1);

            while (lastOfYearDate.getDayOfWeek() != DayOfWeek.WEDNESDAY) {
                lastOfYearDate = lastOfYearDate.plusDays(1);
            }
            lastOfYearDateTime = lastOfYearDate.atStartOfDay().minusDays(3);
        } else {
            lastOfYearDateTime = startOfYearDate.plusWeeks(lastWeek).minusDays(3).atStartOfDay();
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
                .select(Projections.constructor(SummaryByWeekDto.class,
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

    public StatisticsByWeekDetailsResponse getWeeklyDetail(Long foodTruckId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Long> salesIds = queryFactory
                .select(sale.id)
                .from(sale)
                .where(sale.foodTruck.id.eq(foodTruckId),
                        sale.startTime.between(startDate, endDate.plusDays(1)))
                .fetch();

        List<Long> orderIds = getOrderIds(salesIds);

        List<SalesByMenuDto> salesByMenu = getSalesByMenu(orderIds);

        List<SalesByHourDto> salesByHour = getSalesByHour(orderIds);

        List<SalesByDayDto> salesByDay = getSalesByDay(orderIds);

        return StatisticsByWeekDetailsResponse.of(salesByMenu, salesByHour, salesByDay);
    }

    public List<SummaryByMonthDto> getStatisticsByMonth(Long foodTruckId, Integer year) {
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
                .select(Projections.constructor(SummaryByMonthDto.class,
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

    public StatisticsByMonthDetailsResponse getMonthlyDetail(Long foodTruckId, Integer year, Integer month) {
        List<Long> salesIds = queryFactory
                .select(sale.id)
                .from(sale)
                .where(sale.foodTruck.id.eq(foodTruckId),
                        sale.startTime.year().eq(year),
                        sale.startTime.month().eq(month)
                )
                .fetch();

        List<Long> orderIds = getOrderIds(salesIds);

        List<SalesByMenuDto> salesByMenu = getSalesByMenu(orderIds);

        List<SalesByHourDto> salesByHour = getSalesByHour(orderIds);

        List<SalesByDayDto> salesByDay = getSalesByDay(orderIds);

        return StatisticsByMonthDetailsResponse.of(salesByMenu, salesByHour, salesByDay);
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

    private BooleanExpression isCompleteOrder() {
        return order.status.eq(Status.COMPLETE);
    }

    private List<Long> getOrderIds(List<Long> salesIds) {
        return queryFactory
                .select(order.id)
                .from(order)
                .where(order.sale.id.in(salesIds),
                        isCompleteOrder())
                .fetch();
    }

    private List<SalesByMenuDto> getSalesByMenu(List<Long> orderIds) {
        return queryFactory
                .select(Projections.constructor(SalesByMenuDto.class,
                        orderMenu.menu.id,
                        orderMenu.menu.menuInfo.name,
                        orderMenu.quantity.sum(),
                        orderMenu.quantity.multiply(orderMenu.menu.menuInfo.price).sum()
                ))
                .from(orderMenu)
                .innerJoin(order)
                .on(order.id.eq(orderMenu.order.id))
                .where(order.id.in(orderIds))
                .groupBy(orderMenu.menu.id)
                .orderBy(orderMenu.quantity.multiply(orderMenu.menu.menuInfo.price).sum().desc())
                .fetch();
    }

    private List<SalesByHourDto> getSalesByHour(List<Long> orderIds) {
        return queryFactory
                .select(Projections.constructor(SalesByHourDto.class,
                        order.createdDate.hour(),
                        order.count(),
                        orderMenu.quantity.multiply(orderMenu.menu.menuInfo.price).sum()
                ))
                .from(orderMenu)
                .innerJoin(order)
                .on(order.id.eq(orderMenu.order.id))
                .where(order.id.in(orderIds))
                .groupBy(order.createdDate.hour())
                .orderBy(order.createdDate.hour().asc())
                .fetch();
    }

    private List<SalesByDayDto> getSalesByDay(List<Long> orderIds) {
        return queryFactory
                .select(Projections.constructor(SalesByDayDto.class,
                        order.createdDate.dayOfWeek(),
                        order.count(),
                        orderMenu.quantity.multiply(orderMenu.menu.menuInfo.price).sum()
                ))
                .from(orderMenu)
                .innerJoin(order)
                .on(order.id.eq(orderMenu.order.id))
                .where(order.id.in(orderIds))
                .groupBy(order.createdDate.dayOfWeek())
                .orderBy(order.createdDate.dayOfWeek().asc())
                .fetch();
    }
}
