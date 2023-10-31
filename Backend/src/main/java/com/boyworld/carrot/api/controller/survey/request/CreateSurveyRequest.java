package com.boyworld.carrot.api.controller.survey.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CreateSurveyRequest {

    @NotNull
    private Long categoryId;

    @NotNull
    private Long memberId;

    @NotNull
    private BigDecimal latitude;

    @NotNull
    private BigDecimal longitude;

    @NotBlank
    private String content;

    @Builder
    public CreateSurveyRequest(Long categoryId, Long memberId, BigDecimal latitude, BigDecimal longitude, String content) {
        this.categoryId = categoryId;
        this.memberId = memberId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.content = content;
    }
}
