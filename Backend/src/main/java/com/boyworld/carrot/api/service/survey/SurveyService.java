package com.boyworld.carrot.api.service.survey;

import com.boyworld.carrot.api.controller.survey.request.CreateSurveyRequest;
import com.boyworld.carrot.api.controller.survey.response.CreateSurveyResponse;
import com.boyworld.carrot.api.controller.survey.response.SurveyDetailsResponse;
import com.boyworld.carrot.api.controller.survey.response.SurveyCountResponse;
import com.boyworld.carrot.api.service.survey.dto.CreateSurveyDto;
import com.boyworld.carrot.domain.survey.repository.SurveyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 수요조사 서비스
 *
 * @author 양진형
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class SurveyService {

    private final SurveyRepository surveyRepository;

    /**
     * 설문 제출
     *
     * @param request 제출 수요조사 정보
     * @return 제출 수요조사 정보
     */
    public CreateSurveyResponse createSurvey(CreateSurveyRequest request) {

        // GeocodingUtil 사용
        return null;
    }

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

    /**
     * 수요조사 삭제
     *
     * @param surveyId 삭제할 수요조사 ID
     * @return 삭제한 수요조사 ID
     */
    public Long deleteSurvey(Long surveyId) {
        return null;
    }

}
