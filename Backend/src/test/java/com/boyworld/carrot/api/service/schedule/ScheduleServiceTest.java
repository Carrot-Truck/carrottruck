package com.boyworld.carrot.api.service.schedule;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.controller.schedule.response.ScheduleDetailResponse;
import com.boyworld.carrot.api.service.member.error.InValidAccessException;
import com.boyworld.carrot.api.service.schedule.dto.CreateScheduleDto;
import com.boyworld.carrot.api.service.schedule.dto.EditScheduleDto;
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
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 스케줄 CUD 서비스 테스트
 *
 * @author 최영환
 */
@Slf4j
class ScheduleServiceTest extends IntegrationTestSupport {

    @Autowired
    private ScheduleService scheduleService;

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

    @DisplayName("사업자는 본인 소유의 푸드트럭 스케줄을 추가할 수 있다.")
    @Test
    void createScheduleAsOwner() {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@ssafy.com");

        Category category = createCategory("고기/구이");

        FoodTruck foodTruck = createFoodTruck(vendor1, category, "동현 된장삼겹", "010-1234-5678",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                40,
                10,
                true);

        CreateScheduleDto dto = CreateScheduleDto.builder()
                .foodTruckId(foodTruck.getId())
                .latitude(new BigDecimal("37.5665"))
                .longitude(new BigDecimal("126.9780"))
                .dayOfWeek(DayOfWeek.MONDAY.name())
                .startTime("17:00")
                .endTime("20:00")
                .build();

        // when
        ScheduleDetailResponse response = scheduleService.createSchedule(dto, vendor1.getEmail());
        log.debug("response={}", response);

        // then
        assertThat(response)
                .extracting("dayOfWeek", "latitude", "longitude", "startTime", "endTime")
                .containsExactly(dto.getDayOfWeek(), dto.getLatitude().toString(), dto.getLongitude().toString(),
                        dto.getStartTime(), dto.getEndTime());
    }

    @DisplayName("사업자는 본인 소유의 푸드트럭 스케줄을 추가할 수 있다.")
    @Test
    void createScheduleAsInValidMember() {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@ssafy.com");
        Member vendor2 = createMember(Role.VENDOR, "hi@ssafy.com");
        Member client1 = createMember(Role.CLIENT, "ssafy123@ssafy.com");

        Category category = createCategory("고기/구이");

        FoodTruck foodTruck = createFoodTruck(vendor1, category, "동현 된장삼겹", "010-1234-5678",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                40,
                10,
                true);

        CreateScheduleDto dto = CreateScheduleDto.builder()
                .foodTruckId(foodTruck.getId())
                .latitude(new BigDecimal("37.5665"))
                .longitude(new BigDecimal("126.9780"))
                .dayOfWeek(DayOfWeek.MONDAY.name())
                .startTime("17:00")
                .endTime("20:00")
                .build();

        // when // then
        assertThatThrownBy(() -> scheduleService.createSchedule(dto, vendor2.getEmail()))
                .isInstanceOf(InValidAccessException.class)
                .hasMessage("잘못된 접근입니다.");

        assertThatThrownBy(() -> scheduleService.createSchedule(dto, client1.getEmail()))
                .isInstanceOf(InValidAccessException.class)
                .hasMessage("잘못된 접근입니다.");
    }

