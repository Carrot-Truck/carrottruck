package com.boyworld.carrot.api.controller.schedule.response;

import com.boyworld.carrot.domain.foodtruck.Schedule;
import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;

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

    public static ScheduleDetailResponse of(Schedule schedule) {
        return ScheduleDetailResponse.builder()
                .scheduleId(schedule.getId())
                .address(schedule.getAddress())
                .dayOfWeek(schedule.getDayOfWeek().name())
                .latitude(schedule.getLatitude().toString())
                .longitude(schedule.getLongitude().toString())
                .startTime(DateTimeFormatter.ofPattern("HH:mm").format(schedule.getStartTime()))
                .endTime(DateTimeFormatter.ofPattern("HH:mm").format(schedule.getEndTime()))
                .build();
    }
}
