package com.boyworld.carrot.api.service.order.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderMenuItem {

    Long menuId;
    Integer quantity;
    List<Long> menuOptionIdList;

    @Builder
    public OrderMenuItem(Long menuId, Integer quantity, List<Long> menuOptionIdList) {
        this.menuId = menuId;
        this.quantity = quantity;
        this.menuOptionIdList = menuOptionIdList;
    }
}
