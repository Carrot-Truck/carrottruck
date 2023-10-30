package com.boyworld.carrot.domain.survey.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 수요조사 조회 레포지토리
 *
 * @author 양진형
 */
@Repository
@RequiredArgsConstructor
public class SurveyQueryRepository {
    private final JPAQueryFactory queryFactory;
}
