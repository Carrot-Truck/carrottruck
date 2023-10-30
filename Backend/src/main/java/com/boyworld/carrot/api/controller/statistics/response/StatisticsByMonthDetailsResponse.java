package com.boyworld.carrot.api.controller.statistics.response;

import com.boyworld.carrot.api.service.statistics.dto.SalesByDayDto;
import com.boyworld.carrot.api.service.statistics.dto.SalesByHourDto;
import com.boyworld.carrot.api.service.statistics.dto.SalesByMenuDto;
import com.boyworld.carrot.api.service.statistics.dto.StatisticsByMonthDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class StatisticsByMonthDetailsResponse {

    private StatisticsByMonthDto statisticsByMonthDto;

    private List<SalesByMenuDto> salesByMenu;

    private List<SalesByHourDto> salesByHour;

    private List<SalesByDayDto> salesByDay;
}
