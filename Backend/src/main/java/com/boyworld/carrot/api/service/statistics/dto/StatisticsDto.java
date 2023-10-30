package com.boyworld.carrot.api.service.statistics.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class StatisticsDto {

    private String totalTime;

    private Integer totalSales;

    @Builder
    protected StatisticsDto(String totalTime, Integer totalSales) {
        this.totalTime = totalTime;
        this.totalSales = totalSales;
    }
}
