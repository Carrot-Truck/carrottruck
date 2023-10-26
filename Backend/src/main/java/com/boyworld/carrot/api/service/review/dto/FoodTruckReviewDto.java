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

    private Long id;

    private int grade;

    private String content;

    @Builder
    public FoodTruckReviewDto(Long id, int grade, String content) {
        this.id = id;
        this.grade = grade;
        this.content = content;
    }
}
