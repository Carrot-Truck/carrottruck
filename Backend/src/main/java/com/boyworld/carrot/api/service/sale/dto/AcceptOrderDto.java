package com.boyworld.carrot.api.service.sale.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class AcceptOrderDto {
    private Long orderId;
    private Integer prepareTime;

    @Builder
    public AcceptOrderDto(Long orderId, Integer prepareTime) {
        this.orderId = orderId;
        this.prepareTime = prepareTime;
    }
}
