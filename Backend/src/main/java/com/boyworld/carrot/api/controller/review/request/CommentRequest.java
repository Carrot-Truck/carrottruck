package com.boyworld.carrot.api.controller.review.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentRequest {
    @NotNull
    private Long reviewId;

    @NotBlank
    private String comment;

    @Builder
    public CommentRequest(Long reviewId, String comment){
        this.reviewId = reviewId;
        this.comment = comment;
    }
}
