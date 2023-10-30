package com.boyworld.carrot.api.controller.review.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReportRequest {

    @NotNull
    private Long reviewId;

    @NotBlank
    private String content;

    @Builder
    public ReportRequest(Long reviewId, String content){
        this.reviewId = reviewId;
        this.content = content;
    }
}
