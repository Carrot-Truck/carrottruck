package com.boyworld.carrot.api.service.cart.dto;

import com.boyworld.carrot.domain.cart.Cart;
import com.boyworld.carrot.domain.cart.CartMenu;
import com.boyworld.carrot.domain.menu.Menu;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateCartMenuDto {
    private Long foodTruckId;
    private Long menuId;
    private Integer cartMenuQuantity;
    private Integer menuPrice;
    private Integer cartMenuTotalPrice;
    private List<Long> menuOptionIds;


    @Builder
    public CreateCartMenuDto(Long foodTruckId, Long menuId, Integer cartMenuQuantity, Integer menuPrice, Integer cartMenuTotalPrice, List<Long> menuOptionIds) {
        this.foodTruckId = foodTruckId;
        this.menuId = menuId;
        this.cartMenuQuantity = cartMenuQuantity;
        this.menuPrice = menuPrice;
        this.cartMenuTotalPrice = cartMenuTotalPrice;
        this.menuOptionIds = menuOptionIds;
    }


    public CartMenu toEntity(Menu menu, String cartMenuId, String email) {
        return CartMenu.builder()
                .id(cartMenuId)
                .cartId(email)
                .menuId(menu.getId())
                .name(menu.getMenuInfo().getName())
                .price(menu.getMenuInfo().getPrice())
                .cartMenuTotalPrice(this.cartMenuTotalPrice)
                .quantity(this.cartMenuQuantity)
                .build();
    }
}
