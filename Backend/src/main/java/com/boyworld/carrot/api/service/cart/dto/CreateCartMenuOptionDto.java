package com.boyworld.carrot.api.service.cart.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateCartMenuOptionDto {
    private String menuOptionName;
    private Integer menuOptionPrice;
    private Boolean menuOptionSoldOut;
    private Long menuOptionId;

    @Builder
    public CreateCartMenuOptionDto(String menuOptionName, Integer menuOptionPrice, Boolean menuOptionSoldOut, Long menuOptionId) {
        this.menuOptionName = menuOptionName;
        this.menuOptionPrice = menuOptionPrice;
        this.menuOptionSoldOut = menuOptionSoldOut;
        this.menuOptionId = menuOptionId;
    }
}