    @DisplayName("사업자는 본인 소유의 푸드트럭 스케줄을 수정할 수 있다.")
    @Test
    void editScheduleAsOwner() {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@ssafy.com");

        Category category = createCategory("고기/구이");

        FoodTruck foodTruck = createFoodTruck(vendor1, category, "동현 된장삼겹", "010-1234-5678",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                40,
                10,
                true);

        Schedule schedule = createSchedule(foodTruck,
                BigDecimal.valueOf(37.5665),
                BigDecimal.valueOf(126.978),
                LocalDateTime.now().minusHours(1),
                LocalDateTime.now().plusHours(5),
                LocalDateTime.now().getDayOfWeek().name(),
                foodTruck.getId() + " schedule1 address");

        EditScheduleDto dto = EditScheduleDto.builder()
                .foodTruckId(foodTruck.getId())
                .latitude(new BigDecimal("37.5665"))
                .longitude(new BigDecimal("126.978"))
                .dayOfWeek(DayOfWeek.MONDAY.name())
                .startTime("17:00")
                .endTime("20:00")
                .build();

        // when
        ScheduleDetailResponse response = scheduleService.editSchedule(schedule.getId(), dto, vendor1.getEmail());
        log.debug("response={}", response);

        // then
        assertThat(response)
                .extracting("dayOfWeek", "latitude", "longitude", "startTime", "endTime")
                .containsExactly(dto.getDayOfWeek(), dto.getLatitude().toString(), dto.getLongitude().toEngineeringString(),
                        dto.getStartTime(), dto.getEndTime());
    }

