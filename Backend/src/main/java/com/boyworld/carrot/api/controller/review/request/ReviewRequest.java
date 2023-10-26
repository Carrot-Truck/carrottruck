package com.boyworld.carrot.api.controller.review.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewRequest {

    @NotBlank
    private Long memberId;

    @NotBlank
    private Long foodTruckId;

    @NotBlank
    private Long orderId;

    @NotBlank
    private String content;

    @NotBlank
    @Max(value = 5)
    private int grade;

    @Builder
    public ReviewRequest(Long memberId, Long foodTruckId, Long orderId, String content, int grade){
        this.memberId = memberId;
        this.foodTruckId = foodTruckId;
        this.orderId = orderId;
        this.content = content;
        this.grade = grade;
    }

}
