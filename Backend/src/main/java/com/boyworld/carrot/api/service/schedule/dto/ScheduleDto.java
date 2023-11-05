package com.boyworld.carrot.api.service.schedule.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class ScheduleDto {
    private Long scheduleId;
    private String address;
    private String dayOfWeek;
    private String startTime;
    private String endTime;

    @Builder
    public ScheduleDto(Long scheduleId, String address, String dayOfWeek, String startTime, String endTime) {
        this.scheduleId = scheduleId;
        this.address = address;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
