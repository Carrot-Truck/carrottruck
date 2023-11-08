package com.boyworld.carrot.api.controller.statistics.response;

import com.boyworld.carrot.api.service.statistics.dto.list.StatisticsBySalesDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class StatisticsBySalesResponse {

    private Integer year;

    private List<StatisticsBySalesDto> statisticsBySales;

    private Boolean hasNext;

    @Builder
    public StatisticsBySalesResponse(Integer year, List<StatisticsBySalesDto> statisticsBySales, Boolean hasNext) {
        this.year = year;
        this.statisticsBySales = statisticsBySales;
        this.hasNext = hasNext;
    }

    public static StatisticsBySalesResponse of(Integer year, List<StatisticsBySalesDto> statisticsBySales, Boolean hasNext) {
        return StatisticsBySalesResponse.builder()
                .year(year)
                .statisticsBySales(statisticsBySales)
                .hasNext(hasNext)
                .build();
    }
}
