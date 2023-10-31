package com.boyworld.carrot.api.controller.order.response;

import com.boyworld.carrot.api.service.order.dto.OrderItem;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;

public class OrderResponse {
    List<OrderItem> orderItems = new ArrayList<>();

    @Builder
    public OrderResponse(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
