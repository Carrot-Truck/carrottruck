package com.boyworld.carrot.api.service.statistics.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class SalesByDayDto {

    private String day;

    private Integer totalOrders;

    private Integer totalSales;

    @Builder
    public SalesByDayDto(String day, Integer totalOrders, Integer totalSales) {
        this.day = day;
        this.totalOrders = totalOrders;
        this.totalSales = totalSales;
    }
}
