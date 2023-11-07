package com.boyworld.carrot.api.service.schedule;

import com.boyworld.carrot.api.controller.schedule.response.ScheduleDetailResponse;
import com.boyworld.carrot.api.controller.schedule.response.ScheduleResponse;
import com.boyworld.carrot.api.service.schedule.dto.ScheduleDto;
import com.boyworld.carrot.domain.foodtruck.repository.query.ScheduleQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 푸드트럭 조회 서비스
 *
 * @author 최영환
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleQueryService {

    private final ScheduleQueryRepository scheduleQueryRepository;

    /**
     * 푸드트럭 스케줄 목록 조회
     *
     * @param foodTruckId 조회할 푸드트럭 식별키
     * @return 해당 푸드트럭의 스케줄 목록
     */
    public ScheduleResponse getSchedules(Long foodTruckId) {
        List<ScheduleDto> schedules = scheduleQueryRepository.getSchedulesByFoodTruckId(foodTruckId);
        return ScheduleResponse.of(schedules);
    }

    /**
     * 푸드트럭 스케줄 상세 조회
     *
     * @param scheduleId 조회할 스케줄 식별키
     * @param email      현재 로그인 한 사용자 이메일
     * @return 해당 스케줄 상세 정보
     */
    public ScheduleDetailResponse getSchedule(Long scheduleId, String email) {
        return null;
    }
}
