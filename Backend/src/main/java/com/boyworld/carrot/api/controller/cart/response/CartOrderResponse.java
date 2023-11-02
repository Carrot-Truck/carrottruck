package com.boyworld.carrot.api.controller.cart.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartOrderResponse {
    private String address;
    private Integer prepareTime;
    private String phoneNumber;
    private Integer totalPrice;

    @Builder
    public CartOrderResponse(String address, Integer prepareTime, String phoneNumber, Integer totalPrice) {
        this.address = address;
        this.prepareTime = prepareTime;
        this.phoneNumber = phoneNumber;
        this.totalPrice = totalPrice;
    }
}
