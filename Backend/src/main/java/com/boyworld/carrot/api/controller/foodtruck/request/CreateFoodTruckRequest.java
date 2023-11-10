package com.boyworld.carrot.api.controller.foodtruck.request;

import com.boyworld.carrot.api.service.foodtruck.dto.CreateFoodTruckDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateFoodTruckRequest {

    @NotNull
    private Long categoryId;
    @NotBlank
    private String foodTruckName;
    @NotBlank
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$")
    private String phoneNumber;
    private String content;
    private String originInfo;
    @NotNull
    private Integer prepareTime;
    private int waitLimits;

    @Builder
    public CreateFoodTruckRequest(Long categoryId, String foodTruckName, String phoneNumber, String content, String originInfo, Integer prepareTime, int waitLimits) {
        this.categoryId = categoryId;
        this.foodTruckName = foodTruckName;
        this.phoneNumber = phoneNumber;
        this.content = content;
        this.originInfo = originInfo;
        this.prepareTime = prepareTime;
        this.waitLimits = waitLimits;
    }

    public CreateFoodTruckDto toCreateFoodTruckDto() {
        return CreateFoodTruckDto.builder()
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
