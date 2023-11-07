package com.boyworld.carrot.api.service.sale.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class MonthlyStatisticsDto {

    private Integer month;

    private Integer totalMinutes;

    private Integer totalAmount;

    @Builder
    public MonthlyStatisticsDto(Integer month, Integer totalMinutes, Integer totalAmount) {
        this.month = month;
        this.totalMinutes = totalMinutes;
        this.totalAmount = totalAmount;
    }
}
