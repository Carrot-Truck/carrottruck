package com.boyworld.carrot.api.service.survey.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class SurveyCountDto {

    private Long categoryId;

    private String categoryName;

    private Integer surveyCount;

    @Builder
    public SurveyCountDto(Long categoryId, String categoryName, Integer surveyCount) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.surveyCount = surveyCount;
    }
}
