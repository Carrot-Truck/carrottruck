package com.boyworld.carrot.api.service.statistics.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class SalesByDayDto {

    private String day;

    private SalesDto salesDto;

    @Builder
    public SalesByDayDto(String day, SalesDto salesDto) {
        this.day = day;
        this.salesDto = salesDto;
    }
}
