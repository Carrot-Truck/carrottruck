package com.boyworld.carrot.api.service.foodtruck.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class FoodTruckDetailDto {

    private Long foodTruckId;
    private String foodTruckName;
    private String phoneNumber;
    private String content;
    private String originInfo;
    private Boolean isOpen;
    private Boolean isLiked;
    private Integer prepareTime;
    private Double grade;
    private Integer reviewCount;
    private Integer distance;
    private String address;
    private String foodTruckImageUrl;
    private Boolean selected;
    private Boolean isNew;
    private String vendorName;
    private String tradeName;
    private String businessNumber;

    @Builder
    public FoodTruckDetailDto(Long foodTruckId, String foodTruckName, String phoneNumber, String content,
                              String originInfo, Boolean isOpen, Boolean isLiked, Integer prepareTime, Double grade,
                              Integer reviewCount, Integer distance, String address, String foodTruckImageUrl,
                              Boolean isNew, Boolean selected, String vendorName, String tradeName, String businessNumber) {
        this.foodTruckId = foodTruckId;
        this.foodTruckName = foodTruckName;
        this.phoneNumber = phoneNumber;
        this.content = content;
        this.originInfo = originInfo;
        this.isOpen = isOpen;
        this.isLiked = isLiked;
        this.prepareTime = prepareTime;
        this.grade = grade;
        this.reviewCount = reviewCount;
        this.distance = distance;
        this.address = address;
        this.foodTruckImageUrl = foodTruckImageUrl;
        this.isNew = isNew;
        this.selected = selected;
        this.vendorName = vendorName;
        this.tradeName = tradeName;
        this.businessNumber = businessNumber;
    }
}
