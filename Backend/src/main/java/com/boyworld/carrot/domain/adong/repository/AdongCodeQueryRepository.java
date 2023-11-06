package com.boyworld.carrot.domain.adong.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.boyworld.carrot.domain.adong.QAdongCode.adongCode;

@Repository
@RequiredArgsConstructor
public class AdongCodeQueryRepository {

    private final JPAQueryFactory queryFactory;

    public String getAdongCode(String sido, String sigungu, String dong) {
        return queryFactory
                .select(adongCode.adong_code)
                .from(adongCode)
                .where(adongCode.sido.eq(sido)
                    .and(adongCode.sigungu.eq(sigungu))
                    .and(adongCode.dong.eq(dong))
                )
                .fetchOne();
    }
}
