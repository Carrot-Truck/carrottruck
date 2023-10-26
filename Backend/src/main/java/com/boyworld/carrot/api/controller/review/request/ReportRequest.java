package com.boyworld.carrot.api.controller.review.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReportRequest {

    @NotBlank
    private Long reviewId;

    @NotBlank
    private String content;

    @Builder
    public ReportRequest(Long reviewId, String content){
        this.reviewId = reviewId;
        this.content = content;
    }
}
