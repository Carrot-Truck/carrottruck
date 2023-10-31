package com.boyworld.carrot.api.controller.survey.response;

import com.boyworld.carrot.api.service.survey.dto.SurveyDetailDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class SurveyDetailsResponse {

    private Long categoryId;

    private String categoryName;

    private List<SurveyDetailDto> surveyDetailDtoList;

    @Builder
    public SurveyDetailsResponse(Long categoryId, String categoryName, List<SurveyDetailDto> surveyDetailDtoList) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.surveyDetailDtoList = surveyDetailDtoList;
    }
}
