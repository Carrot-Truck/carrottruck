package com.boyworld.carrot.api.controller.cart.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CartMenuRequest {
    @NotNull
    private Long memberId;

    @NotNull
    private Long foodTruckId;

    @NotNull
    private Long menuId;

    @NotNull
    private Integer menuQuantity;

    private List<CartMenuOptionRequest> menuOptions;
}
