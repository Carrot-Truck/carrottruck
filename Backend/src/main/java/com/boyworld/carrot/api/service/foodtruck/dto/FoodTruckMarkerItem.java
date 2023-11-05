package com.boyworld.carrot.api.service.foodtruck.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
@Data
public class FoodTruckMarkerItem {

    private Long categoryId;

    private Long foodTruckId;

    private String latitude;

    private String longitude;

    private String distance;

    private Boolean isOpen;

    @Builder
    public FoodTruckMarkerItem(Long categoryId, Long foodTruckId, BigDecimal distance,
                               BigDecimal latitude, BigDecimal longitude, Boolean isOpen) {
        this.categoryId = categoryId;
        this.foodTruckId = foodTruckId;
        this.distance = distance.toEngineeringString();
        this.latitude = latitude.toEngineeringString();
        this.longitude = longitude.toEngineeringString();
        this.isOpen = isOpen;
    }
}
