package com.boyworld.carrot.api.controller.cart.response;

import com.boyworld.carrot.api.service.cart.dto.CartMenuDto;
import com.boyworld.carrot.domain.cart.Cart;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CartResponse {
    @NotBlank
    private String foodTruckName;
    @NotNull
    private Integer totalPrice;
    @NotNull
    private List<CartMenuDto> cartMenus;

    @Builder
    public CartResponse(String foodTruckName, Integer totalPrice, List<CartMenuDto> cartMenus) {
        this.foodTruckName = foodTruckName;
        this.totalPrice = totalPrice;
        this.cartMenus = cartMenus;
    }

    public static CartResponse of(Cart cart, List<CartMenuDto> cartMenus) {
        return CartResponse.builder()
                .foodTruckName(cart.getFoodTruckName())
                .totalPrice(cart.getTotalPrice())
                .cartMenus(cartMenus)
                .build();
    }
}
