package com.boyworld.carrot.api.controller.schedule.response;

import com.boyworld.carrot.api.service.schedule.dto.ScheduleDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class ScheduleResponse {

    private List<ScheduleDto> schedules;

    @Builder
    public ScheduleResponse(List<ScheduleDto> schedules) {
        this.schedules = schedules;
    }
}
