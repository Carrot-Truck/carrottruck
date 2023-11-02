package com.boyworld.carrot.api.controller.survey.request;

import com.boyworld.carrot.api.service.survey.dto.CreateSurveyDto;
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
    private BigDecimal latitude;

    @NotNull
    private BigDecimal longitude;

    @NotBlank
    private String content;

    @Builder
    public CreateSurveyRequest(Long categoryId, BigDecimal latitude, BigDecimal longitude, String content) {
        this.categoryId = categoryId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.content = content;
    }

    public CreateSurveyDto toCreateSurveyDto() {
        return CreateSurveyDto.builder()
                .categoryId(this.categoryId)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .content(this.content)
                .build();
    }
}
