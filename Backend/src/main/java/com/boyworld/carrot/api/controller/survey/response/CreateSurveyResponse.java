package com.boyworld.carrot.api.controller.survey.response;

import com.boyworld.carrot.domain.survey.Survey;
import lombok.Builder;
import lombok.Data;

@Data
public class CreateSurveyResponse {

    private String categoryName;

    private String nickname;

    private String sido;

    private String sigungu;

    private String dong;

    @Builder
    public CreateSurveyResponse(String categoryName, String nickname, String sido, String sigungu, String dong) {
        this.categoryName = categoryName;
        this.nickname = nickname;
        this.sido = sido;
        this.sigungu = sigungu;
        this.dong = dong;
    }

    public static CreateSurveyResponse of(Survey survey) {
        return CreateSurveyResponse.builder()
                .categoryName(survey.getCategory().getName())
                .nickname(survey.getMember().getNickname())
                .sido(survey.getSido())
                .sigungu(survey.getSigungu())
                .dong(survey.getDong())
                .build();
    }
}
