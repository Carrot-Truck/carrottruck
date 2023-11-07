package com.boyworld.carrot.api.service.statistics.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class SummaryByWeekDto {

    private Long days;

    private Integer totalMinutes;

    private Integer totalAmount;

    @Builder
    public SummaryByWeekDto(Long days, Integer totalMinutes, Integer totalAmount) {
        this.days = days;
        this.totalMinutes = totalMinutes;
        this.totalAmount = totalAmount;
    }
}
