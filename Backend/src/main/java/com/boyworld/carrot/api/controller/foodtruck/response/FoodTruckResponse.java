package com.boyworld.carrot.api.controller.foodtruck.response;

import lombok.Builder;
import lombok.Data;

@Data
public class FoodTruckResponse {

    private Long foodTruckId;
    private Long categoryId;
    private String foodTruckName;
    private String categoryName;
    private String phoneNumber;
    private String content;
    private String originInfo;
    private Integer prepareTime;
    private int waitLimits;

    @Builder
    public FoodTruckResponse(Long foodTruckId, Long categoryId, String foodTruckName, String categoryName, String phoneNumber, String content, String originInfo, Integer prepareTime, int waitLimits) {
        this.foodTruckId = foodTruckId;
        this.categoryId = categoryId;
        this.foodTruckName = foodTruckName;
        this.categoryName = categoryName;
        this.phoneNumber = phoneNumber;
        this.content = content;
        this.originInfo = originInfo;
        this.prepareTime = prepareTime;
        this.waitLimits = waitLimits;
    }
}
