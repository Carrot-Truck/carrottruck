package com.boyworld.carrot.api.controller.statistics.response;

import com.boyworld.carrot.api.service.statistics.dto.StatisticsBySalesDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class StatisticsBySalesResponse {

    private Integer year;

    private List<StatisticsBySalesDto> statisticsBySales;

    @Builder
    public StatisticsBySalesResponse(Integer year, List<StatisticsBySalesDto> statisticsBySales) {
        this.year = year;
        this.statisticsBySales = statisticsBySales;
    }
}
