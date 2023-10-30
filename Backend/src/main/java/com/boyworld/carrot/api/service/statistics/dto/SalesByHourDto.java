package com.boyworld.carrot.api.service.statistics.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class SalesByHourDto {

    private String startHour;

    private SalesDto salesDto;

    @Builder
    public SalesByHourDto(String startHour, SalesDto salesDto) {
        this.startHour = startHour;
        this.salesDto = salesDto;
    }
}
