package com.boyworld.carrot.api.service.statistics.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class StatisticsByMonthDto {

    private Integer month;

    private StatisticsDto statisticsDto;

    @Builder
    public StatisticsByMonthDto(Integer month, Integer totalHours, Integer totalMinutes, Integer totalSales) {
        this.month = month;
        this.statisticsDto = new StatisticsDto(totalHours, totalMinutes, totalSales);
    }

    @Builder
    public StatisticsByMonthDto(StatisticsByMonthDto statisticsByMonthDto) {
        this.month = statisticsByMonthDto.getMonth();
        this.statisticsDto = statisticsByMonthDto.getStatisticsDto();
    }
}
