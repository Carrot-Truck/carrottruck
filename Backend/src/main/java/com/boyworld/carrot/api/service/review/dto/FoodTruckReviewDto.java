package com.boyworld.carrot.api.service.review.dto;

import com.boyworld.carrot.domain.review.Review;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

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

    private MultipartFile image;

    @Builder
    private FoodTruckReviewDto(Long reviewId, int grade, String content, String nickname, MultipartFile image) {
        this.reviewId = reviewId;
        this.grade = grade;
        this.content = content;
        this.nickname = nickname;
        this.image = image;
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
