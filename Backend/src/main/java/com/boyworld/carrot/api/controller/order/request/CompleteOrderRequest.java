package com.boyworld.carrot.api.controller.order.request;

import com.boyworld.carrot.api.service.order.dto.CompleteOrderDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompleteOrderRequest {

    Long orderId;

    @Builder
    public CompleteOrderRequest(Long orderId) {
        this.orderId = orderId;
    }

    public CompleteOrderDto toCompleteOrderDto() {
       return CompleteOrderDto.builder()
            .orderId(this.orderId)
            .build();
    }
}
