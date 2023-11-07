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

    private Integer totalHours;

    private Integer totalMinutes;

    private Integer totalSales;

    @Builder
    public StatisticsBySalesDto(Long salesId, String date, String startTime, String endTime, String address, Integer totalHours, Integer totalMinutes, Integer totalSales) {
        this.salesId = salesId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.address = address;
        this.totalHours = totalHours;
        this.totalMinutes = totalMinutes;
        this.totalSales = totalSales;
    }
}
