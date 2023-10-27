package com.boyworld.carrot.api.controller.schedule.request;

import com.boyworld.carrot.api.service.schedule.dto.EditScheduleDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EditScheduleRequest {
    private Long foodTruckId;
    private String address;
    private String latitude;
    private String longitude;
    private String days;
    private String startTime;
    private String endTime;

    @Builder
    public EditScheduleRequest(Long foodTruckId, String address, String latitude, String longitude, String days, String startTime, String endTime) {
        this.foodTruckId = foodTruckId;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.days = days;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public EditScheduleDto toEditScheduleDto() {
        return EditScheduleDto.builder()
                .foodTruckId(this.foodTruckId)
                .address(this.address)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .days(this.days)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .build();
    }
}
