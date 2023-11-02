package com.boyworld.carrot.api.controller.foodtruck.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FoodTruckResponse<T> {

    private Boolean hasNext;
    private T items;

    public FoodTruckResponse(Boolean hasNext, T items) {
        this.hasNext = hasNext;
        this.items = items;
    }

    public static <T> FoodTruckResponse<T> of(Boolean hasNext, T items) {
        return new FoodTruckResponse<>(hasNext, items);
    }
}
