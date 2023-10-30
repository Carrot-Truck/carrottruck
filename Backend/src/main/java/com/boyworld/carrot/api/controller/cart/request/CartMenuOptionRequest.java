package com.boyworld.carrot.api.controller.cart.request;

import jakarta.validation.constraints.NotNull;

public class CartMenuOptionRequest {
    @NotNull
    private Long menuOptionId;

    @NotNull
    private Integer menuOptionQuantity;
}
