package com.boyworld.carrot.api.service.survey;

import com.boyworld.carrot.api.controller.survey.request.CreateSurveyRequest;
import com.boyworld.carrot.api.controller.survey.response.CreateSurveyResponse;
import com.boyworld.carrot.api.service.geocoding.GeocodingService;
import com.boyworld.carrot.domain.survey.Survey;
import com.boyworld.carrot.domain.survey.repository.SurveyRepository;
import com.boyworld.carrot.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;

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

    private final GeocodingService geocodingService;

    /**
     * 설문 제출
     *
     * @param request 제출 수요조사 정보
     * @return 제출 수요조사 정보
     */
    public CreateSurveyResponse createSurvey(CreateSurveyRequest request) {
        BigDecimal latitude = request.getLatitude();
        BigDecimal longitude = request.getLongitude();

        Map<String, String> result = geocodingService.reverseGeocoding(latitude, longitude, "");

        return null;
    }

    /**
     * 수요조사 삭제
     *
     * @param surveyId 삭제할 수요조사 ID
     * @return 삭제한 수요조사 ID
     */
    public Long deleteSurvey(Long surveyId) {
        String email = SecurityUtil.getCurrentLoginId();
        Survey survey = surveyRepository.findById(surveyId).orElse(null);

        if(survey == null) {
            // 존재하지 않는 수요조사
            return null;
        }

        if (!survey.getMember().getEmail().equals(email)) {
            // 로그인한 회원과 수요조사 작성 회원 불일치
            return null;
        }

        if (!survey.getMember().getActive()) {
            // 탈퇴한 회원이 작성한 수요조사
            return null;
        }

        survey.delete();

        return surveyId;
    }
}
