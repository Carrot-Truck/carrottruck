package com.boyworld.carrot.domain.order.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final JPAQueryFactory queryFactory;
}
