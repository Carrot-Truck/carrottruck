package com.boyworld.carrot.api.controller.statistics.response;

import com.boyworld.carrot.api.service.statistics.dto.StatisticsBySalesDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class StatisticsBySalesResponse {

    private List<StatisticsBySalesDto> statisticsBySales;

    @Builder
    public StatisticsBySalesResponse(List<StatisticsBySalesDto> statisticsBySales) {
        this.statisticsBySales = statisticsBySales;
    }
}
