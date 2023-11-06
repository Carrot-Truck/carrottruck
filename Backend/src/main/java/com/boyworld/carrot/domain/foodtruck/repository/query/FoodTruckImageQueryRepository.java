package com.boyworld.carrot.domain.foodtruck.repository.query;

import com.boyworld.carrot.domain.foodtruck.FoodTruckImage;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.boyworld.carrot.domain.foodtruck.QFoodTruckImage.foodTruckImage;

/**
 * 푸드트럭 이미지 조회 레포지토리
 *
 * @author 최영환
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class FoodTruckImageQueryRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * 푸드트럭 식별키로 푸드트럭 이미지 조회 쿼리
     *
     * @param foodTruckId 푸드트럭 식별키
     * @return 푸드트럭 이미지 엔티티
     */
    public FoodTruckImage getFoodTruckImageByFoodTruckId(Long foodTruckId) {
        return queryFactory
                .selectFrom(foodTruckImage)
                .where(
                        isEqualFoodTruckId(foodTruckId),
                        foodTruckImage.active
                )
                .fetchOne();
    }

    private BooleanExpression isEqualFoodTruckId(Long foodTruckId) {
        return foodTruckId != null ? foodTruckImage.foodTruck.id.eq(foodTruckId) : null;
    }
}
