package com.boyworld.carrot.api.controller.schedule;

import com.boyworld.carrot.api.ApiResponse;
import com.boyworld.carrot.api.controller.schedule.request.CreateScheduleRequest;
import com.boyworld.carrot.api.controller.schedule.response.ScheduleDetailResponse;
import com.boyworld.carrot.api.controller.schedule.response.ScheduleResponse;
import com.boyworld.carrot.api.service.schedule.ScheduleQueryService;
import com.boyworld.carrot.api.service.schedule.ScheduleService;
import com.boyworld.carrot.security.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 푸드트럭 스케줄 API 컨트롤러
 *
 * @author 최영환
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduleQueryService scheduleQueryService;

    /**
     * 스케줄 등록 API
     *
     * @param request 등록할 스케줄 정보
     * @return 등록된 스케줄 정보
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ScheduleDetailResponse> createSchedule(@Valid @RequestBody CreateScheduleRequest request) {
        log.debug("ScheduleController#createSchedule called");
        log.debug("CreateScheduleRequest={}", request);

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        ScheduleDetailResponse response = scheduleService.createSchedule(request.toCreateScheduleDto(), email);
        log.debug("ScheduleResponse={}", response);

        return ApiResponse.created(response);
    }


    /**
     * 푸드트럭 스케줄 목록 조회 API
     *
     * @param foodTruckId 푸드트럭 식별키
     * @return 해당 푸드트럭 스케줄 목록
     */
    @GetMapping
    public ApiResponse<ScheduleResponse> getSchedules(@RequestParam Long foodTruckId) {
        log.debug("ScheduleController#getSchedules called");
        log.debug("foodTruckId={}", foodTruckId);

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        ScheduleResponse response = scheduleQueryService.getSchedules(foodTruckId, email);
        log.debug("ScheduleResponse={}", response);

        return ApiResponse.ok(response);
    }
}
