package com.boyworld.carrot.api.service.statistics.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class StatisticsBySalesDto {

    private Long salesId;

    private String date;

    private String startTime;

    private String endTime;

    private String address;

    private StatisticsDto statisticsDto;

    @Builder
    public StatisticsBySalesDto(Long salesId, String date, String startTime, String endTime, String address, Integer totalHours, Integer totalMinutes, Integer totalSales) {
        this.salesId = salesId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.address = address;
        this.statisticsDto = new StatisticsDto(totalHours, totalMinutes, totalSales);
    }

    @Builder
    public StatisticsBySalesDto(StatisticsBySalesDto statisticsBySalesDto) {
        this.salesId = statisticsBySalesDto.getSalesId();
        this.date = statisticsBySalesDto.getDate();
        this.startTime = statisticsBySalesDto.getStartTime();
        this.endTime = statisticsBySalesDto.getDate();
        this.address = statisticsBySalesDto.getAddress();
        this.statisticsDto = statisticsBySalesDto.getStatisticsDto();
    }
}
