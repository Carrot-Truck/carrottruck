package com.boyworld.carrot.domain.survey.repository;

import com.boyworld.carrot.api.service.survey.dto.SurveyCountDto;
import com.boyworld.carrot.api.service.survey.dto.SurveyDetailDto;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.boyworld.carrot.domain.SizeConstants.PAGE_SIZE;
import static com.boyworld.carrot.domain.survey.QSurvey.survey;

/**
 * 수요조사 조회 레포지토리
 *
 * @author 양진형
 */
@Repository
@RequiredArgsConstructor
public class SurveyQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<SurveyCountDto> countSurvey(String sido, String sigungu, String dong) {
        LocalDateTime currentDate = LocalDateTime.now();

        return queryFactory
                .selectDistinct(Projections.constructor(SurveyCountDto.class,
                        survey.category.id,
                        survey.category.name,
                        survey.count().castToNum(Integer.class)
                ))
                .from(survey)
                .where(survey.createdDate.gt(currentDate.minusMonths(6))
                        .and(survey.sido.eq(sido))
                        .and(survey.sigungu.eq(sigungu))
                        .and(survey.dong.eq(dong)),
                        isActiveSurvey()
                )
                .groupBy(survey.category.id)
                .fetch();
    }

    public List<SurveyDetailDto> getSurvey(Long categoryId, String sido, String sigungu, String dong, Long lastSurveyId) {
        LocalDateTime currentDate = LocalDateTime.now();

        List<Long> ids = queryFactory
                .select(survey.id)
                .from(survey)
                .where(survey.createdDate.gt(currentDate.minusMonths(6))
                        .and(survey.category.id.eq(categoryId))
                        .and(survey.sido.eq(sido))
                        .and(survey.sigungu.eq(sigungu))
                        .and(survey.dong.eq(dong)),
                        isGreaterThanLastId(lastSurveyId),
                        isActiveSurvey()
                )
                .limit(PAGE_SIZE + 1)
                .fetch();

        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }

        Expression<String> formattedDate = Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%dT%H:%i:%s')", survey.createdDate);

        return queryFactory
                .select(Projections.constructor(SurveyDetailDto.class,
                        survey.id,
                        survey.member.nickname,
                        survey.content,
                        formattedDate
                ))
                .from(survey)
                .where(survey.id.in(ids))
                .fetch();
    }

    private BooleanExpression isGreaterThanLastId(Long lastSurveyId) {
        return lastSurveyId != null ? survey.id.gt(lastSurveyId) : null;
    }

    private BooleanExpression isActiveSurvey() {
        return survey.active.isTrue();
    }
}
