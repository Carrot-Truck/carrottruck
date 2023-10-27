package com.boyworld.carrot.api.controller.foodtruck.request;

import com.boyworld.carrot.api.service.foodtruck.dto.UpdateFoodTruckDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateFoodTruckRequest {
    private Long categoryId;
    private String foodTruckName;
    private String phoneNumber;
    private String content;
    private String originInfo;
    private Integer prepareTime;
    private Integer waitLimits;

    @Builder
    public UpdateFoodTruckRequest(Long categoryId, String foodTruckName, String phoneNumber, String content, String originInfo, Integer prepareTime, Integer waitLimits) {
        this.categoryId = categoryId;
        this.foodTruckName = foodTruckName;
        this.phoneNumber = phoneNumber;
        this.content = content;
        this.originInfo = originInfo;
        this.prepareTime = prepareTime;
        this.waitLimits = waitLimits;
    }

    public UpdateFoodTruckDto toUpdateFoodTruckDto(Long foodTruckId) {
        return UpdateFoodTruckDto.builder()
                .foodTruckId(foodTruckId)
                .categoryId(this.categoryId)
                .foodTruckName(this.foodTruckName)
                .phoneNumber(this.phoneNumber)
                .content(this.content)
                .originInfo(this.originInfo)
                .prepareTime(this.prepareTime)
                .waitLimits(this.waitLimits)
                .build();
    }
}
