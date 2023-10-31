package com.boyworld.carrot.api.service.statistics.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class SalesByHourDto {

    private Integer startHour;

    private SalesDto salesDto;

    @Builder
    public SalesByHourDto(Integer startHour, Integer totalOrders, Integer totalSales) {
        this.startHour = startHour;
        this.salesDto = new SalesDto(totalOrders, totalSales);
    }
}
