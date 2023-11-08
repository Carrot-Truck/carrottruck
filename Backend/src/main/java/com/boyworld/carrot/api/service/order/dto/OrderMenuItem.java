package com.boyworld.carrot.api.service.order.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderMenuItem {

    Long id;
    Long menuId;
    Integer quantity;
    List<OrderMenuOptionItem> menuOptionList;

    @Builder
    public OrderMenuItem(Long id, Long menuId, Integer quantity, List<OrderMenuOptionItem> menuOptionList) {
        this.id = id;
        this.menuId = menuId;
        this.quantity = quantity;
        this.menuOptionList = menuOptionList;
    }
}
