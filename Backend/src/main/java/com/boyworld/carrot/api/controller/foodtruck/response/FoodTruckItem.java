package com.boyworld.carrot.api.controller.foodtruck.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

import static com.boyworld.carrot.domain.StringConstants.NO_IMG;

@Data
public class FoodTruckItem {

    private Long categoryId;
    private Long foodTruckId;
    private String foodTruckName;
    private Boolean isOpen;
    private Boolean isLiked;
    private int likeCount;
    private double grade;
    private int reviewCount;
    private String foodTruckImageUrl;
    private Boolean isNew;

    @Builder
    public FoodTruckItem(Long categoryId, Long foodTruckId, String foodTruckName,
                         boolean isOpen, boolean isLiked, int likeCount, double grade,
                         int reviewCount, String foodTruckImageUrl, boolean isNew) {
        this.categoryId = categoryId;
        this.foodTruckId = foodTruckId;
        this.foodTruckName = foodTruckName;
        this.isOpen = isOpen;
        this.isLiked = isLiked;
        this.likeCount = likeCount;
        this.grade = grade;
        this.reviewCount = reviewCount;
        this.foodTruckImageUrl = foodTruckImageUrl != null ? foodTruckImageUrl : NO_IMG;
        this.isNew = isNew;
    }
}
