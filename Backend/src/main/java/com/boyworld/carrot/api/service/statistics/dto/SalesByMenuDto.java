package com.boyworld.carrot.api.service.statistics.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class SalesByMenuDto {

    private Long menuId;

    private String menuName;

    private SalesDto salesDto;

    @Builder
    public SalesByMenuDto(Long menuId, String menuName, SalesDto salesDto) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.salesDto = salesDto;
    }
}
