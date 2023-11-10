package com.boyworld.carrot.api.service.foodtruck.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FoodTruckClientDetailDto {

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
    private BigDecimal distance;
    private String address;
    private String foodTruckImageUrl;
    private Boolean selected;
    private Boolean isNew;
    private String vendorName;
    private String tradeName;
    private String businessNumber;

    @Builder
    public FoodTruckClientDetailDto(Long foodTruckId, String foodTruckName, String phoneNumber, String content,
                                    String originInfo, boolean isOpen, boolean isLiked, int prepareTime,
                                    double avgGrade, int likeCount, int reviewCount, double distance,
                                    String address, String foodTruckImageUrl, boolean selected, boolean isNew,
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
        this.distance = new BigDecimal(distance);
        this.address = address;
        this.foodTruckImageUrl = foodTruckImageUrl;
        this.selected = selected;
        this.isNew = isNew;
        this.vendorName = vendorName;
        this.tradeName = tradeName;
        this.businessNumber = businessNumber;
    }
}
