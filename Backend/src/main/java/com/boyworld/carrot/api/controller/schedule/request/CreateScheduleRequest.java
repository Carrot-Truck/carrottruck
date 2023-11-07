package com.boyworld.carrot.api.controller.schedule.request;

import com.boyworld.carrot.api.service.schedule.dto.CreateScheduleDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CreateScheduleRequest {

    private Long foodTruckId;
    private String address;
    private String latitude;
    private String longitude;
    private String dayOfWeek;
    private String startTime;
    private String endTime;

    @Builder
    public CreateScheduleRequest(Long foodTruckId, String address, String latitude, String longitude, String dayOfWeek, String startTime, String endTime) {
        this.foodTruckId = foodTruckId;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public CreateScheduleDto toCreateScheduleDto() {
        return CreateScheduleDto.builder()
                .foodTruckId(this.foodTruckId)
                .address(this.address)
                .latitude(new BigDecimal(this.latitude))
                .longitude(new BigDecimal(this.longitude))
                .dayOfWeek(this.dayOfWeek)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .build();
    }
}
