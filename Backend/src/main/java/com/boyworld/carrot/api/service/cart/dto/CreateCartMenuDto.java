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
    private List<Long> cartMenuOptionIds;

    @Builder
    public CreateCartMenuDto(Long foodTruckId, Long menuId, Integer cartMenuQuantity, List<Long> cartMenuOptionIds) {
        this.foodTruckId = foodTruckId;
        this.menuId = menuId;
        this.cartMenuQuantity = cartMenuQuantity;
        this.cartMenuOptionIds = cartMenuOptionIds;
    }

    public CartMenu toEntity(Menu menu, String cartMenuId, String email) {
        return CartMenu.builder()
                .id(cartMenuId)
                .cartId(email)
                .menuId(menu.getId())
                .name(menu.getMenuInfo().getName())
                .price(menu.getMenuInfo().getPrice())
                .quantity(this.cartMenuQuantity)
                .build();
    }
}
