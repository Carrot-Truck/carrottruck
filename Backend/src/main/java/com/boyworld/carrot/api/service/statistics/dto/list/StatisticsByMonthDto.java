package com.boyworld.carrot.api.service.statistics.dto.list;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class StatisticsByMonthDto {

    private Integer month;

    private Integer totalHours;

    private Integer totalMinutes;

    private Integer totalSales;

    @Builder
    public StatisticsByMonthDto(Integer month, Integer totalHours, Integer totalMinutes, Integer totalSales) {
        this.month = month;
        this.totalHours = totalHours;
        this.totalMinutes = totalMinutes;
        this.totalSales = totalSales;
    }
}
