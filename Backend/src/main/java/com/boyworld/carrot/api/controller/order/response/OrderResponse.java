package com.boyworld.carrot.api.controller.order.response;

import com.boyworld.carrot.api.service.order.dto.OrderItem;
import com.boyworld.carrot.api.service.order.dto.OrderMenuItem;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class OrderResponse {

    private OrderItem orderItem;

    @Builder
    public OrderResponse(OrderItem orderItem) {
        this.orderItem = orderItem;
    }
}
