package com.boyworld.carrot.api.controller.foodtruck.response;

import lombok.Builder;
import lombok.Data;

@Data
public class FoodTruckItem {
    private Long categoryId;
    private Long foodTruckId;
    private String foodTruckName;
    private Boolean isOpen;
    private Boolean isLiked;
    private Integer prepareTime;
    private Double grade;
    private Integer reviewCount;
    private Integer distance;
    private Long foodTruckImageId;
    private Boolean isNew;

    @Builder
    public FoodTruckItem(Long categoryId, Long foodTruckId, String foodTruckName, Boolean isOpen, Boolean isLiked, Integer prepareTime, Double grade, Integer reviewCount, Integer distance, Long foodTruckImageId, Boolean isNew) {
        this.categoryId = categoryId;
        this.foodTruckId = foodTruckId;
        this.foodTruckName = foodTruckName;
        this.isOpen = isOpen;
        this.isLiked = isLiked;
        this.prepareTime = prepareTime;
        this.grade = grade;
        this.reviewCount = reviewCount;
        this.distance = distance;
        this.foodTruckImageId = foodTruckImageId;
        this.isNew = isNew;
    }
}
