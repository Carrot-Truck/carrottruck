package com.boyworld.carrot.api.controller.statistics.response;

import com.boyworld.carrot.api.service.statistics.dto.SalesByDayDto;
import com.boyworld.carrot.api.service.statistics.dto.SalesByHourDto;
import com.boyworld.carrot.api.service.statistics.dto.SalesByMenuDto;
import com.boyworld.carrot.api.service.statistics.dto.StatisticsByWeekDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class StatisticsByWeekDetailsResponse {

    private StatisticsByWeekDto statisticsByWeekDto;

    private List<SalesByMenuDto> salesByMenu;

    private List<SalesByHourDto> salesByHour;

    private List<SalesByDayDto> salesByDay;
}
