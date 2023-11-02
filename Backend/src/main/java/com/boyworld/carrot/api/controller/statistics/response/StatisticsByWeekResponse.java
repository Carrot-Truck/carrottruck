package com.boyworld.carrot.api.controller.statistics.response;

import com.boyworld.carrot.api.service.statistics.dto.StatisticsByWeekDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class StatisticsByWeekResponse {

    private Integer year;

    private List<StatisticsByWeekDto> statisticsByWeek;

    private Boolean hasNext;

    @Builder
    public StatisticsByWeekResponse(Integer year, List<StatisticsByWeekDto> statisticsByWeek, Boolean hasNext) {
        this.year = year;
        this.statisticsByWeek = statisticsByWeek;
        this.hasNext = hasNext;
    }
}
