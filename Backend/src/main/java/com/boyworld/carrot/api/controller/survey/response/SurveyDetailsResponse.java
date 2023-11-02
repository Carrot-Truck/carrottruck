package com.boyworld.carrot.api.controller.survey.response;

import com.boyworld.carrot.api.service.survey.dto.SurveyDetailDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class SurveyDetailsResponse {

    private Long categoryId;

    private String categoryName;

    private List<SurveyDetailDto> surveyDetails;

    private Boolean hasNext;

    @Builder
    public SurveyDetailsResponse(Long categoryId, String categoryName, List<SurveyDetailDto> surveyDetails, Boolean hasNext) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.surveyDetails = surveyDetails;
        this.hasNext = hasNext;
    }
}
