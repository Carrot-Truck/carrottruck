package com.boyworld.carrot.api.service.foodtruck.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateFoodTruckDto {

    private Long categoryId;
    private String foodTruckName;
    private String phoneNumber;
    private String content;
    private String originInfo;
    private Integer prepareTime;
    private int waitLimits;

    @Builder
    public CreateFoodTruckDto(Long categoryId, String foodTruckName, String phoneNumber, String content, String originInfo, Integer prepareTime, int waitLimits) {
        this.categoryId = categoryId;
        this.foodTruckName = foodTruckName;
        this.phoneNumber = phoneNumber;
        this.content = content;
        this.originInfo = originInfo;
        this.prepareTime = prepareTime;
        this.waitLimits = waitLimits;
    }
}
