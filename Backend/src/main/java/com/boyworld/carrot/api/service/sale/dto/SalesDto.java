package com.boyworld.carrot.api.service.sale.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SalesDto {

    private Long salesId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private Integer totalAmount;

    @Builder
    public SalesDto(Long salesId, LocalDateTime startTime, LocalDateTime endTime,
                    BigDecimal latitude, BigDecimal longitude, Integer totalAmount) {
        this.salesId = salesId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.totalAmount = totalAmount;
    }
}
