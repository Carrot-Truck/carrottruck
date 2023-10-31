package com.boyworld.carrot.api.service.cart.dto;

import lombok.Builder;

public class CreateCartMenuOptionDto {
    private String menuOptionName;
    private Integer menuOptionPrice;
    private Long menuOptionId;

    @Builder
    public CreateCartMenuOptionDto(String menuOptionName, Integer menuOptionPrice, Long menuOptionId) {
        this.menuOptionName = menuOptionName;
        this.menuOptionPrice = menuOptionPrice;
        this.menuOptionId = menuOptionId;
    }
}
