package com.boyworld.carrot.api.service.statistics.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class StatisticsByMonthDto {

    private String year;

    private String month;

    private StatisticsDto statisticsDto;

    @Builder
    public StatisticsByMonthDto(String year, String month, StatisticsDto statisticsDto) {
        this.year = year;
        this.month = month;
        this.statisticsDto = statisticsDto;
    }

    @Builder
    public StatisticsByMonthDto(StatisticsByMonthDto statisticsByMonthDto) {
        this.year = statisticsByMonthDto.getYear();
        this.month = statisticsByMonthDto.getMonth();
        this.statisticsDto = statisticsByMonthDto.getStatisticsDto();
    }
}
