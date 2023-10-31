package com.boyworld.carrot.api.service.survey;

import com.boyworld.carrot.api.controller.survey.response.SurveyCountResponse;
import com.boyworld.carrot.api.controller.survey.response.SurveyDetailsResponse;
import com.boyworld.carrot.domain.survey.repository.SurveyQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 수요조사 쿼리 서비스
 *
 * @author 양진형
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SurveyQueryService {

    private final SurveyQueryRepository surveyQueryRepository;

    /**
     * 수요조사 확인
     *
     * @param sido 시도
     * @param sigungu 시군구
     * @param dong 읍면동
     * @return 각 카테고리별 최근 6개월 수요조사 건수
     */
    public SurveyCountResponse getSurveyCount(String sido, String sigungu, String dong) {
        return null;
    }

    /**
     * 카테고리별 수요조사 상세내용 리스트
     * (1페이지당 10건)
     *
     * @param categoryId 카테고리 ID
     * @param sido 시도
     * @param sigungu 시군구
     * @param dong 읍면동
     * @param page 페이지
     * @return 각 카테고리별 최근 6개월 수요조사 상세내용 리스트
     */
    public SurveyDetailsResponse getSurveyDetails(Long categoryId, String sido, String sigungu, String dong, Integer page) {
        return null;
    }
}
