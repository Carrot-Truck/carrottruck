package com.boyworld.carrot.api.service.order.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateOrderDto {

    Long foodTruckId;
    Integer totalPrice;
    List<OrderMenuItem> orderMenuItems;

    @Builder
    public CreateOrderDto(Long foodTruckId, Integer totalPrice, List<OrderMenuItem> orderMenuItems) {
        this.foodTruckId = foodTruckId;
        this.totalPrice = totalPrice;
        this.orderMenuItems = orderMenuItems;
    }
}