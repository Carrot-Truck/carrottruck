package com.boyworld.carrot.domain.foodtruck.repository.query;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
                .select(foodTruck.id)
                .from(foodTruck)
                .join(foodTruck.vendor, member)
                .where(
                        isEqualEmail(email),
                        isActiveFoodTruck()
                )
                .fetchOne();

    }

    private BooleanExpression isEqualEmail(String email) {
        return hasText(email) ? foodTruck.vendor.email.eq(email) : null;
    }

    private static BooleanExpression isActiveFoodTruck() {
        return foodTruck.active.isTrue();
    }
}
