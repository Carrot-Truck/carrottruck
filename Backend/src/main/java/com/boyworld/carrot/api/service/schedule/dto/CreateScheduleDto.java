package com.boyworld.carrot.api.service.schedule.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateScheduleDto {

    private Long foodTruckId;
    private String address;
    private String latitude;
    private String longitude;
    private String days;
    private String startTime;
    private String endTime;

    @Builder
    public CreateScheduleDto(Long foodTruckId, String address, String latitude, String longitude, String days, String startTime, String endTime) {
        this.foodTruckId = foodTruckId;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.days = days;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
