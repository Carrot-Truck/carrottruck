package com.boyworld.carrot.api.controller.foodtruck.request;

import com.boyworld.carrot.api.service.foodtruck.dto.UpdateFoodTruckDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateFoodTruckRequest {
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
    @NotNull
    private Integer waitLimits;

    @Builder
    public UpdateFoodTruckRequest(Long categoryId, String foodTruckName, String phoneNumber,
                                  String content, String originInfo, Integer prepareTime, Integer waitLimits) {
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
