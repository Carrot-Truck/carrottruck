package com.boyworld.carrot.api.service.schedule.dto;

import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import com.boyworld.carrot.domain.foodtruck.Schedule;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class CreateScheduleDto {

    private Long foodTruckId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String dayOfWeek;
    private String startTime;
    private String endTime;

    @Builder
    public CreateScheduleDto(Long foodTruckId, BigDecimal latitude, BigDecimal longitude,
                             String dayOfWeek, String startTime, String endTime) {
        this.foodTruckId = foodTruckId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Schedule toEntity(FoodTruck foodtruck, String address) {
        return Schedule.builder()
                .address(address)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .dayOfWeek(this.dayOfWeek)
                .startTime(LocalTime.parse(this.startTime))
                .endTime(LocalTime.parse(this.endTime))
                .active(true)
                .foodTruck(foodtruck)
                .build();
    }
}
