package com.boyworld.carrot.domain.foodtruck.repository.query;

import com.boyworld.carrot.api.controller.foodtruck.response.FoodTruckOverview;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.boyworld.carrot.domain.SizeConstants.PAGE_SIZE;
import static com.boyworld.carrot.domain.foodtruck.QFoodTruck.foodTruck;
import static com.boyworld.carrot.domain.member.QMember.member;
import static org.springframework.util.StringUtils.hasText;

/**
 * 푸드트럭 조회 레포지토리
 *
 * @author 최영환
 */
@Repository
@RequiredArgsConstructor
public class FoodTruckQueryRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * 사용자 이메일로 선택된 푸드트럭의 개수 조회 쿼리
     *
     * @param email 사용자 이메일
     * @return 해당 사용자의 선택된 푸드트럭 개수
     */
    public Long getSelectedCountByEmail(String email) {
        return queryFactory
                .select(foodTruck.count())
                .from(foodTruck)
                .join(foodTruck.vendor, member)
                .where(
                        isEqualEmail(email),
                        isActiveFoodTruck()
                )
                .fetchOne();

    }

    /**
     * 이메일로 보유 푸드트럭 조회 쿼리
     *
     * @param lastFoodTruckId 마지막으로 조회된 푸드트럭 식별키
     * @param email           사업자 이메일
     * @return 해당 사업자 보유 푸드트럭 목록
     */
    public List<FoodTruckOverview> getFoodTruckOverviewsByEmail(Long lastFoodTruckId, String email) {
        List<Long> ids = queryFactory
                .select(foodTruck.id)
                .from(foodTruck)
                .join(foodTruck.vendor, member)
                .where(
                        isEqualEmail(email),
                        isGreaterThanLastId(lastFoodTruckId),
                        isActiveFoodTruck()
                )
                .limit(PAGE_SIZE + 1)
                .fetch();

        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }

        return queryFactory
                .select(Projections.constructor(FoodTruckOverview.class,
                        foodTruck.id,
                        foodTruck.name,
                        foodTruck.selected
                ))
                .from(foodTruck)
                .where(
                        foodTruck.id.in(ids)
                )
                .fetch();
    }

    private BooleanExpression isEqualEmail(String email) {
        return hasText(email) ? foodTruck.vendor.email.eq(email) : null;
    }

    private BooleanExpression isGreaterThanLastId(Long lastFoodTruckId) {
        return lastFoodTruckId != null ? foodTruck.id.gt(lastFoodTruckId) : null;
    }

    private static BooleanExpression isActiveFoodTruck() {
        return foodTruck.active.isTrue();
    }
}
