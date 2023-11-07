package com.boyworld.carrot.api.controller.statistics.response;

import com.boyworld.carrot.api.service.statistics.dto.details.SalesByHourDto;
import com.boyworld.carrot.api.service.statistics.dto.details.SalesByMenuDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class StatisticsBySalesDetailsResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal latitude;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal longitude;

    private List<SalesByMenuDto> salesByMenu;

    private List<SalesByHourDto> salesByHour;

    @Builder
    public StatisticsBySalesDetailsResponse(BigDecimal latitude, BigDecimal longitude, List<SalesByMenuDto> salesByMenu, List<SalesByHourDto> salesByHour) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.salesByMenu = salesByMenu;
        this.salesByHour = salesByHour;
    }

}
