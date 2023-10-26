package com.boyworld.carrot.api.controller.foodtruck.response;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FoodTruckResponse {

    private Boolean hasNext;
    private List<FoodTruckItem> foodTruckItems = new ArrayList<>();

    @Builder
    public FoodTruckResponse(Boolean hasNext, List<FoodTruckItem> foodTruckItems) {
        this.hasNext = hasNext;
        this.foodTruckItems = foodTruckItems;
    }
}
