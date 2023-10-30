package com.boyworld.carrot.api.controller.review.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WithdrawalRequest {

    private String email;

    private Long reviewId;

    @Builder
    public WithdrawalRequest(String email, Long reviewId) {
        this.email = email;
        this.reviewId = reviewId;
    }
}
