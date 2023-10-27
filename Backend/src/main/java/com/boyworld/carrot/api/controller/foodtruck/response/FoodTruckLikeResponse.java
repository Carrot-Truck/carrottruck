package com.boyworld.carrot.api.controller.foodtruck.response;

import lombok.Builder;
import lombok.Data;

@Data
public class FoodTruckLikeResponse {

    private Long foodTruckLikeId;
    private Long foodTruckId;
    private Boolean isLiked;

    @Builder
    public FoodTruckLikeResponse(Long foodTruckLikeId, Long foodTruckId, Boolean isLiked) {
        this.foodTruckLikeId = foodTruckLikeId;
        this.foodTruckId = foodTruckId;
        this.isLiked = isLiked;
    }
}
