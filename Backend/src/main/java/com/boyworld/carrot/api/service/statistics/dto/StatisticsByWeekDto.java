package com.boyworld.carrot.api.service.statistics.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class StatisticsByWeekDto {

    private String startDate;

    private String endDate;

    private StatisticsDto statisticsDto;

    @Builder
    public StatisticsByWeekDto(String startDate, String endDate, Integer totalHours, Integer totalMinutes, Integer totalSales) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.statisticsDto = new StatisticsDto(totalHours, totalMinutes, totalSales);
    }

    @Builder
    public StatisticsByWeekDto(StatisticsByWeekDto statisticsByWeekDto) {
        this.startDate = statisticsByWeekDto.getStartDate();
        this.endDate = statisticsByWeekDto.getEndDate();
        this.statisticsDto = statisticsByWeekDto.getStatisticsDto();
    }
}
