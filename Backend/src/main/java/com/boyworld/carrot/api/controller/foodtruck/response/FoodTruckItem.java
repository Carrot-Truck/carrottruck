package com.boyworld.carrot.api.controller.foodtruck.response;

import lombok.Builder;
import lombok.Data;

@Data
public class FoodTruckItem {
    private Long categoryId;
    private String categoryName;
    private Long foodTruckId;
    private String foodTruckName;
    private Boolean isOpen;
    private Integer prepareTime;
    private Double grade;
    private Integer reviewCount;
    private String longitude;
    private String latitude;
    private Long foodTruckImageId;

    @Builder
    public FoodTruckItem(Long categoryId, String categoryName, Long foodTruckId, String foodTruckName, Boolean isOpen, Integer prepareTime, Double grade, Integer reviewCount, String longitude, String latitude, Long foodTruckImageId) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.foodTruckId = foodTruckId;
        this.foodTruckName = foodTruckName;
        this.isOpen = isOpen;
        this.prepareTime = prepareTime;
        this.grade = grade;
        this.reviewCount = reviewCount;
        this.longitude = longitude;
        this.latitude = latitude;
        this.foodTruckImageId = foodTruckImageId;
    }
}
