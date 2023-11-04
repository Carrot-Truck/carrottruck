package com.boyworld.carrot.api.controller.foodtruck.response;

import com.boyworld.carrot.api.service.foodtruck.dto.FoodTruckMarkerItem;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FoodTruckMarkerResponse {

    private Integer markerCount;
    private List<FoodTruckMarkerItem> markerItems = new ArrayList<>();

    @Builder
    public FoodTruckMarkerResponse(Integer markerCount, List<FoodTruckMarkerItem> markerItems) {
        this.markerCount = markerCount;
        this.markerItems = markerItems;
    }

    public static FoodTruckMarkerResponse of(Integer markerCount, List<FoodTruckMarkerItem> markerItems) {
        return FoodTruckMarkerResponse.builder()
                .markerCount(markerCount)
                .markerItems(markerItems)
                .build();
    }
}
