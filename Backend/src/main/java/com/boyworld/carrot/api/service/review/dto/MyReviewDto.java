package com.boyworld.carrot.api.service.review.dto;

import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

/**
 * To show Review entity
 *
 * @author Gunhoo Park
 */
@Data
public class MyReviewDto {

    private Long reviewId;

    private FoodTruck foodTruck;

    private int grade;

    private LocalDateTime createdDate;

    private String content;

    @Builder
    public MyReviewDto(Long reviewId, FoodTruck foodTruck, int grade, LocalDateTime createdDate, String content) {
        this.reviewId = reviewId;
        this.foodTruck = foodTruck;
        this.grade = grade;
        this.content = content;
        this.createdDate = createdDate;
    }
}
