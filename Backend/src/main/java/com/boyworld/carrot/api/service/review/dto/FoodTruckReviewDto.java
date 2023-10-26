package com.boyworld.carrot.api.service.review.dto;

import com.boyworld.carrot.domain.review.Review;
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

    private String nickname;

    @Builder
    private FoodTruckReviewDto(Long reviewId, int grade, String content, String nickname) {
        this.reviewId = reviewId;
        this.nickname = nickname;
        this.grade = grade;
        this.content = content;
        this.nickname = nickname;
    }

    public static FoodTruckReviewDto of(Review review){
        return FoodTruckReviewDto.builder()
            .reviewId(review.getId())
            .grade(review.getGrade())
            .content(review.getContent())
            .nickname(review.getMember().getNickname())
            .build();
    }
}
