package com.boyworld.carrot.api.controller.survey.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class SubmitSurveyRequest {

    @NotBlank
    private BigDecimal longitude;

    @NotBlank
    private BigDecimal latitude;

    @NotBlank
    private String content;

    @Builder
    public SubmitSurveyRequest(BigDecimal longitude, BigDecimal latitude, String content) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.content = content;
    }
}
