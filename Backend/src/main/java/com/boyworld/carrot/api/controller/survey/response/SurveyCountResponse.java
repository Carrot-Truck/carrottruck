package com.boyworld.carrot.api.controller.survey.response;

import com.boyworld.carrot.api.service.survey.dto.SurveyCountDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class SurveyCountResponse {

    private List<SurveyCountDto> surveyCounts;

    @Builder
    public SurveyCountResponse(List<SurveyCountDto> surveyCounts) {
        this.surveyCounts = surveyCounts;
    }

    public static SurveyCountResponse of(List<SurveyCountDto> surveyCounts) {
        return SurveyCountResponse.builder()
                .surveyCounts(surveyCounts)
                .build();
    }
}
