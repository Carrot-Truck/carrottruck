package com.boyworld.carrot.domain.sale.repository.query;

import com.boyworld.carrot.api.controller.foodtruck.response.FoodTruckItem;
import com.boyworld.carrot.api.service.foodtruck.dto.FoodTruckMarkerItem;
import com.boyworld.carrot.domain.foodtruck.repository.dto.OrderCondition;
import com.boyworld.carrot.domain.foodtruck.repository.dto.SearchCondition;
import com.boyworld.carrot.domain.foodtruck.repository.query.FoodTruckQueryRepository;
import com.boyworld.carrot.domain.sale.QSale;
import com.boyworld.carrot.domain.sale.Sale;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.boyworld.carrot.domain.SizeConstants.PAGE_SIZE;
import static com.boyworld.carrot.domain.SizeConstants.SEARCH_RANGE_METER;
import static com.boyworld.carrot.domain.foodtruck.QFoodTruck.foodTruck;
import static com.boyworld.carrot.domain.foodtruck.QFoodTruckImage.foodTruckImage;
import static com.boyworld.carrot.domain.foodtruck.QFoodTruckLike.foodTruckLike;
import static com.boyworld.carrot.domain.member.QMember.member;
import static com.boyworld.carrot.domain.review.QReview.review;
import static com.boyworld.carrot.domain.sale.QSale.sale;
import static com.querydsl.jpa.JPAExpressions.select;
import static org.springframework.util.StringUtils.hasText;

