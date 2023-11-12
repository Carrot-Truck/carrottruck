package com.boyworld.carrot.api.controller.statistics.response;

import com.boyworld.carrot.api.service.statistics.dto.list.StatisticsBySalesDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class StatisticsBySalesResponse {

    private Integer year;

    private Long lastSalesId;

    private List<StatisticsBySalesDto> statisticsBySales;

    private Boolean hasNext;

    @Builder
    public StatisticsBySalesResponse(Integer year, Long lastSalesId, List<StatisticsBySalesDto> statisticsBySales, Boolean hasNext) {
        this.year = year;
        this.lastSalesId = lastSalesId;
        this.statisticsBySales = statisticsBySales;
        this.hasNext = hasNext;
    }

    public static StatisticsBySalesResponse of(Integer year, Long lastSalesId, List<StatisticsBySalesDto> statisticsBySales, Boolean hasNext) {
        return StatisticsBySalesResponse.builder()
                .year(year)
                .lastSalesId(lastSalesId)
                .statisticsBySales(statisticsBySales)
                .hasNext(hasNext)
                .build();
    }
}
