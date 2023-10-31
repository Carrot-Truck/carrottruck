package com.boyworld.carrot.api.service.order.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateOrderDto {

    List<OrderMenuItem> orderMenuItems;

    @Builder
    public CreateOrderDto(List<OrderMenuItem> orderMenuItems) {
        this.orderMenuItems = orderMenuItems;
    }
}