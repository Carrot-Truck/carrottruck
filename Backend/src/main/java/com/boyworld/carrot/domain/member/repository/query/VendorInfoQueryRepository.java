package com.boyworld.carrot.domain.member.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 사업자 정보 쿼리 레포지토리
 *
 * @author 최영환
 */
@Repository
@RequiredArgsConstructor
public class VendorInfoQueryRepository {

    private final JPAQueryFactory queryFactory;
}
