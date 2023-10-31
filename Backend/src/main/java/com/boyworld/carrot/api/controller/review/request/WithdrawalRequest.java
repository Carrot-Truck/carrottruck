package com.boyworld.carrot.api.controller.review.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WithdrawalRequest {

    @NotNull
    private Long reviewId;

    @Builder
    public WithdrawalRequest(Long reviewId) {
        this.reviewId = reviewId;
    }
}
