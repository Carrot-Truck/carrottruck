package com.boyworld.carrot.api.service.foodtruck.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class FoodTruckVendorDetailDto {

    private Long foodTruckId;
    private String foodTruckName;
    private String phoneNumber;
    private String content;
    private String originInfo;
    private Boolean isOpen;
    private Boolean isLiked;
    private Integer prepareTime;
    private Double avgGrade;
    private Integer likeCount;
    private Integer reviewCount;
    private String address;
    private String foodTruckImageUrl;
    private Boolean selected;
    private Boolean isNew;
    private Boolean isOwner;
    private String vendorName;
    private String tradeName;
    private String businessNumber;

    @Builder
    public FoodTruckVendorDetailDto(Long foodTruckId, String foodTruckName, String phoneNumber, String content,
                                    String originInfo, Boolean isOpen, Boolean isLiked, Integer prepareTime,
                                    Double avgGrade, Integer likeCount, Integer reviewCount, String address,
                                    String foodTruckImageUrl, Boolean selected, Boolean isNew, Boolean isOwner,
                                    String vendorName, String tradeName, String businessNumber) {
        this.foodTruckId = foodTruckId;
        this.foodTruckName = foodTruckName;
        this.phoneNumber = phoneNumber;
        this.content = content;
        this.originInfo = originInfo;
        this.isOpen = isOpen;
        this.isLiked = isLiked;
        this.prepareTime = prepareTime;
        this.avgGrade = avgGrade;
        this.likeCount = likeCount;
        this.reviewCount = reviewCount;
        this.address = address;
        this.foodTruckImageUrl = foodTruckImageUrl;
        this.selected = selected;
        this.isNew = isNew;
        this.isOwner = isOwner;
        this.vendorName = vendorName;
        this.tradeName = tradeName;
        this.businessNumber = businessNumber;
    }
}
