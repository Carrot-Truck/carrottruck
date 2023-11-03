package com.boyworld.carrot.domain.sale.repository.query;

import com.boyworld.carrot.api.controller.foodtruck.response.FoodTruckItem;
import com.boyworld.carrot.api.service.foodtruck.dto.FoodTruckMarkerItem;
import com.boyworld.carrot.domain.foodtruck.repository.dto.SearchCondition;
import com.boyworld.carrot.domain.sale.QSale;
import com.boyworld.carrot.domain.sale.Sale;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.boyworld.carrot.domain.SizeConstants.SEARCH_RANGE_METER;
import static com.boyworld.carrot.domain.foodtruck.QFoodTruck.foodTruck;
import static com.boyworld.carrot.domain.sale.QSale.sale;
import static org.springframework.util.StringUtils.hasText;

/**
 * 영업 조회 레포지토리
 *
 * @author 박은규
 */
@Repository
@RequiredArgsConstructor
public class SaleQueryRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 최신 영업 조회
     *
     * @param
     * @return
     */
    public Optional<Sale> getSaleOrderByCreatedTimeDesc(Long foodTruckId) {

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
        List<Long> ids = queryFactory
                .select(sale.id)
                .from(sale)
                .join(sale.foodTruck, foodTruck)
                .where(
                        isEqualCategoryId(condition.getCategoryId()),
                        nameLikeKeyword(condition.getKeyword()),
                        isNearBy(condition, sale.latitude, sale.longitude),
                        isActiveSale()
                )
                .fetch();

        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }

        return queryFactory
                .select(Projections.constructor(FoodTruckMarkerItem.class,
                        sale.foodTruck.category.id,
                        sale.foodTruck.id,
                        calculateDistance(condition.getLatitude(), sale.latitude,
                                condition.getLongitude(), sale.longitude),
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

    private BooleanExpression isActiveSale() {
        return sale.active.isTrue().and(sale.endTime.isNull());
    }

    public List<FoodTruckItem> getFoodTrucksByCondition(SearchCondition condition) {
        return null;
    }
}
