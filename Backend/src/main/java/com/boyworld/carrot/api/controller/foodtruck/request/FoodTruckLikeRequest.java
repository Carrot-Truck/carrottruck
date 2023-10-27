package com.boyworld.carrot.api.controller.foodtruck.request;

import com.boyworld.carrot.api.service.foodtruck.dto.FoodTruckLikeDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FoodTruckLikeRequest {

    private Long foodTruckId;

    @Builder
    public FoodTruckLikeRequest(Long foodTruckId) {
        this.foodTruckId = foodTruckId;
    }

    public FoodTruckLikeDto toFoodTruckLikeDto() {
        return FoodTruckLikeDto.builder()
                .foodTruckId(this.foodTruckId)
                .build();
    }
}
