package com.boyworld.carrot.api.service.survey.dto;

import com.boyworld.carrot.domain.foodtruck.Category;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.survey.Survey;
import lombok.Builder;
import lombok.Data;

@Data
public class CreateSurveyDto {

    private Long id;

    private Category category;

    private Member member;

    private String sido;

    private String sigungu;

    private String dong;

    private String content;

    private Boolean active;

    @Builder
    public CreateSurveyDto(Long id, Category category, Member member, String sido, String sigungu, String dong, String content, Boolean active) {
        this.id = id;
        this.category = category;
        this.member = member;
        this.sido = sido;
        this.sigungu = sigungu;
        this.dong = dong;
        this.content = content;
        this.active = active;
    }

//    public Survey toEntity() {
//        return Survey.builder()
//                .category()
//                .member()
//                .sido()
//                .sigungu()
//                .dong()
//                .content()
//                .active()
//                .build();
//    }
}
