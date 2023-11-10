package com.boyworld.carrot.api.service.review.dto;

import com.boyworld.carrot.domain.review.Review;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.boyworld.carrot.domain.StringConstants.NO_IMG;

/**
 * To show Review entity
 *
 * @author Gunhoo Park
 */
@Data
@NoArgsConstructor
public class MyReviewDto {

    private Long reviewId;

    private ReviewFoodTruckDto reviewFoodTruckDto;

    private int grade;

    private LocalDateTime createdDate;

    private String content;

    private String imageUrl;

    @Builder
    private MyReviewDto(Long reviewId, ReviewFoodTruckDto reviewFoodTruckDto, int grade, LocalDateTime createdDate, String content, String imageUrl) {
        this.reviewId = reviewId;
        this.reviewFoodTruckDto = reviewFoodTruckDto;
        this.grade = grade;
        this.content = content;
        this.createdDate = createdDate;
        this.imageUrl = imageUrl != null ? imageUrl : NO_IMG;
    }

    public static MyReviewDto of(Review review){
        return MyReviewDto.builder()
            .reviewId(review.getId())
            .reviewFoodTruckDto(ReviewFoodTruckDto.builder()
                .foodTruckId(review.getFoodTruck().getId())
                .foodTruckName(review.getFoodTruck().getName())
                .build())
            .grade(review.getGrade())
            .createdDate(review.getCreatedDate())
            .content(review.getContent())
            .build();
    }
}
