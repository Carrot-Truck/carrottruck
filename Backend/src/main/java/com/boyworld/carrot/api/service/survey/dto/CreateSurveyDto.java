package com.boyworld.carrot.api.service.survey.dto;

import com.boyworld.carrot.domain.foodtruck.Category;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.survey.Survey;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateSurveyDto {

    private Long categoryId;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private String content;

    private Boolean active;

    @Builder
    public CreateSurveyDto(Long categoryId, BigDecimal latitude, BigDecimal longitude, String content, Boolean active) {
        this.categoryId = categoryId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.content = content;
        this.active = active;
    }

    public Survey toEntity(Category category, Member member, String sido, String sigungu, String dong) {
        return Survey.builder()
                .category(category)
                .member(member)
                .sido(sido)
                .sigungu(sigungu)
                .dong(dong)
                .content(this.content)
                .active(true)
                .build();
    }
}
