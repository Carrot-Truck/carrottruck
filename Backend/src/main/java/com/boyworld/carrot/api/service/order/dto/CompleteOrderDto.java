package com.boyworld.carrot.api.service.order.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class CompleteOrderDto {

    Long orderId;

    public CompleteOrderDto(Long orderId) {
        this.orderId = orderId;
    }
}
