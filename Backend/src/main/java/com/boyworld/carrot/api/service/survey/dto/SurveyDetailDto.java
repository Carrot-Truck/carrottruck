package com.boyworld.carrot.api.service.survey.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SurveyDetailDto {

    private Long surveyId;

    private String nickname;

    private String content;

    private String createdTime;

    @Builder
    public SurveyDetailDto(Long surveyId, String nickname, String content, String createdTime) {
        this.surveyId = surveyId;
        this.nickname = nickname;
        this.content = content;
        this.createdTime = createdTime;
    }
}
