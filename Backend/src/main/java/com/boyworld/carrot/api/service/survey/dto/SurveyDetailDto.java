package com.boyworld.carrot.api.service.survey.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SurveyDetailDto {

    private Long surveyId;

    private Long memberId;

    private String nickname;

    private String content;

    private LocalDateTime createdTime;

    @Builder
    public SurveyDetailDto(Long surveyId, Long memberId, String nickname, String content, LocalDateTime createdTime) {
        this.surveyId = surveyId;
        this.memberId = memberId;
        this.nickname = nickname;
        this.content = content;
        this.createdTime = createdTime;
    }
}
