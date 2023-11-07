package com.boyworld.carrot.api.service.schedule.dto;

import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
public class ScheduleDto {
    private Long scheduleId;
    private String address;
    private String dayOfWeek;
    private String startTime;
    private String endTime;

    @Builder
    public ScheduleDto(Long scheduleId, String address, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.scheduleId = scheduleId;
        this.address = address;
        this.dayOfWeek = dayOfWeek.name();
        this.startTime = startTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        this.endTime = endTime != null ? endTime.format(DateTimeFormatter.ofPattern("HH:mm")) : "null";
    }
}
