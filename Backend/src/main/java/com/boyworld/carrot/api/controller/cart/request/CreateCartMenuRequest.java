package com.boyworld.carrot.api.controller.cart.request;

import com.boyworld.carrot.api.service.cart.dto.CreateCartMenuDto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateCartMenuRequest {

    @NotNull
    private Long foodTruckId;

    @NotNull
    private Long menuId;

    @NotNull
    private Integer cartMenuQuantity;
    @NotNull
    private Integer cartMenuTotalPrice;

    private List<Long> menuOptionIds;


    @Builder
    public CreateCartMenuRequest(Long foodTruckId, Long menuId, Integer cartMenuQuantity, Integer cartMenuTotalPrice, List<Long> menuOptionIds) {
        this.foodTruckId = foodTruckId;
        this.menuId = menuId;
        this.cartMenuQuantity = cartMenuQuantity;
        this.cartMenuTotalPrice = cartMenuTotalPrice;
        this.menuOptionIds = menuOptionIds;
    }


    public CreateCartMenuDto toCreateMenuDto() {
        return CreateCartMenuDto.builder()
                .foodTruckId(this.foodTruckId)
                .menuId(this.menuId)
                .cartMenuQuantity(this.cartMenuQuantity)
                .cartMenuTotalPrice(this.cartMenuTotalPrice)
                .menuOptionIds(this.menuOptionIds)
                .build();
    }
}
