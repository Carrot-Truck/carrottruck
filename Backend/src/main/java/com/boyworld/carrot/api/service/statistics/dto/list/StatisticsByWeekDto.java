package com.boyworld.carrot.api.service.statistics.dto.list;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class StatisticsByWeekDto {

    private String startDate;

    private String endDate;

    private Integer totalHours;

    private Integer totalMinutes;

    private Integer totalSales;

    @Builder
    public StatisticsByWeekDto(String startDate, String endDate, Integer totalHours, Integer totalMinutes, Integer totalSales) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalHours = totalHours;
        this.totalMinutes = totalMinutes;
        this.totalSales = totalSales;
    }
}
