package com.boyworld.carrot.api.service.foodtruck.dto;

import com.boyworld.carrot.domain.foodtruck.Category;
import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import com.boyworld.carrot.domain.member.Member;
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

    public FoodTruck toEntity(Member vendor, Category category, Boolean selected) {
        return FoodTruck.builder()
                .vendor(vendor)
                .category(category)
                .name(this.foodTruckName)
                .phoneNumber(this.phoneNumber)
                .content(this.content)
                .originInfo(this.originInfo)
                .prepareTime(this.prepareTime)
                .waitLimits(this.waitLimits)
                .selected(selected)
                .active(selected)
                .build();
    }
}
