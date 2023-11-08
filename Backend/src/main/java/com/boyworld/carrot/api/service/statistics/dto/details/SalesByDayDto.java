package com.boyworld.carrot.api.service.statistics.dto.details;

import lombok.Builder;
import lombok.Data;

@Data
public class SalesByDayDto {

    private String day;

    private Integer totalOrders;

    private Integer totalSales;

    @Builder
    public SalesByDayDto(String day, Number totalOrders, Integer totalSales) {
        this.day = day;
        this.totalOrders = totalOrders.intValue();
        this.totalSales = totalSales;
    }
}
