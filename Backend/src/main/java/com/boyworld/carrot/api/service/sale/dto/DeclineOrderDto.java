package com.boyworld.carrot.api.service.sale.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class DeclineOrderDto {
    private Long orderId;
    private String reason;

    @Builder
    public DeclineOrderDto(Long orderId, String reason) {
        this.orderId = orderId;
        this.reason = reason;
    }
}
