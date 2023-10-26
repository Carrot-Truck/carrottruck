package com.boyworld.carrot.api.service.review.dto;

import lombok.Builder;
import lombok.Data;

/**
 * To show Review entity
 *
 * @author Gunhoo Park
 */

@Data
public class FoodTruckReviewDto {

    private Long reviewId;

    private String nickname;

    private int grade;

    private String content;

    @Builder
    public FoodTruckReviewDto(Long reviewId, String nickname, int grade, String content) {
        this.reviewId = reviewId;
        this.nickname = nickname;
        this.grade = grade;
        this.content = content;
    }
}
