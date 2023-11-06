package com.boyworld.carrot.api.service.sale.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SalesStatisticsDto {

    private Long salesId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String address;

    private Integer totalAmount;

    @Builder
    public SalesStatisticsDto(Long salesId, LocalDateTime startTime, LocalDateTime endTime,
                              String address, Integer totalAmount) {
        this.salesId = salesId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.address = address;
        this.totalAmount = totalAmount;
    }
}
