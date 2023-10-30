package com.boyworld.carrot.domain.survey.repository;

import com.boyworld.carrot.domain.survey.Survey;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    public List<Survey> getSurvey(Long categoryId, String sido, String sigungu, String dong, Pageable pageable) {
        LocalDateTime currentDate = LocalDate.now().atStartOfDay();

        return queryFactory
                .select(survey)
                .where(survey.createdDate.between(currentDate.minusMonths(6), currentDate)
                        .and(survey.category.id.eq(categoryId))
                        .and(survey.sido.eq(sido))
                        .and(survey.sigungu.eq(sigungu))
                        .and(survey.dong.eq(dong)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
