package com.boyworld.carrot.api.service.schedule;

import com.boyworld.carrot.api.controller.schedule.response.ScheduleDetailResponse;
import com.boyworld.carrot.api.controller.schedule.response.ScheduleResponse;
import com.boyworld.carrot.api.service.member.error.InValidAccessException;
import com.boyworld.carrot.api.service.schedule.dto.ScheduleDto;
import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import com.boyworld.carrot.domain.foodtruck.Schedule;
import com.boyworld.carrot.domain.foodtruck.repository.command.FoodTruckRepository;
import com.boyworld.carrot.domain.foodtruck.repository.query.ScheduleQueryRepository;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
    private final MemberRepository memberRepository;
    private final FoodTruckRepository foodTruckRepository;

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
     * @param scheduleId  조회할 스케줄 식별키
     * @param foodTruckId 푸드트럭 식별키
     * @param email       현재 로그인 한 사용자 이메일
     * @return 해당 스케줄 상세 정보
     */
    public ScheduleDetailResponse getSchedule(Long scheduleId, Long foodTruckId, String email) {
        Member member = getMemberByEmail(email);
        checkValidMemberAccess(member);

        FoodTruck foodTruck = getFoodTruckById(foodTruckId);
        checkOwnerAccess(member, foodTruck);

        Schedule schedule = scheduleQueryRepository.getScheduleById(scheduleId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 스케줄입니다."));
        return ScheduleDetailResponse.of(schedule);
    }

    /**
     * 이메일로 회원 엔티티 조회
     *
     * @param email 현재 로그인한 사용자 이메일
     * @return 이메일에 해당하는 회원 엔티티
     */
    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
    }

    /**
     * 접근 유효성 판별
     *
     * @param member 회원 엔티티
     * @throws InValidAccessException 해당 회원의 권한이 CLIENT 이거나 비활성화 상태인 경우
     */
    private void checkValidMemberAccess(Member member) {
        if (isClient(member.getRole()) || !member.getActive()) {
            throw new InValidAccessException("잘못된 접근입니다.");
        }
    }

    /**
     * 회원 권한이 CLIENT 인지 여부 판별
     *
     * @param role 회원 권한
     * @return true : CLIENT 인 경우 false: 그 외 경우
     */
    private boolean isClient(Role role) {
        return role.equals(Role.CLIENT);
    }

    /**
     * 소유주 접근 여부 판별
     *
     * @param member    접근하려는 회원
     * @param foodTruck 접근하려는 푸드트럭
     * @throws InValidAccessException 해당 회원이 해당 푸드트럭의 소유주가 아닌 경우
     */
    private void checkOwnerAccess(Member member, FoodTruck foodTruck) {
        if (!foodTruck.getVendor().getId().equals(member.getId())) {
            throw new InValidAccessException("잘못된 접근입니다.");
        }
    }

    /**
     * 푸드트럭 식별키로 푸드트럭 조회
     *
     * @param foodTruckId 푸드트럭 식별키
     * @return 푸드트럭 엔티티
     * @throws NoSuchElementException 식별키에 해당하는 푸드트럭이 없는 경우
     */
    private FoodTruck getFoodTruckById(Long foodTruckId) {
        return foodTruckRepository.findById(foodTruckId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 푸드트럭입니다."));
    }
}
