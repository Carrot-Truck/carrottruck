package com.boyworld.carrot.api.service.schedule.dto;

import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import com.boyworld.carrot.domain.foodtruck.Schedule;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class CreateScheduleDto {

    private Long foodTruckId;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String dayOfWeek;
    private String startTime;
    private String endTime;

    @Builder
    public CreateScheduleDto(Long foodTruckId, String address, BigDecimal latitude, BigDecimal longitude,
                             String dayOfWeek, String startTime, String endTime) {
        this.foodTruckId = foodTruckId;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Schedule toEntity(FoodTruck foodtruck) {
        return Schedule.builder()
                .address(this.address)
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
