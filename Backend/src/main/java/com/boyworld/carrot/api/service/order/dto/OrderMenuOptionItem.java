package com.boyworld.carrot.api.service.order.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderMenuOptionItem {

    Long id;
    Integer quantity;

    @Builder
    public OrderMenuOptionItem(Long id, Integer quantity) {
        this.id = id;
        this.quantity = quantity;
    }
}
