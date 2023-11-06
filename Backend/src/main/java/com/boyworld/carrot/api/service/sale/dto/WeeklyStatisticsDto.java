package com.boyworld.carrot.api.service.sale.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class WeeklyStatisticsDto {

    private Long days;

    private Integer totalMinutes;

    private Integer totalAmount;

    @Builder
    public WeeklyStatisticsDto(Long days, Integer totalMinutes, Integer totalAmount) {
        this.days = days;
        this.totalMinutes = totalMinutes;
        this.totalAmount = totalAmount;
    }
}
