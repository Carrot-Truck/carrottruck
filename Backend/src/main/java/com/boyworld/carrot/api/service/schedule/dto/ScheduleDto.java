package com.boyworld.carrot.api.service.schedule.dto;

import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class ScheduleDto {
    private Long scheduleId;
    private String address;
    private String dayOfWeek;
    private String startTime;
    private String endTime;

    @Builder
    public ScheduleDto(Long scheduleId, String address, DayOfWeek dayOfWeek, LocalDateTime startTime, LocalDateTime endTime) {
        this.scheduleId = scheduleId;
        this.address = address;
        this.dayOfWeek = dayOfWeek.name();
        this.startTime = startTime.format(DateTimeFormatter.ofPattern("hh:mm"));
        this.endTime = endTime != null ? endTime.format(DateTimeFormatter.ofPattern("hh:mm")) : "null";
    }
}
