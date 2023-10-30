package com.boyworld.carrot.api.service.statistics.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class SalesDto {

    private Integer totalOrders;

    private Integer totalSales;

    @Builder
    protected SalesDto(Integer totalOrders, Integer totalSales) {
        this.totalOrders = totalOrders;
        this.totalSales = totalSales;
    }
}
