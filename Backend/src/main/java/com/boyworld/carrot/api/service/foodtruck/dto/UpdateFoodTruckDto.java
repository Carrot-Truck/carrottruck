package com.boyworld.carrot.api.service.foodtruck.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UpdateFoodTruckDto {

    private Long foodTruckId;
    private Long categoryId;
    private String foodTruckName;
    private String phoneNumber;
    private String content;
    private String originInfo;
    private Integer prepareTime;
    private Integer waitLimits;

    @Builder
    public UpdateFoodTruckDto(Long foodTruckId, Long categoryId, String foodTruckName, String phoneNumber, String content, String originInfo, Integer prepareTime, Integer waitLimits) {
        this.foodTruckId = foodTruckId;
        this.categoryId = categoryId;
        this.foodTruckName = foodTruckName;
        this.phoneNumber = phoneNumber;
        this.content = content;
        this.originInfo = originInfo;
        this.prepareTime = prepareTime;
        this.waitLimits = waitLimits;
    }
}
