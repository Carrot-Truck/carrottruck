package com.boyworld.carrot.api.controller.cart.response;

import com.boyworld.carrot.domain.cart.Cart;
import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import com.boyworld.carrot.domain.member.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartOrderResponse {
    private String foodTruckName;
    private Integer prepareTime;
    private String phoneNumber;
    private Integer totalPrice;

    @Builder
    public CartOrderResponse(String foodTruckName, Integer prepareTime, String phoneNumber, Integer totalPrice) {
        this.foodTruckName = foodTruckName;
        this.prepareTime = prepareTime;
        this.phoneNumber = phoneNumber;
        this.totalPrice = totalPrice;
    }

    public static CartOrderResponse of(FoodTruck foodTruck, Member member, Cart cart) {
        return CartOrderResponse.builder()
                .foodTruckName(foodTruck.getName())
                .prepareTime(foodTruck.getPrepareTime())
                .phoneNumber(member.getPhoneNumber())
                .totalPrice(cart.getTotalPrice())
                .build();
    }
}
