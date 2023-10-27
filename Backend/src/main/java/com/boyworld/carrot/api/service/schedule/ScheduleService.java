package com.boyworld.carrot.api.service.schedule;

import com.boyworld.carrot.api.controller.schedule.response.ScheduleDetailResponse;
import com.boyworld.carrot.api.service.schedule.dto.CreateScheduleDto;
import com.boyworld.carrot.api.service.schedule.dto.EditScheduleDto;
import com.boyworld.carrot.domain.foodtruck.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 푸드트럭 스케줄 서비스
 *
 * @author 최영환
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    /**
     * 스케줄 등록
     *
     * @param dto   스케줄 정보
     * @param email 현재 로그인 한 사용자 이메일
     * @return 등록된 스케줄 정보
     */
    public ScheduleDetailResponse createSchedule(CreateScheduleDto dto, String email) {
        return null;
    }

    /**
     * 스케줄 수정
     *
     * @param scheduleId 수정할 스케줄 식별키
     * @param dto        수정할 스케줄 정보
     * @param email      현재 로그인한 사용자 이메일
     * @return 수정된 스케줄 정보
     */
    public ScheduleDetailResponse editSchedule(Long scheduleId, EditScheduleDto dto, String email) {
        return null;
    }

    /**
     * 스케줄 삭제
     *
     * @param scheduleId 삭제할 스케줄 식별키
     * @param email      현재 로그인한 사용자 이메일
     * @return 삭제된 스케줄 식별키
     */
    public Long deleteSchedule(Long scheduleId, String email) {
        return null;
    }
}
