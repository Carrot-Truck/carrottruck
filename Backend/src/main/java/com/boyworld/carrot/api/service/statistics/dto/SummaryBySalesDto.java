package com.boyworld.carrot.api.service.statistics.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SummaryBySalesDto {

    private Long salesId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String address;

    private Integer totalAmount;

    @Builder
    public SummaryBySalesDto(Long salesId, LocalDateTime startTime, LocalDateTime endTime,
                             String address, Integer totalAmount) {
        this.salesId = salesId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.address = address;
        this.totalAmount = totalAmount;
    }
}
