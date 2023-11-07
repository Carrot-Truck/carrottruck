package com.boyworld.carrot.api.service.statistics.dto.details;

import lombok.Builder;
import lombok.Data;

@Data
public class SalesByMenuDto {

    private Long menuId;

    private String menuName;

    private Integer totalOrders;

    private Integer totalSales;

    @Builder
    public SalesByMenuDto(Long menuId, String menuName, Integer totalOrders, Integer totalSales) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.totalOrders = totalOrders;
        this.totalSales = totalSales;
    }
}
