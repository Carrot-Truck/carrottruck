package com.boyworld.carrot.api.controller.schedule.response;

import lombok.Builder;
import lombok.Data;

@Data
public class ScheduleDetailResponse {

    private Long scheduleId;
    private String address;
    private String dayOfWeek;
    private String latitude;
    private String longitude;
    private String startTime;
    private String endTime;

    @Builder
    public ScheduleDetailResponse(Long scheduleId, String address, String dayOfWeek, String latitude, String longitude, String startTime, String endTime) {
        this.scheduleId = scheduleId;
        this.address = address;
        this.dayOfWeek = dayOfWeek;
        this.latitude = latitude;
        this.longitude = longitude;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
