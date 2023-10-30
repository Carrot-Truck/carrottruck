package com.boyworld.carrot.api.controller.statistics.response;

import com.boyworld.carrot.api.service.statistics.dto.StatisticsByMonthDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class StatisticsByMonthResponse {

    private List<StatisticsByMonthDto> statisticsByMonth;

    @Builder
    StatisticsByMonthResponse(List<StatisticsByMonthDto> statisticsByMonth) {
        this.statisticsByMonth = statisticsByMonth;
    }
}
