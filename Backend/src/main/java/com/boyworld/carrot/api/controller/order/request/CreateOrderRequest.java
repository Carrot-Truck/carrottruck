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

    Long foodTruckId;
    Integer totalPrice;
    List<OrderMenuItem> orderMenuItems;

    @Builder
    public CreateOrderRequest(Long foodTruckId, Integer totalPrice, List<OrderMenuItem> orderMenuItems) {
        this.foodTruckId = foodTruckId;
        this.totalPrice = totalPrice;
        this.orderMenuItems = orderMenuItems;
    }

    public CreateOrderDto toCreateOrderDto() {
        return CreateOrderDto.builder()
            .foodTruckId(this.foodTruckId)
            .totalPrice(this.totalPrice)
            .orderMenuItems(this.orderMenuItems)
            .build();
    }
}