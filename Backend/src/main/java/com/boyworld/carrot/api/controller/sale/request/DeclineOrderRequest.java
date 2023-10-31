package com.boyworld.carrot.api.controller.sale.request;

import com.boyworld.carrot.api.service.sale.dto.DeclineOrderDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeclineOrderRequest {

    private Long orderId;
    private String reason;

    @Builder
    public DeclineOrderRequest(Long orderId, String reason) {
        this.orderId = orderId;
        this.reason = reason;
    }

    public DeclineOrderDto toDeclineOrderDto() {
        return DeclineOrderDto.builder()
            .orderId(this.orderId)
            .reason(this.reason)
            .build();
    }
}
