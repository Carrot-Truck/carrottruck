package com.boyworld.carrot.api.service.order.dto;

import java.util.List;

import com.boyworld.carrot.domain.cart.CartMenu;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderMenuItem {

    Long id;
    Long menuId;
    Integer quantity;
    List<Long> menuOptionList;

    @Builder
    public OrderMenuItem(Long id, Long menuId, Integer quantity, List<Long> menuOptionList) {
        this.id = id;
        this.menuId = menuId;
        this.quantity = quantity;
        this.menuOptionList = menuOptionList;
    }

    public static OrderMenuItem of(CartMenu cartMenu, List<Long> menuOptionIdList) {
        return OrderMenuItem.builder()
                .menuId(cartMenu.getMenuId())
                .quantity(cartMenu.getQuantity())
                .menuOptionIdList(menuOptionIdList)
                .build();
    }
}
