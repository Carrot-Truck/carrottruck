package com.boyworld.carrot.api.controller.survey.request;

import com.boyworld.carrot.api.service.survey.dto.CreateSurveyDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CreateSurveyRequest {

    @NotBlank
    private Long categoryId;

    @NotBlank
    private Long memberId;

    @NotBlank
    private BigDecimal longitude;

    @NotBlank
    private BigDecimal latitude;

    @NotBlank
    private String content;

    @Builder
    public CreateSurveyRequest(Long categoryId, Long memberId, BigDecimal longitude, BigDecimal latitude, String content) {
        this.categoryId = categoryId;
        this.memberId = memberId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.content = content;
    }
}