    @DisplayName("푸드트럭 식별키가 존재하지 않는다면 예외가 발생한다.")
    @Test
    void editEmptyScheduleAsOwner() {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@ssafy.com");

        Category category = createCategory("고기/구이");

        FoodTruck foodTruck = createFoodTruck(vendor1, category, "동현 된장삼겹", "010-1234-5678",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                40,
                10,
                true);

        Schedule schedule = createSchedule(foodTruck,
                BigDecimal.valueOf(37.5665),
                BigDecimal.valueOf(126.978),
                LocalDateTime.now().minusHours(1),
                LocalDateTime.now().plusHours(5),
                LocalDateTime.now().getDayOfWeek().name(),
                foodTruck.getId() + " schedule1 address");

        EditScheduleDto dto = EditScheduleDto.builder()
                .foodTruckId(foodTruck.getId())
                .latitude(new BigDecimal("37.5665"))
                .longitude(new BigDecimal("126.978"))
                .dayOfWeek(DayOfWeek.MONDAY.name())
                .startTime("17:00")
                .endTime("20:00")
                .build();

        // when // then
        assertThatThrownBy(() -> scheduleService.editSchedule(-1L, dto, vendor1.getEmail()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("존재하지 않는 스케줄입니다.");
    }

    @DisplayName("일반 사용자이거나 소유주가 아닌 경우 예외가 발생한다..")
    @Test
    void editScheduleAsInValidMember() {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@ssafy.com");
        Member vendor2 = createMember(Role.VENDOR, "hi@ssafy.com");
        Member client1 = createMember(Role.CLIENT, "ssafy123@ssafy.com");

        Category category = createCategory("고기/구이");

        FoodTruck foodTruck = createFoodTruck(vendor1, category, "동현 된장삼겹", "010-1234-5678",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                40,
                10,
                true);

        Schedule schedule = createSchedule(foodTruck,
                BigDecimal.valueOf(37.5665),
                BigDecimal.valueOf(126.978),
                LocalDateTime.now().minusHours(1),
                LocalDateTime.now().plusHours(5),
                LocalDateTime.now().getDayOfWeek().name(),
                foodTruck.getId() + " schedule1 address");

        EditScheduleDto dto = EditScheduleDto.builder()
                .foodTruckId(foodTruck.getId())
                .latitude(new BigDecimal("37.5665"))
                .longitude(new BigDecimal("126.978"))
                .dayOfWeek(DayOfWeek.MONDAY.name())
                .startTime("17:00")
                .endTime("20:00")
                .build();

        // when
        ScheduleDetailResponse response = scheduleService.editSchedule(schedule.getId(), dto, vendor1.getEmail());
        log.debug("response={}", response);

        // when // then
        assertThatThrownBy(() -> scheduleService.editSchedule(schedule.getId(), dto, vendor2.getEmail()))
                .isInstanceOf(InValidAccessException.class)
                .hasMessage("잘못된 접근입니다.");

        assertThatThrownBy(() -> scheduleService.editSchedule(schedule.getId(), dto, client1.getEmail()))
                .isInstanceOf(InValidAccessException.class)
                .hasMessage("잘못된 접근입니다.");
    }

    @DisplayName("사업자는 본인 소유의 푸드트럭 스케줄을 삭제할 수 있다.")
    @Test
    void deleteScheduleAsOwner() {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@ssafy.com");

        Category category = createCategory("고기/구이");

        FoodTruck foodTruck = createFoodTruck(vendor1, category, "동현 된장삼겹", "010-1234-5678",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                40,
                10,
                true);

        Schedule schedule = createSchedule(foodTruck,
                BigDecimal.valueOf(37.5665),
                BigDecimal.valueOf(126.978),
                LocalDateTime.now().minusHours(1),
                LocalDateTime.now().plusHours(5),
                LocalDateTime.now().getDayOfWeek().name(),
                foodTruck.getId() + " schedule1 address");

        // when
        Long deletedId = scheduleService.deleteSchedule(schedule.getId(), vendor1.getEmail());
        log.debug("deletedId={}", deletedId);

        Optional<Schedule> deleted = scheduleRepository.findById(schedule.getId());

        // then
        assertThat(deleted).isPresent();
        assertThat(deleted.get().getActive()).isFalse();
    }

    @DisplayName("존재하지 않는 스케줄을 삭제하려고하면 예외가 발생한다.")
    @Test
    void deleteEmptyScheduleAsOwner() {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@ssafy.com");

        Category category = createCategory("고기/구이");

        FoodTruck foodTruck = createFoodTruck(vendor1, category, "동현 된장삼겹", "010-1234-5678",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                40,
                10,
                true);

        Schedule schedule = createSchedule(foodTruck,
                BigDecimal.valueOf(37.5665),
                BigDecimal.valueOf(126.978),
                LocalDateTime.now().minusHours(1),
                LocalDateTime.now().plusHours(5),
                LocalDateTime.now().getDayOfWeek().name(),
                foodTruck.getId() + " schedule1 address");

        // when // then
        assertThatThrownBy(() -> scheduleService.deleteSchedule(-1L, vendor1.getEmail()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("존재하지 않는 스케줄입니다.");
    }

    @DisplayName("일반 사용자나 푸드트럭 소유주가 아닐 경우 예외가 발생한다.")
    @Test
    void deleteScheduleAsInValidMember() {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@ssafy.com");
        Member vendor2 = createMember(Role.VENDOR, "hi@ssafy.com");
        Member client1 = createMember(Role.CLIENT, "ssafy123@ssafy.com");

        Category category = createCategory("고기/구이");

        FoodTruck foodTruck = createFoodTruck(vendor1, category, "동현 된장삼겹", "010-1234-5678",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                40,
                10,
                true);

        Schedule schedule = createSchedule(foodTruck,
                BigDecimal.valueOf(37.5665),
                BigDecimal.valueOf(126.978),
                LocalDateTime.now().minusHours(1),
                LocalDateTime.now().plusHours(5),
                LocalDateTime.now().getDayOfWeek().name(),
                foodTruck.getId() + " schedule1 address");

        // when // then
        assertThatThrownBy(() -> scheduleService.deleteSchedule(schedule.getId(), vendor2.getEmail()))
                .isInstanceOf(InValidAccessException.class)
                .hasMessage("잘못된 접근입니다.");

        assertThatThrownBy(() -> scheduleService.deleteSchedule(schedule.getId(), client1.getEmail()))
                .isInstanceOf(InValidAccessException.class)
                .hasMessage("잘못된 접근입니다.");
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

    private Schedule createSchedule(FoodTruck foodTruck, BigDecimal latitude, BigDecimal longitude, LocalDateTime startTime, LocalDateTime endTime, String dayOfWeek, String address) {
        Schedule schedule = Schedule.builder()
                .address(address)
                .latitude(latitude)
                .longitude(longitude)
                .dayOfWeek(dayOfWeek)
                .startTime(LocalTime.from(startTime))
                .endTime(LocalTime.from(endTime))
                .active(true)
                .foodTruck(foodTruck)
                .build();
        return scheduleRepository.save(schedule);
    }
}