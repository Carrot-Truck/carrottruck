package com.boyworld.carrot.api.controller.survey.response;

import lombok.Builder;
import lombok.Data;

@Data
public class CreateSurveyResponse {

    private String category_name;

    private String nickname;

    private String sido;

    private String sigungu;

    private String dong;

    @Builder
    public CreateSurveyResponse(String category_name, String nickname, String sido, String sigungu, String dong) {
        this.category_name = category_name;
        this.nickname = nickname;
        this.sido = sido;
        this.sigungu = sigungu;
        this.dong = dong;
    }
}
