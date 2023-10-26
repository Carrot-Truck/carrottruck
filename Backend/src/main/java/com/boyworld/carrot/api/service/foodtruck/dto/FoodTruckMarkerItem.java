package com.boyworld.carrot.api.service.foodtruck.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class FoodTruckMarkerItem {

    private Long categoryId;

    private Long foodTruckId;

    private String latitude;

    private String longitude;

    private Boolean isOpen;

    @Builder
    public FoodTruckMarkerItem(Long categoryId, Long foodTruckId, String latitude, String longitude, Boolean isOpen) {
        this.categoryId = categoryId;
        this.foodTruckId = foodTruckId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isOpen = isOpen;
    }
}
