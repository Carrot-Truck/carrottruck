package com.boyworld.carrot.api.controller.order.response;

import com.boyworld.carrot.api.service.order.dto.OrderItem;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {

    OrderItem orderItem;
}
