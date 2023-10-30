package com.boyworld.carrot.domain.menu.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 메뉴 조회 레포지토리
 *
 * @author 최영환
 */
@Repository
@RequiredArgsConstructor
public class MenuQueryRepository {

    private final JPAQueryFactory queryFactory;
}
