package com.boyworld.carrot.api.service.statistics.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class SummaryByMonthDto {

    private Integer month;

    private Integer totalMinutes;

    private Integer totalAmount;

    @Builder
    public SummaryByMonthDto(Integer month, Integer totalMinutes, Integer totalAmount) {
        this.month = month;
        this.totalMinutes = totalMinutes;
        this.totalAmount = totalAmount;
    }
}
