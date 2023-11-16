package com.boyworld.carrot.api.service.order.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderMenuItem {

    private Long id;
    private Long menuId;
    private Integer quantity;
    private String menuName;
    private Integer price;
    private List<Long> menuOptionList;

    @Builder
    public OrderMenuItem(Long id, Long menuId, Integer quantity, String menuName,
                         Integer price, List<Long> menuOptionList) {
        this(id, menuId, menuName, price, quantity);
        this.menuOptionList = menuOptionList;
    }

    public OrderMenuItem(Long id, Long menuId, String menuName, Integer price, Integer quantity) {
        this.id = id;
        this.menuId = menuId;
        this.menuName = menuName;
        this.price = price;
        this.quantity = quantity;
    }
}
