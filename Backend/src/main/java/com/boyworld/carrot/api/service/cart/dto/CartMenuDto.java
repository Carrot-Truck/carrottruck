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
    private String menuImageUrl;
    private List<CartMenuOptionDto> cartMenuOptionDtos;

    @Builder
    public CartMenuDto(Long cartMenuId, String menuName, Integer menuPrice, Integer cartMenuQuantity, String menuImageUrl, List<CartMenuOptionDto> cartMenuOptionDtos) {
        this.cartMenuId = cartMenuId;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.cartMenuQuantity = cartMenuQuantity;
        this.menuImageUrl = menuImageUrl;
        this.cartMenuOptionDtos = cartMenuOptionDtos;
    }
}
