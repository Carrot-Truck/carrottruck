package com.boyworld.carrot.api.controller.foodtruck.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

import static com.boyworld.carrot.domain.StringConstants.NO_IMG;

@Data
public class FoodTruckItem {

    private Long foodTruckScheduleId;
    private Long categoryId;
    private Long foodTruckId;
    private String foodTruckName;
    private Boolean isOpen;
    private Boolean isLiked;
    private Integer prepareTime;
    private Integer likeCount;
    private Double grade;
    private Integer reviewCount;
    private BigDecimal distance;
    private String address;
    private String foodTruckImageUrl;
    private Boolean isNew;

    @Builder
    public FoodTruckItem(Long foodTruckScheduleId, Long categoryId, Long foodTruckId, String foodTruckName,
                         Boolean isOpen, Boolean isLiked, Integer prepareTime, Integer likeCount, Double grade,
                         Integer reviewCount, BigDecimal distance, String address, String foodTruckImageUrl,
                         Boolean isNew) {
        this.foodTruckScheduleId = foodTruckScheduleId;
        this.categoryId = categoryId;
        this.foodTruckId = foodTruckId;
        this.foodTruckName = foodTruckName;
        this.isOpen = isOpen;
        this.isLiked = isLiked;
        this.prepareTime = prepareTime;
        this.likeCount = likeCount;
        this.grade = grade;
        this.reviewCount = reviewCount;
        this.distance = distance;
        this.address = address;
        this.foodTruckImageUrl = foodTruckImageUrl != null ? foodTruckImageUrl : NO_IMG;
        this.isNew = isNew;
    }
}
