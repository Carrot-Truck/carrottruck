package com.boyworld.carrot.api.controller.foodtruck.response;

import lombok.Builder;
import lombok.Data;

@Data
public class FoodTruckOverview {

    private Long foodTruckId;

    private String foodTruckName;

    private Boolean selected;

    @Builder
    public FoodTruckOverview(Long foodTruckId, String foodTruckName, Boolean selected) {
        this.foodTruckId = foodTruckId;
        this.foodTruckName = foodTruckName;
        this.selected = selected;
    }
}
