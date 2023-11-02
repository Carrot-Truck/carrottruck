package com.boyworld.carrot.api.service.cart.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CartMenuDto {
    private Long cartMenuId;
    private String menuName;
    private Integer menuPrice;
    private Integer cartMenuQuantity;
    private List<CartMenuOptionDto> cartMenuOptionDtos;

    @Builder
    public CartMenuDto(Long cartMenuId, String menuName, Integer menuPrice, Integer cartMenuQuantity, List<CartMenuOptionDto> cartMenuOptionDtos) {
        this.cartMenuId = cartMenuId;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.cartMenuQuantity = cartMenuQuantity;
        this.cartMenuOptionDtos = cartMenuOptionDtos;
    }
}
