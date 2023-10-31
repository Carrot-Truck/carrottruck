package com.boyworld.carrot.api.controller.order.request;

import com.boyworld.carrot.api.service.order.dto.CreateOrderDto;
import com.boyworld.carrot.api.service.order.dto.OrderMenuItem;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateOrderRequest {

    List<OrderMenuItem> orderMenuItems;
    @Builder
    public CreateOrderRequest(List<OrderMenuItem> orderMenuItems) {
        this.orderMenuItems = orderMenuItems;
    }

    public CreateOrderDto toCreateOrderDto() {
        return CreateOrderDto.builder()
            .orderMenuItems(this.orderMenuItems)
            .build();
    }
}