/**
 * 영업 조회 레포지토리
 *
 * @author 박은규
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class SaleQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final FoodTruckQueryRepository foodTruckQueryRepository;

    /**
     * 최신 영업 조회
     *
     * @param foodTruckId 푸드트럭 Id
     * @return Optional<Sale> 최신 영업
     */
    public Optional<Sale> getLatestSale(Long foodTruckId) {

        QSale sale = QSale.sale;

        Sale firstSale = queryFactory
                .selectFrom(sale)
                .orderBy(sale.createdDate.desc())
                .fetchFirst();

        return Optional.ofNullable(firstSale);
    }

    /**
     * 영업중인 푸드트럭 위치정보 조회 쿼리
     *
     * @param condition 검색 조건
     * @return 현재 위치 기준 1Km 이내의 푸드트럭 마커 목록
     */
    public List<FoodTruckMarkerItem> getPositionsByCondition(SearchCondition condition) {
        NumberTemplate<BigDecimal> distance = calculateDistance(condition.getLatitude(), sale.latitude,
                condition.getLongitude(), sale.longitude);

        List<Long> ids = queryFactory
                .select(sale.id)
                .from(sale)
                .join(sale.foodTruck, foodTruck)
                .where(
                        isEqualCategoryId(condition.getCategoryId()),
                        nameLikeKeyword(condition.getKeyword()),
                        distance.loe(SEARCH_RANGE_METER),
                        isActiveFoodTruck(),
                        isActiveSale()
                )
                .fetch();

        log.debug("ids={}", ids);

        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }

        return queryFactory
                .select(Projections.constructor(FoodTruckMarkerItem.class,
                        sale.id,
                        sale.foodTruck.category.id,
                        sale.foodTruck.id,
                        sale.foodTruck.name,
                        distance,
                        sale.latitude,
                        sale.longitude,
                        sale.endTime.isNull()
                ))
                .from(sale)
                .join(sale.foodTruck, foodTruck)
                .where(
                        sale.id.in(ids)
                )
                .fetch();
    }

    /**
     * 근처 푸드트럭 목록 조회 API
     *
     * @param condition      검색 조건
     * @param email          현재 로그인 중인 사용자 이메일
     * @param lastSaleId 마지막으로 조회된 푸드트럭 식별키
     * @return 현재 위치 기반 반경 1Km 이내의 푸드트럭 목록
     */
    public List<FoodTruckItem> getFoodTrucksByCondition(SearchCondition condition, String email, Long lastSaleId) {
        NumberTemplate<BigDecimal> distance = calculateDistance(condition.getLatitude(), sale.latitude,
                condition.getLongitude(), sale.longitude);

        List<Long> ids = queryFactory
                .selectDistinct(sale.foodTruck.id)
                .from(sale)
                .join(sale.foodTruck, foodTruck)
                .leftJoin(foodTruckLike).on(sale.foodTruck.eq(foodTruckLike.foodTruck), foodTruckLike.active)
                .leftJoin(member).on(foodTruckLike.member.eq(member), member.active)
                .leftJoin(review).on(sale.foodTruck.eq(review.foodTruck), review.active)
                .leftJoin(foodTruckImage).on(sale.foodTruck.eq(foodTruckImage.foodTruck), foodTruckImage.active)
                .where(
                        isEqualCategoryId(condition.getCategoryId()),
                        nameLikeKeyword(condition.getKeyword()),
                        distance.loe(SEARCH_RANGE_METER),
                        isLastId(lastSaleId, condition.getOrderCondition()),
                        isActiveFoodTruck(),
                        isActiveSale()
                )
                .limit(PAGE_SIZE + 1)
                .orderBy(
                        createOrderSpecifier(condition.getOrderCondition(), distance)
                )
                .fetch();

        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastMonth = now.minusMonths(1);

        return queryFactory
                .select(Projections.constructor(FoodTruckItem.class,
                        sale.foodTruck.category.id,
                        sale.foodTruck.id,
                        sale.foodTruck.name,
                        sale.isNull(),
                        isLiked(email),
                        getLikeCount(),
                        getAvgGrade(),
                        getReviewCount(),
                        foodTruckImage.uploadFile.storeFileName,
                        isNew(lastMonth)
                ))
                .from(sale)
                .join(sale.foodTruck, foodTruck)
                .leftJoin(foodTruckLike).on(sale.foodTruck.eq(foodTruckLike.foodTruck), foodTruckLike.active)
                .leftJoin(member).on(foodTruckLike.member.eq(member), member.active)
                .leftJoin(review).on(sale.foodTruck.eq(review.foodTruck), review.active)
                .leftJoin(foodTruckImage).on(sale.foodTruck.eq(foodTruckImage.foodTruck), foodTruckImage.active)
                .where(
                        sale.foodTruck.id.in(ids)
                )
                .groupBy(
                        sale.foodTruck.id
                )
                .orderBy(
                        createOrderSpecifier(condition.getOrderCondition(), distance)
                )
                .fetch();
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

    private BooleanExpression isLastId(Long lastSaleId, OrderCondition orderCondition) {
        if (orderCondition == null) {
            return lastSaleId != null ? sale.id.gt(lastSaleId) : null;
        }
        return lastSaleId != null ? sale.id.lt(lastSaleId) : null;
    }

    private BooleanExpression isActiveFoodTruck() {
        return foodTruck.active;
    }

    private BooleanExpression isActiveSale() {
        return sale.active.and(sale.endTime.isNull());
    }

    private JPQLQuery<Boolean> isLiked(String email) {
        return select(foodTruckLike.count().goe(1L))
                .from(foodTruckLike)
                .where(
                        foodTruckLike.foodTruck.eq(sale.foodTruck),
                        foodTruckLike.member.email.eq(email)
                );
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
                .where(foodTruckLike.foodTruck.eq(sale.foodTruck));
    }

    private JPQLQuery<Double> getAvgGrade() {
        return select(review.grade.sum().divide(review.count()).doubleValue())
                .from(review)
                .where(review.foodTruck.eq(sale.foodTruck));
    }

    private JPQLQuery<Integer> getReviewCount() {
        return select(review.count().intValue())
            .from(review)
            .where(review.foodTruck.eq(sale.foodTruck));
    }
}
