package com.boyworld.carrot.api.service.schedule;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.controller.schedule.response.ScheduleResponse;
import com.boyworld.carrot.api.service.schedule.dto.ScheduleDto;
import com.boyworld.carrot.domain.foodtruck.Category;
import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import com.boyworld.carrot.domain.foodtruck.Schedule;
import com.boyworld.carrot.domain.foodtruck.repository.command.CategoryRepository;
import com.boyworld.carrot.domain.foodtruck.repository.command.FoodTruckRepository;
import com.boyworld.carrot.domain.foodtruck.repository.command.ScheduleRepository;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 스케줄 조회 서비스 테스트
 *
 * @author 최영환
 */
@Slf4j
class ScheduleQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private ScheduleQueryService scheduleQueryService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FoodTruckRepository foodTruckRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @DisplayName("사용자는 푸드트럭 식별키로 해당 푸드트럭의 스케줄 리스트를 조회할 수 있다.")
    @Test
    void getSchedulesByFoodTruckId() {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@ssafy.com");

        Category category = createCategory("고기/구이");

        FoodTruck foodTruck = createFoodTruck(vendor1, category, "동현 된장삼겹", "010-1234-5678",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                40,
                10,
                true);

        createSchedules(foodTruck);

        // when
        ScheduleResponse response = scheduleQueryService.getSchedules(foodTruck.getId());
        for (ScheduleDto schedule : response.getSchedules()) {
            log.debug("schedule={}", schedule);
        }

        // then
        assertThat(response.getSchedules()).hasSize(3);
    }

    private Member createMember(Role role, String email) {
        Member member = Member.builder()
                .email(email)
                .nickname("매미킴")
                .encryptedPwd(passwordEncoder.encode("ssafy1234"))
                .name("김동현")
                .phoneNumber("010-1234-5678")
                .role(role)
                .active(true)
                .build();
        return memberRepository.save(member);
    }

    private Category createCategory(String name) {
        Category category = Category.builder()
                .name(name)
                .active(true)
                .build();
        return categoryRepository.save(category);
    }

    private FoodTruck createFoodTruck(Member member, Category category, String name, String phoneNumber,
                                      String content, String originInfo, Integer prepareTime,
                                      Integer waitLimits, Boolean selected) {
        FoodTruck foodTruck = FoodTruck.builder()
                .vendor(member)
                .category(category)
                .name(name)
                .phoneNumber(phoneNumber)
                .content(content)
                .originInfo(originInfo)
                .prepareTime(prepareTime)
                .waitLimits(waitLimits)
                .selected(selected)
                .active(true)
                .build();
        return foodTruckRepository.save(foodTruck);
    }

    private void createSchedules(FoodTruck foodTruck) {
        Schedule schedule1 = createSchedule(foodTruck,
                BigDecimal.valueOf(35.204008),
                BigDecimal.valueOf(126.807271),
                LocalDateTime.now().minusHours(1),
                LocalDateTime.now().plusHours(5),
                LocalDateTime.now().getDayOfWeek().name(),
                foodTruck.getId() + " schedule1 address"
        );

        Schedule schedule2 = createSchedule(foodTruck,
                BigDecimal.valueOf(35.204349),
                BigDecimal.valueOf(126.807805),
                LocalDateTime.now().plusDays(1).plusHours(3),
                LocalDateTime.now().plusDays(1).plusHours(5),
                LocalDateTime.now().plusDays(1).getDayOfWeek().name(), foodTruck.getId() + "schedule2 address"
        );

        Schedule schedule3 = createSchedule(foodTruck,
                BigDecimal.valueOf(35.204349),
                BigDecimal.valueOf(126.807805),
                LocalDateTime.now().plusHours(5),
                LocalDateTime.now().plusDays(2).plusHours(7),
                LocalDateTime.now().plusDays(2).getDayOfWeek().name(), foodTruck.getId() + "schedule3 address"
        );
        List<Schedule> schedules = List.of(schedule1, schedule2, schedule3);
        scheduleRepository.saveAll(schedules);
    }

    private Schedule createSchedule(FoodTruck foodTruck, BigDecimal latitude, BigDecimal longitude, LocalDateTime startTime, LocalDateTime endTime, String dayOfWeek, String address) {
        return Schedule.builder()
                .address(address)
                .latitude(latitude)
                .longitude(longitude)
                .dayOfWeek(dayOfWeek)
                .startTime(LocalTime.from(startTime))
                .endTime(LocalTime.from(endTime))
                .active(true)
                .foodTruck(foodTruck)
                .build();
    }
}