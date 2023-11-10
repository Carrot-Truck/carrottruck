package com.boyworld.carrot.api.service.foodtruck.dto;

import lombok.Builder;
import lombok.Data;

import static com.boyworld.carrot.domain.StringConstants.NO_IMG;

@Data
public class FoodTruckVendorDetailDto {

    private Long foodTruckId;
    private String foodTruckName;
    private String phoneNumber;
    private String content;
    private String originInfo;
    private Integer prepareTime;
    private double avgGrade;
    private int reviewCount;
    private String foodTruckImageUrl;
    private Boolean selected;
    private String vendorName;
    private String tradeName;
    private String businessNumber;

    @Builder
    public FoodTruckVendorDetailDto(Long foodTruckId, String foodTruckName, String phoneNumber, String content,
                                    String originInfo, Integer prepareTime, double avgGrade, int reviewCount,
                                    String foodTruckImageUrl, Boolean selected, String vendorName, String tradeName, String businessNumber) {
        this.foodTruckId = foodTruckId;
        this.foodTruckName = foodTruckName;
        this.phoneNumber = phoneNumber;
        this.content = content;
        this.originInfo = originInfo;
        this.prepareTime = prepareTime;
        this.avgGrade = avgGrade;
        this.reviewCount = reviewCount;
        this.foodTruckImageUrl = foodTruckImageUrl != null ? foodTruckImageUrl : NO_IMG;
        this.selected = selected;
        this.vendorName = vendorName;
        this.tradeName = tradeName;
        this.businessNumber = businessNumber;
    }
}
