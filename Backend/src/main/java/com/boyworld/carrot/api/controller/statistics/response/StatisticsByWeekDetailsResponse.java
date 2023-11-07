package com.boyworld.carrot.api.controller.statistics.response;

import com.boyworld.carrot.api.service.statistics.dto.SalesByDayDto;
import com.boyworld.carrot.api.service.statistics.dto.SalesByHourDto;
import com.boyworld.carrot.api.service.statistics.dto.SalesByMenuDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class StatisticsByWeekDetailsResponse {

    private List<SalesByMenuDto> salesByMenu;

    private List<SalesByHourDto> salesByHour;

    private List<SalesByDayDto> salesByDay;

    @Builder
    public StatisticsByWeekDetailsResponse(List<SalesByMenuDto> salesByMenu,
                                           List<SalesByHourDto> salesByHour,
                                           List<SalesByDayDto> salesByDay) {
        this.salesByMenu = salesByMenu;
        this.salesByHour = salesByHour;
        this.salesByDay = salesByDay;
    }
}
