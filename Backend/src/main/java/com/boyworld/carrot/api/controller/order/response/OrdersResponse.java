package com.boyworld.carrot.api.controller.order.response;

import com.boyworld.carrot.api.service.order.dto.OrderItem;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrdersResponse {

    List<OrderItem> orderItems;
}
