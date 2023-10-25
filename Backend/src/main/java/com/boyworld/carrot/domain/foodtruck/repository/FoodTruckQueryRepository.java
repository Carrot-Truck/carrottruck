package com.boyworld.carrot.domain.foodtruck.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 푸드트럭 조회 레포지토리
 *
 * @author 최영환
 */
@Repository
@RequiredArgsConstructor
public class FoodTruckQueryRepository {
    private final JPAQueryFactory queryFactory;
}
