package com.boyworld.carrot.api.service.foodtruck.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FoodTruckLikeDto {

    private Long foodTruckId;

    @Builder
    public FoodTruckLikeDto(Long foodTruckId) {
        this.foodTruckId = foodTruckId;
    }
}
