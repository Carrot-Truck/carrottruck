package com.boyworld.carrot.api.controller.sale.request;

import com.boyworld.carrot.api.service.sale.dto.AcceptOrderDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AcceptOrderRequest {
    private Long orderId;
    private Integer prepareTime;

    @Builder
    public AcceptOrderRequest(Long orderId, Integer prepareTime) {
        this.orderId = orderId;
        this.prepareTime = prepareTime;
    }

    public AcceptOrderDto toAcceptOrderDto() {
        return AcceptOrderDto.builder()
            .orderId(this.orderId)
            .prepareTime(this.prepareTime)
            .build();
    }
}
