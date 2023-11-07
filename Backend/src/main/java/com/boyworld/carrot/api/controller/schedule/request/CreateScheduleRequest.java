package com.boyworld.carrot.api.controller.schedule.request;

import com.boyworld.carrot.api.service.schedule.dto.CreateScheduleDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CreateScheduleRequest {

    @NotNull
    private Long foodTruckId;

    @NotBlank
    private String latitude;

    @NotBlank
    private String longitude;

    @NotBlank
    private String dayOfWeek;

    @NotNull
    private String startTime;

    @NotNull
    private String endTime;

    @Builder
    public CreateScheduleRequest(Long foodTruckId, String latitude, String longitude, String dayOfWeek, String startTime, String endTime) {
        this.foodTruckId = foodTruckId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public CreateScheduleDto toCreateScheduleDto() {
        return CreateScheduleDto.builder()
                .foodTruckId(this.foodTruckId)
                .latitude(new BigDecimal(this.latitude))
                .longitude(new BigDecimal(this.longitude))
                .dayOfWeek(this.dayOfWeek)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .build();
    }
}
