package com.boyworld.carrot.api.controller.survey.response;

import com.boyworld.carrot.api.service.survey.dto.SurveyCountDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class SurveyCountResponse {

    private List<SurveyCountDto> surveyCountDtoList;

    @Builder
    public SurveyCountResponse(List<SurveyCountDto> surveyCountDtoList) {
        this.surveyCountDtoList = surveyCountDtoList;
    }
}
