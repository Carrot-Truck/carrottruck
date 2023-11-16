package com.boyworld.carrot.api.service.foodtruck.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
@Data
public class FoodTruckMarkerItem {

    private Long id;

    private Long categoryId;

    private Long foodTruckId;

    private String foodTruckName;

    private String latitude;

    private String longitude;

    private String distance;

    private Boolean isOpen;

    @Builder
    public FoodTruckMarkerItem(Long id, Long categoryId, Long foodTruckId, String foodTruckName, BigDecimal distance,
                               BigDecimal latitude, BigDecimal longitude, Boolean isOpen) {
        this.id = id;
        this.categoryId = categoryId;
        this.foodTruckId = foodTruckId;
        this.foodTruckName = foodTruckName;
        this.distance = distance.toEngineeringString();
        this.latitude = latitude.toEngineeringString();
        this.longitude = longitude.toEngineeringString();
        this.isOpen = isOpen;
    }
}
