package com.boyworld.carrot.domain.foodtruck.repository.query;

import com.boyworld.carrot.api.controller.foodtruck.response.FoodTruckItem;
import com.boyworld.carrot.api.service.foodtruck.dto.FoodTruckMarkerItem;
import com.boyworld.carrot.api.service.schedule.dto.ScheduleDto;
import com.boyworld.carrot.domain.foodtruck.Schedule;
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
import java.util.Optional;

import static com.boyworld.carrot.domain.SizeConstants.PAGE_SIZE;
import static com.boyworld.carrot.domain.SizeConstants.SEARCH_RANGE_METER;
import static com.boyworld.carrot.domain.foodtruck.QFoodTruck.foodTruck;
import static com.boyworld.carrot.domain.foodtruck.QFoodTruckImage.foodTruckImage;
import static com.boyworld.carrot.domain.foodtruck.QFoodTruckLike.foodTruckLike;
import static com.boyworld.carrot.domain.foodtruck.QSchedule.schedule;
import static com.boyworld.carrot.domain.member.QMember.member;
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
        NumberTemplate<BigDecimal> distance = calculateDistance(condition.getLatitude(), schedule.latitude,
                condition.getLongitude(), schedule.longitude);

        List<Long> ids = queryFactory
                .select(schedule.id)
                .from(schedule)
                .join(schedule.foodTruck, foodTruck)
                .leftJoin(sale).on(schedule.foodTruck.eq(sale.foodTruck), sale.active)
                .where(
                        isEqualCategoryId(condition.getCategoryId()),
                        nameLikeKeyword(condition.getKeyword()),
                        distance.loe(SEARCH_RANGE_METER),
                        isActiveFoodTruck(),
                        schedule.active
                )
                .fetch();

        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }

        LocalDateTime today = LocalDate.now().atStartOfDay();
        LocalDateTime now = LocalDateTime.now();

        return queryFactory
                .select(Projections.constructor(FoodTruckMarkerItem.class,
                        schedule.id,
                        schedule.foodTruck.category.id,
                        schedule.foodTruck.id,
                        schedule.foodTruck.name,
                        distance,
                        schedule.latitude,
                        schedule.longitude,
                        isOpen(today, now)
                ))
                .from(schedule)
                .join(schedule.foodTruck, foodTruck)
                .leftJoin(sale).on(schedule.foodTruck.eq(sale.foodTruck), sale.active)
                .where(
                        schedule.id.in(ids)
                )
                .fetch();
    }

    /**
     * 근처 푸드트럭 목록 조회 API
     *
     * @param condition      검색 조건
     * @param email          현재 로그인 중인 사용자 이메일
     * @param lastScheduleId 마지막으로 조회된 푸드트럭 식별키
     * @return 현재 위치 기반 반경 1Km 이내의 푸드트럭 목록
     */
    public List<FoodTruckItem> getFoodTrucksByCondition(SearchCondition condition, String email, Long lastScheduleId) {
        NumberTemplate<BigDecimal> distance = calculateDistance(condition.getLatitude(), schedule.latitude,
                condition.getLongitude(), schedule.longitude);

        List<Long> ids = queryFactory
                .selectDistinct(schedule.foodTruck.id)
                .from(schedule)
                .join(schedule.foodTruck, foodTruck)
                .leftJoin(sale).on(schedule.foodTruck.eq(sale.foodTruck), sale.active)
                .leftJoin(foodTruckLike).on(schedule.foodTruck.eq(foodTruckLike.foodTruck), foodTruckLike.active)
                .leftJoin(foodTruckLike.member, member).on(foodTruckLike.member.eq(member), member.active)
                .leftJoin(review).on(schedule.foodTruck.eq(review.foodTruck), review.active)
                .leftJoin(foodTruckImage).on(schedule.foodTruck.eq(foodTruckImage.foodTruck), foodTruckImage.active)
                .where(
                        isEqualCategoryId(condition.getCategoryId()),
                        nameLikeKeyword(condition.getKeyword()),
                        distance.loe(SEARCH_RANGE_METER),
                        isLastId(lastScheduleId, condition.getOrderCondition()),
                        isActiveFoodTruck(),
                        schedule.active
                )
                .limit(PAGE_SIZE + 1)
                .fetch();

        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime today = LocalDate.now().atStartOfDay();
        LocalDateTime lastMonth = now.minusMonths(1);

        return queryFactory
                .selectDistinct(Projections.constructor(FoodTruckItem.class,
                        schedule.foodTruck.category.id,
                        schedule.foodTruck.id,
                        schedule.foodTruck.name,
                        isOpen(today, now),
                        isLiked(email),
                        getLikeCount(),
                        getAvgGrade(),
                        getReviewCount(),
                        foodTruckImage.uploadFile.storeFileName,
                        isNew(lastMonth)
                ))
                .from(schedule)
                .join(schedule.foodTruck, foodTruck)
                .leftJoin(sale).on(schedule.foodTruck.eq(sale.foodTruck), sale.active)
                .leftJoin(foodTruckLike).on(schedule.foodTruck.eq(foodTruckLike.foodTruck), foodTruckLike.active)
                .leftJoin(foodTruckLike.member, member).on(foodTruckLike.member.eq(member), member.active)
                .leftJoin(review).on(schedule.foodTruck.eq(review.foodTruck), review.active)
                .leftJoin(foodTruckImage).on(schedule.foodTruck.eq(foodTruckImage.foodTruck), foodTruckImage.active)
                .where(
                        schedule.foodTruck.id.in(ids)
                )
                .groupBy(
                        schedule.foodTruck.id
                )
                .orderBy(
                        createOrderSpecifier(condition.getOrderCondition(), distance)
                )
                .fetch();
    }

    /**
     * 푸드트럭 식별키로 스케줄 목록 조회 쿼리
     *
     * @param foodTruckId 푸드트럭 식별키
     * @return 해당 푸드트럭의 스케줄 목록
     */
    public List<ScheduleDto> getSchedulesByFoodTruckId(Long foodTruckId) {
        List<Long> ids = queryFactory
                .select(schedule.id)
                .from(schedule)
                .where(
                        isEqualFoodTruckId(foodTruckId),
                        schedule.active
                )
                .fetch();

        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }

        return queryFactory
                .select(Projections.constructor(ScheduleDto.class,
                        schedule.id,
                        schedule.address,
                        schedule.dayOfWeek,
                        schedule.startTime,
                        schedule.endTime
                ))
                .from(schedule)
                .where(
                        schedule.id.in(ids)
                )
                .fetch();
    }

    /**
     * 스케줄 식별키로 스케줄 상세 조회 쿼리
     *
     * @param scheduleId 스케줄 식별키
     * @return 스케줄 상세 정보
     */
    public Optional<Schedule> getScheduleById(Long scheduleId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(schedule)
                .where(
                        isEqualScheduleId(scheduleId),
                        schedule.foodTruck.active,
                        schedule.active
                )
                .fetchOne());
    }

    private BooleanExpression isEqualCategoryId(Long categoryId) {
        return categoryId != null ? foodTruck.category.id.eq(categoryId) : null;
    }

    private BooleanExpression nameLikeKeyword(String keyword) {
        return hasText(keyword) ? foodTruck.name.contains(keyword) : null;
    }

    private NumberTemplate<BigDecimal> calculateDistance(BigDecimal currentLat, NumberPath<BigDecimal> targetLat,
                                                         BigDecimal currentLng, NumberPath<BigDecimal> targetLng) {
        return Expressions.numberTemplate(BigDecimal.class,
                "ST_DISTANCE_SPHERE(POINT({0}, {1}), POINT({2}, {3}))",
                currentLng, currentLat, targetLng, targetLat);
    }

    private BooleanExpression isLastId(Long lastScheduleId, OrderCondition orderCondition) {
        if (orderCondition == null) {
            return lastScheduleId != null ? schedule.id.lt(lastScheduleId) : null;
        }
        return lastScheduleId != null ? schedule.id.gt(lastScheduleId) : null;
    }

    private BooleanExpression isOpen(LocalDateTime today, LocalDateTime now) {
        return notClosed()
                .and(isOpened(today, now))
                .and(isToDay(now));
    }

    private BooleanExpression notClosed() {
        return sale.endTime.isNull();
    }

    private BooleanExpression isOpened(LocalDateTime today, LocalDateTime now) {
        return sale.startTime.isNotNull()
                .and(new CaseBuilder()
                        .when(sale.startTime.between(today, now))
                        .then(true)
                        .otherwise(false));
    }

    private BooleanExpression isToDay(LocalDateTime now) {
        return schedule.dayOfWeek.eq(now.getDayOfWeek());
    }

    private JPQLQuery<Boolean> isLiked(String email) {
        return select(foodTruckLike.count().goe(1L))
                .from(foodTruckLike)
                .where(
                        foodTruckLike.foodTruck.eq(schedule.foodTruck),
                        foodTruckLike.member.email.eq(email),
                        member.active
                );
    }

    private BooleanExpression isActiveFoodTruck() {
        return foodTruck.active;
    }

    private BooleanExpression isNew(LocalDateTime lastMonth) {
        return new CaseBuilder()
                .when(foodTruck.createdDate.after(lastMonth))
                .then(true)
                .otherwise(false);
    }

    private OrderSpecifier<?> createOrderSpecifier(OrderCondition orderCondition, NumberTemplate<BigDecimal> distance) {
        OrderSpecifier<?> orderSpecifier = null;
        if (orderCondition == null) {
            orderSpecifier = new OrderSpecifier<>(Order.ASC, distance);
        } else if (orderCondition.equals(OrderCondition.LIKE)) {
            orderSpecifier = new OrderSpecifier<>(Order.DESC, getLikeCount());
        } else if (orderCondition.equals(OrderCondition.GRADE)) {
            orderSpecifier = new OrderSpecifier<>(Order.DESC, getAvgGrade());
        } else if (orderCondition.equals(OrderCondition.REVIEW)) {
            orderSpecifier = new OrderSpecifier<>(Order.DESC, getReviewCount());
        }
        return orderSpecifier;
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

    private BooleanExpression isEqualFoodTruckId(Long foodTruckId) {
        return foodTruckId != null ? schedule.foodTruck.id.eq(foodTruckId) : null;
    }

    private BooleanExpression isEqualScheduleId(Long scheduleId) {
        return scheduleId != null ? schedule.id.eq(scheduleId) : null;
    }
}
