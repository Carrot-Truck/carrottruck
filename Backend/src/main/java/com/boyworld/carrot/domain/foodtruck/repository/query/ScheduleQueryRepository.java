package com.boyworld.carrot.domain.foodtruck.repository.query;

import com.boyworld.carrot.api.service.foodtruck.dto.FoodTruckMarkerItem;
import com.boyworld.carrot.domain.foodtruck.repository.dto.SearchCondition;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.boyworld.carrot.domain.SizeConstants.PAGE_SIZE;
import static com.boyworld.carrot.domain.SizeConstants.SEARCH_RANGE_METER;
import static com.boyworld.carrot.domain.foodtruck.QFoodTruck.foodTruck;
import static com.boyworld.carrot.domain.foodtruck.QSchedule.schedule;
import static com.boyworld.carrot.domain.sale.QSale.sale;
import static org.springframework.util.StringUtils.hasText;

/**
 * 푸드트럭 스케줄 조회 레포지토리
 *
 * @author 최영환
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class ScheduleQueryRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * 푸드트럭 위치정보 조회 쿼리
     *
     * @param condition 검색 조건
     * @return 현재 위치 기준 1Km 이내의 푸드트럭 마커 목록
     */
    public List<FoodTruckMarkerItem> getPositionsByCondition(SearchCondition condition) {
        List<Long> ids = queryFactory
                .select(schedule.id)
                .from(schedule)
                .join(schedule.foodTruck, foodTruck)
                .join(sale).on(schedule.foodTruck.id.eq(sale.foodTruck.id))
                .where(
                        isEqualCategoryId(condition.getCategoryId()),
                        nameLikeKeyword(condition.getKeyword()),
                        isNearBy(condition, schedule.latitude, schedule.longitude),
                        isActiveSchedule()
                )
                .limit(PAGE_SIZE + 1)
                .fetch();

        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }

        // TODO: 2023-11-03 리팩토링 필요

        LocalDateTime today = LocalDate.now().atStartOfDay();
        LocalDateTime now = LocalDateTime.now();
        return queryFactory
                .select(Projections.constructor(FoodTruckMarkerItem.class,
                        schedule.foodTruck.category.id,
                        schedule.foodTruck.id,
                        calculateDistance(condition.getLatitude(), schedule.latitude,
                                condition.getLongitude(), schedule.longitude),
                        schedule.latitude,
                        schedule.longitude,
                        sale.endTime.isNull()
                                .and(new CaseBuilder()
                                        .when(sale.startTime.between(today, now))
                                        .then(true)
                                        .otherwise(false))
                                .and(schedule.dayOfWeek.eq(LocalDateTime.now().getDayOfWeek()))
                ))
                .from(schedule)
                .join(schedule.foodTruck, foodTruck)
                .join(sale).on(schedule.foodTruck.id.eq(sale.foodTruck.id))
                .where(
                        schedule.id.in(ids)
                )
                .fetch();
    }

    private BooleanExpression isEqualCategoryId(Long categoryId) {
        return categoryId != null ? foodTruck.category.id.eq(categoryId) : null;
    }

    private BooleanExpression nameLikeKeyword(String keyword) {
        return hasText(keyword) ? foodTruck.name.contains(keyword) : null;
    }

    private BooleanExpression isNearBy(SearchCondition condition,
                                       NumberPath<BigDecimal> targetLat, NumberPath<BigDecimal> targetLng) {
        NumberTemplate<BigDecimal> distance = calculateDistance(condition.getLatitude(), targetLat,
                condition.getLongitude(), targetLng);
        return distance.loe(SEARCH_RANGE_METER);
    }

    private NumberTemplate<BigDecimal> calculateDistance(BigDecimal currentLat, NumberPath<BigDecimal> targetLat,
                                                         BigDecimal currentLng, NumberPath<BigDecimal> targetLng) {
        return Expressions.numberTemplate(BigDecimal.class,
                "ST_DISTANCE(POINT({0}, {1}), POINT({2}, {3}))",
                currentLat, currentLng, targetLat, targetLng);
    }

    private BooleanExpression isActiveSchedule() {
        return schedule.active.isTrue();
    }
}
