package com.boyworld.carrot.api.service.cart.dto;

import com.boyworld.carrot.domain.cart.CartMenu;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CartMenuDto {
    private String cartMenuId;
    private String menuName;
    private Integer menuPrice;
    private Integer cartMenuTotalPrice;
    private Integer cartMenuQuantity;
    private String menuImageUrl;
    private List<CartMenuOptionDto> cartMenuOptionDtos;


    @Builder
    public CartMenuDto(String cartMenuId, String menuName, Integer menuPrice, Integer cartMenuTotalPrice, Integer cartMenuQuantity, String menuImageUrl, List<CartMenuOptionDto> cartMenuOptionDtos) {
        this.cartMenuId = cartMenuId;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.cartMenuTotalPrice = cartMenuTotalPrice;
        this.cartMenuQuantity = cartMenuQuantity;
        this.menuImageUrl = menuImageUrl;
        this.cartMenuOptionDtos = cartMenuOptionDtos;
    }


    public static CartMenuDto of(CartMenu cartMenu, List<CartMenuOptionDto> cartMenuOptionDtos) {
        return CartMenuDto.builder()
                .cartMenuId(cartMenu.getId())
                .menuName(cartMenu.getName())
                .menuPrice(cartMenu.getPrice())
                .cartMenuTotalPrice(cartMenu.getCartMenuTotalPrice())
                .cartMenuQuantity(cartMenu.getQuantity())
                .menuImageUrl(cartMenu.getMenuImageUrl())
                .cartMenuOptionDtos(cartMenuOptionDtos)
                .build();
    }
}
