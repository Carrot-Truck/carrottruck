package com.boyworld.carrot.api.service.order.dto;

import com.boyworld.carrot.domain.cart.CartMenu;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateOrderMenuDto {
    private Long id;
    private Long menuId;
    private Integer quantity;
    private List<Long> menuOptionList;

    @Builder
    public CreateOrderMenuDto(Long id, Long menuId, Integer quantity, List<Long> menuOptionList) {
        this.id = id;
        this.menuId = menuId;
        this.quantity = quantity;
        this.menuOptionList = menuOptionList;
    }

    public static CreateOrderMenuDto of(CartMenu cartMenu, List<Long> menuOptionIdList) {
        return CreateOrderMenuDto.builder()
                .menuId(cartMenu.getMenuId())
                .quantity(cartMenu.getQuantity())
                .menuOptionList(menuOptionIdList)
                .build();
    }
}
