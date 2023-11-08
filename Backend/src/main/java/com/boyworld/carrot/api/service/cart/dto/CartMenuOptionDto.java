package com.boyworld.carrot.api.service.cart.dto;

import com.boyworld.carrot.domain.cart.CartMenuOption;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartMenuOptionDto {
    private String menuOptionName;
    private Integer menuOptionPrice;

    @Builder
    public CartMenuOptionDto(String menuOptionName, Integer menuOptionPrice) {
        this.menuOptionName = menuOptionName;
        this.menuOptionPrice = menuOptionPrice;
    }

    public static CartMenuOptionDto of(CartMenuOption cartMenuOption) {
        return CartMenuOptionDto.builder()
                .menuOptionName(cartMenuOption.getName())
                .menuOptionPrice(cartMenuOption.getPrice())
                .build();
    }
}
