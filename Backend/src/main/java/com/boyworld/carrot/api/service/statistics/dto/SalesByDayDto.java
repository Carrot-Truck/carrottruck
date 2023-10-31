package com.boyworld.carrot.api.service.statistics.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class SalesByDayDto {

    private String day;

    private SalesDto salesDto;

    @Builder
    public SalesByDayDto(String day, Integer totalOrders, Integer totalSales) {
        this.day = day;
        this.salesDto = new SalesDto(totalOrders, totalSales);
    }
}
