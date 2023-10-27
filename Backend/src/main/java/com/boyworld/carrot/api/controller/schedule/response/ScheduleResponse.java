package com.boyworld.carrot.api.controller.schedule.response;

import lombok.Builder;
import lombok.Data;

@Data
public class ScheduleResponse {

    private Long scheduleId;
    private String address;
    private String days;
    private String startTime;
    private String endTime;

    @Builder
    public ScheduleResponse(Long scheduleId, String address, String days, String startTime, String endTime) {
        this.scheduleId = scheduleId;
        this.address = address;
        this.days = days;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
