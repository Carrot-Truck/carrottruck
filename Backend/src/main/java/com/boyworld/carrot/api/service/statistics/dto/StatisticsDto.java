package com.boyworld.carrot.api.service.statistics.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class StatisticsDto {

    private Integer totalHours;

    private Integer totalMinutes;

    private Integer totalSales;

    @Builder
    protected StatisticsDto(Integer totalHours, Integer totalMinutes, Integer totalSales) {
        this.totalHours = totalHours;
        this.totalMinutes = totalMinutes;
        this.totalSales = totalSales;
    }
}
