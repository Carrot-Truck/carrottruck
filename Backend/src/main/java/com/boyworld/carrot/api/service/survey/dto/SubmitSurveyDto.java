package com.boyworld.carrot.api.service.survey.dto;

import com.boyworld.carrot.domain.foodtruck.Category;
import com.boyworld.carrot.domain.member.Member;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SubmitSurveyDto {

    private Category category;

    private Member member;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private String content;

    @Builder
    public SubmitSurveyDto(Category category, Member member, BigDecimal longitude, BigDecimal latitude, String content) {
        this.category = category;
        this.member = member;
        this.longitude = longitude;
        this.latitude = latitude;
        this.content = content;
    }
}
