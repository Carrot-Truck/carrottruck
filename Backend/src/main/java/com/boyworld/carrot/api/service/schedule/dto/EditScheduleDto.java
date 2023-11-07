package com.boyworld.carrot.api.service.schedule.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor

public class EditScheduleDto {

    private Long foodTruckId;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String dayOfWeek;
    private String startTime;
    private String endTime;

    @Builder
    public EditScheduleDto(Long foodTruckId, String address, BigDecimal latitude, BigDecimal longitude,
                           String dayOfWeek, String startTime, String endTime) {
        this.foodTruckId = foodTruckId;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
