package com.boyworld.carrot.domain.member.repository.query;

import static com.boyworld.carrot.domain.foodtruck.QFoodTruckLike.foodTruckLike;
import static com.boyworld.carrot.domain.member.QMemberDeviceToken.memberDeviceToken;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberDeviceTokenQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<String> getMembersLikeFoodTruck(Long foodTruckId) {
        List<Long> memberIdList = queryFactory
            .select(foodTruckLike.member.id)
            .from(foodTruckLike)
            .where(foodTruckLike.foodTruck.id.eq(foodTruckId),
                foodTruckLike.member.active.eq(true)
                )
            .fetch();

        if (memberIdList != null) {
            return queryFactory
                .select(memberDeviceToken.deviceToken)
                .from(memberDeviceToken)
                .where(memberDeviceToken.member.id.in(memberIdList))
                .fetch();
        }

        return null;
    }
}
