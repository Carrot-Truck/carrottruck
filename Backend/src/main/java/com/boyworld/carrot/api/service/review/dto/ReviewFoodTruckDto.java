package com.boyworld.carrot.api.service.review.dto;

import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewFoodTruckDto {
    private String foodTruckName;
    private Long foodTruckId;
}
