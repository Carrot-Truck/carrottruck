package com.boyworld.carrot.api.controller.cart.request;

import com.boyworld.carrot.api.service.cart.dto.CreateCartMenuDto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.Collator;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CreateCartMenuRequest {

    @NotNull
    private Long foodTruckId;

    @NotNull
    private Long menuId;

    @NotNull
    private Integer cartMenuQuantity;

    private List<Long> menuOptionIds;


    @Builder
    public CreateCartMenuRequest(Long foodTruckId, Long menuId, Integer cartMenuQuantity, List<Long> menuOptionIds) {
        this.foodTruckId = foodTruckId;
        this.menuId = menuId;
        this.cartMenuQuantity = cartMenuQuantity;
        this.menuOptionIds = menuOptionIds;
    }


    public CreateCartMenuDto toCreateMenuDto() {
        return CreateCartMenuDto.builder()
                .foodTruckId(this.foodTruckId)
                .menuId(this.menuId)
                .cartMenuQuantity(this.cartMenuQuantity)
                .menuOptionIds(this.menuOptionIds)
                .build();
    }
}
