package com.boyworld.carrot.domain.foodtruck.repository.query;

import com.boyworld.carrot.api.controller.foodtruck.response.FoodTruckItem;
import com.boyworld.carrot.api.service.foodtruck.dto.FoodTruckMarkerItem;
import com.boyworld.carrot.domain.foodtruck.repository.dto.OrderCondition;
import com.boyworld.carrot.domain.foodtruck.repository.dto.SearchCondition;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.JPQLQuery;
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
import static com.boyworld.carrot.domain.foodtruck.QFoodTruckImage.foodTruckImage;
import static com.boyworld.carrot.domain.foodtruck.QFoodTruckLike.foodTruckLike;
import static com.boyworld.carrot.domain.foodtruck.QSchedule.schedule;
import static com.boyworld.carrot.domain.review.QReview.review;
import static com.boyworld.carrot.domain.sale.QSale.sale;
import static com.querydsl.jpa.JPAExpressions.select;
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
                .leftJoin(sale).on(schedule.foodTruck.eq(sale.foodTruck)).fetchJoin()
                .where(
                        isEqualCategoryId(condition.getCategoryId()),
                        nameLikeKeyword(condition.getKeyword()),
                        isNearBy(condition, schedule.latitude, schedule.longitude),
                        isActiveSchedule()
                )
                .fetch();

        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }

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
                        isOpen(today, now)
                ))
                .from(schedule)
                .join(schedule.foodTruck, foodTruck)
                .leftJoin(sale).on(schedule.foodTruck.eq(sale.foodTruck)).fetchJoin()
                .where(
                        schedule.id.in(ids)
                )
                .fetch();
    }

    /**
     * 근처 푸드트럭 목록 조회 API
     *
     * @param condition 검색 조건
     * @return 현재 위치 기반 반경 1Km 이내의 푸드트럭 목록
     */
    public List<FoodTruckItem> getFoodTrucksByCondition(SearchCondition condition) {
        List<Long> ids = queryFactory
                .select(schedule.id)
                .from(schedule)
                .join(schedule.foodTruck, foodTruck)
                .leftJoin(sale).on(schedule.foodTruck.eq(sale.foodTruck)).fetchJoin()
                .leftJoin(foodTruckLike).on(schedule.foodTruck.eq(foodTruckLike.foodTruck)).fetchJoin()
                .leftJoin(review).on(schedule.foodTruck.eq(review.foodTruck)).fetchJoin()
                .leftJoin(foodTruckImage).on(schedule.foodTruck.eq(foodTruckImage.foodTruck)).fetchJoin()
                .where(
                        isEqualCategoryId(condition.getCategoryId()),
                        nameLikeKeyword(condition.getKeyword()),
                        isNearBy(condition, schedule.latitude, schedule.longitude),
                        isActive(),
                        isActiveSchedule()
                )
                .fetch();

        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime today = LocalDate.now().atStartOfDay();
        LocalDateTime lastMonth = now.minusMonths(1);

        NumberTemplate<BigDecimal> distance = calculateDistance(condition.getLatitude(), schedule.latitude,
                condition.getLongitude(), schedule.longitude);

        // TODO: 2023-11-03 리팩토링

        return queryFactory
                .select(Projections.constructor(FoodTruckItem.class,
                        schedule.foodTruck.category.id,
                        schedule.foodTruck.id,
                        schedule.foodTruck.name,
                        isOpen(today, now),
                        isLiked(),
                        schedule.foodTruck.prepareTime,
                        getLikeCount(),
                        getAvgGrade(),
                        getReviewCount(),
                        distance,
                        schedule.address,
                        foodTruckImage.uploadFile.storeFileName,
                        isNew(lastMonth)
                ))
                .from(schedule)
                .join(schedule.foodTruck, foodTruck)
                .leftJoin(sale).on(schedule.foodTruck.eq(sale.foodTruck)).fetchJoin()
                .leftJoin(foodTruckLike).on(schedule.foodTruck.eq(foodTruckLike.foodTruck)).fetchJoin()
                .leftJoin(review).on(schedule.foodTruck.eq(review.foodTruck)).fetchJoin()
                .leftJoin(foodTruckImage).on(schedule.foodTruck.eq(foodTruckImage.foodTruck)).fetchJoin()
                .where(
                        schedule.id.in(ids)
                )
                .orderBy(
                        createOrderSpecifiers(condition.getOrderCondition(), distance)
                )
                .limit(PAGE_SIZE + 1)
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

    private static BooleanExpression isOpen(LocalDateTime today, LocalDateTime now) {
        return notClosed()
                .and(isOpened(today, now))
                .and(isToDay(now));
    }

    private static BooleanExpression notClosed() {
        return sale.endTime.isNull();
    }

    private static BooleanExpression isOpened(LocalDateTime today, LocalDateTime now) {
        return sale.startTime.isNotNull()
                .and(new CaseBuilder()
                        .when(sale.startTime.between(today, now))
                        .then(true)
                        .otherwise(false));
    }

    private BooleanExpression isLiked() {
        return foodTruckLike.isNotNull().and(foodTruckLike.active.isTrue());
    }

    private static BooleanExpression isToDay(LocalDateTime now) {
        return schedule.dayOfWeek.eq(now.getDayOfWeek());
    }

    private BooleanExpression isActiveSchedule() {
        return schedule.active;
    }

    private BooleanExpression isActive() {
        return foodTruck.active;
    }

    private BooleanExpression isNew(LocalDateTime lastMonth) {
        return new CaseBuilder()
                .when(foodTruck.createdDate.after(lastMonth))
                .then(true)
                .otherwise(false);
    }

    private OrderSpecifier<?>[] createOrderSpecifiers(OrderCondition orderCondition, NumberTemplate<BigDecimal> distance) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

        if (orderCondition == null) {
            orderSpecifiers.add(new OrderSpecifier<>(Order.ASC, distance));
        } else if (orderCondition.equals(OrderCondition.LIKE)) {
            orderSpecifiers.add(new OrderSpecifier<>(Order.DESC, getLikeCount()));
        } else if (orderCondition.equals(OrderCondition.GRADE)) {
            orderSpecifiers.add(new OrderSpecifier<>(Order.DESC, review.grade.avg()));
        } else if (orderCondition.equals(OrderCondition.REVIEW)) {
            orderSpecifiers.add(new OrderSpecifier<>(Order.DESC, review.count()));
        }

        return orderSpecifiers.toArray(new OrderSpecifier[0]);
    }

    private JPQLQuery<Integer> getLikeCount() {
        return select(foodTruckLike.count().intValue())
                .from(foodTruckLike)
                .where(foodTruckLike.foodTruck.eq(schedule.foodTruck));
    }

    private JPQLQuery<Double> getAvgGrade() {
        return select(review.grade.sum().divide(review.count()).doubleValue())
                .from(review)
                .where(review.foodTruck.eq(schedule.foodTruck));
    }

    private JPQLQuery<Integer> getReviewCount() {
        return select(review.count().intValue())
                .from(review)
                .where(review.foodTruck.eq(schedule.foodTruck));
    }
}
