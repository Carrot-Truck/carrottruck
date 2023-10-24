package com.boyworld.carrot.api.service.survey;

import com.boyworld.carrot.api.controller.survey.response.SubmitSurveyResponse;
import com.boyworld.carrot.api.service.survey.dto.SubmitSurveyDto;
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

    public SubmitSurveyResponse SubmitSurvey(SubmitSurveyDto dto) {
        return null;
    }

}
