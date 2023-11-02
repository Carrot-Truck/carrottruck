package com.boyworld.carrot.domain.survey.repository;

import com.boyworld.carrot.api.controller.survey.response.CreateSurveyResponse;
import com.boyworld.carrot.domain.survey.Survey;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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

    public int countSurvey(String sido, String sigungu, String dong) {
        LocalDateTime currentDate = LocalDate.now().atStartOfDay();

        return queryFactory
                .selectFrom(survey)
                .where(survey.createdDate.between(currentDate.minusMonths(6), currentDate)
                        .and(survey.sido.eq(sido))
                        .and(survey.sigungu.eq(sigungu))
                        .and(survey.dong.eq(dong)))
                .fetch().size();
    }

    public List<CreateSurveyResponse> getSurvey(Long categoryId, String sido, String sigungu, String dong, Long lastSurveyId) {
        LocalDateTime currentDate = LocalDate.now().atStartOfDay();

        List<Long> ids = queryFactory
                .select(survey.id)
                .from(survey)
                .where(survey.createdDate.between(currentDate.minusMonths(6), currentDate)
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

        return queryFactory
                .select(Projections.constructor(CreateSurveyResponse.class,
                        survey.category.name,
                        survey.member.nickname,
                        survey.sido,
                        survey.sigungu,
                        survey.dong
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
