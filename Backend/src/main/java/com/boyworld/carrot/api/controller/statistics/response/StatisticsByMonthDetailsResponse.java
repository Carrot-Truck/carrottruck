package com.boyworld.carrot.api.controller.statistics.response;

import com.boyworld.carrot.api.service.statistics.dto.details.SalesByDayDto;
import com.boyworld.carrot.api.service.statistics.dto.details.SalesByHourDto;
import com.boyworld.carrot.api.service.statistics.dto.details.SalesByMenuDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class StatisticsByMonthDetailsResponse {

    private List<SalesByMenuDto> salesByMenu;

    private List<SalesByHourDto> salesByHour;

    private List<SalesByDayDto> salesByDay;

    @Builder
    public StatisticsByMonthDetailsResponse(List<SalesByMenuDto> salesByMenu,
                                            List<SalesByHourDto> salesByHour,
                                            List<SalesByDayDto> salesByDay) {
        this.salesByMenu = salesByMenu;
        this.salesByHour = salesByHour;
        this.salesByDay = salesByDay;
    }
}
