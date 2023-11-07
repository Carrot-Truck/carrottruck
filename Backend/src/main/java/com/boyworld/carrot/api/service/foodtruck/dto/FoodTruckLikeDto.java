package com.boyworld.carrot.api.service.foodtruck.dto;

import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import com.boyworld.carrot.domain.foodtruck.FoodTruckLike;
import com.boyworld.carrot.domain.member.Member;
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

    public FoodTruckLike toEntity(Member member, FoodTruck foodTruck) {
        return FoodTruckLike.builder()
                .foodTruck(foodTruck)
                .member(member)
                .active(true)
                .build();
    }
}
