package com.boyworld.carrot.api.controller.survey.response;

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
}
