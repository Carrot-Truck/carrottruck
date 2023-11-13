package com.boyworld.carrot.api.controller.statistics.response;

import com.boyworld.carrot.api.service.statistics.dto.list.StatisticsByWeekDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class StatisticsByWeekResponse {

    private Integer year;

    private List<StatisticsByWeekDto> statisticsByWeek;

    private Integer lastWeek;

    private Boolean hasNext;

    @Builder
    public StatisticsByWeekResponse(Integer year, List<StatisticsByWeekDto> statisticsByWeek, Integer lastWeek, Boolean hasNext) {
        this.year = year;
        this.statisticsByWeek = statisticsByWeek;
        this.lastWeek = lastWeek;
        this.hasNext = hasNext;
    }

    public static StatisticsByWeekResponse of(Integer year, List<StatisticsByWeekDto> statisticsByWeek, Integer lastWeek, Boolean hasNext) {
        return StatisticsByWeekResponse.builder()
                .year(year)
                .statisticsByWeek(statisticsByWeek)
                .lastWeek(lastWeek)
                .hasNext(hasNext)
                .build();
    }
}
