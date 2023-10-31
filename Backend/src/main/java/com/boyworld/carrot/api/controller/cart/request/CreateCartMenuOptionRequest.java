package com.boyworld.carrot.api.controller.cart.request;

import com.boyworld.carrot.api.service.cart.dto.CreateCartMenuOptionDto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public class CreateCartMenuOptionRequest {
    @NotNull
    private String menuOptionName;

    @NotNull
    private Integer menuOptionPrice;

    @NotNull
    private Long menuOptionId;

    @Builder
    public CreateCartMenuOptionRequest(@NotNull String menuOptionName, @NotNull Integer menuOptionPrice, @NotNull Long menuOptionId) {
        this.menuOptionName = menuOptionName;
        this.menuOptionPrice = menuOptionPrice;
        this.menuOptionId = menuOptionId;
    }


    public CreateCartMenuOptionDto toCreateCartMenuOptionDto() {
        return CreateCartMenuOptionDto.builder()
                .menuOptionName(this.menuOptionName)
                .menuOptionPrice(this.menuOptionPrice)
                .menuOptionId(this.menuOptionId)
                .build();
    }
}
