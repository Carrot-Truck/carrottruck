package com.boyworld.carrot.domain.foodtruck.repository.query;

import com.boyworld.carrot.domain.foodtruck.FoodTruckLike;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.boyworld.carrot.domain.foodtruck.QFoodTruckLike.foodTruckLike;

/**
 * 푸드트럭 찜 조회 레포지토리
 *
 * @author 최영환
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class FoodTruckLikeQueryRepository {

    private final JPAQueryFactory queryFactory;

    public FoodTruckLike getFoodTruckLikeByMemberIdAndFoodTruckId(Long memberId, Long foodTruckId) {
        return queryFactory
                .select(foodTruckLike)
                .from(foodTruckLike)
                .where(
                        foodTruckLike.member.id.eq(memberId),
                        foodTruckLike.foodTruck.id.eq(foodTruckId),
                        foodTruckLike.active
                )
                .fetchOne();
    }

}
