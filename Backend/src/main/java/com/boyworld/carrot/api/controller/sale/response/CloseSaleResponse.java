package com.boyworld.carrot.api.controller.sale.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
public class CloseSaleResponse {

    private Long saleId;
    private Integer orderNumber;
    private Integer totalAmount;
    private LocalDateTime createdTime;
    private LocalDateTime endTime;

    @Builder
    public CloseSaleResponse(Long saleId, Integer orderNumber, Integer totalAmount,
        LocalDateTime createdTime, LocalDateTime endTime) {
        this.saleId = saleId;
        this.orderNumber = orderNumber;
        this.totalAmount = totalAmount;
        this.createdTime = createdTime;
        this.endTime = endTime;
    }


}
