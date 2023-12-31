package com.boyworld.carrot.api.service.order.dto;

import java.util.List;

import com.boyworld.carrot.domain.cart.Cart;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateOrderDto {

    Long foodTruckId;
    Integer totalPrice;
    List<CreateOrderMenuDto> orderMenuItems;

    @Builder
    public CreateOrderDto(Long foodTruckId, Integer totalPrice, List<CreateOrderMenuDto> orderMenuItems) {
        this.foodTruckId = foodTruckId;
        this.totalPrice = totalPrice;
        this.orderMenuItems = orderMenuItems;
    }

    public static CreateOrderDto of(Cart cart, List<CreateOrderMenuDto> orderMenuItems) {
        return CreateOrderDto.builder()
                .foodTruckId(cart.getFoodTruckId())
                .totalPrice(cart.getTotalPrice())
                .orderMenuItems(orderMenuItems)
                .build();
    }
}
