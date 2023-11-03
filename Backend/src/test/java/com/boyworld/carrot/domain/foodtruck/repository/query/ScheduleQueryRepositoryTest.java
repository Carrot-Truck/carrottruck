package com.boyworld.carrot.domain.foodtruck.repository.query;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.controller.foodtruck.response.FoodTruckItem;
import com.boyworld.carrot.api.service.foodtruck.dto.FoodTruckMarkerItem;
import com.boyworld.carrot.domain.foodtruck.Category;
import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import com.boyworld.carrot.domain.foodtruck.FoodTruckLike;
import com.boyworld.carrot.domain.foodtruck.Schedule;
import com.boyworld.carrot.domain.foodtruck.repository.command.CategoryRepository;
import com.boyworld.carrot.domain.foodtruck.repository.command.FoodTruckLikeRepository;
import com.boyworld.carrot.domain.foodtruck.repository.command.FoodTruckRepository;
import com.boyworld.carrot.domain.foodtruck.repository.command.ScheduleRepository;
import com.boyworld.carrot.domain.foodtruck.repository.dto.SearchCondition;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
import com.boyworld.carrot.domain.review.Review;
import com.boyworld.carrot.domain.sale.Sale;
import com.boyworld.carrot.domain.sale.repository.command.SaleRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 푸드트럭 조회 레포지토리 테스트
 *
 * @author 최영환
 */
@Slf4j
class ScheduleQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private ScheduleQueryRepository scheduleQueryRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FoodTruckRepository foodTruckRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private FoodTruckLikeRepository foodTruckLikeRepository;

    @DisplayName("사용자는 검색 조건 없이 현재 위치 기준 반경 1Km 이내의 푸드트럭 위치 정보를 조회할 수 있다.")
    @Test
    void getPositionsByCondition() {
        // given
        Member member = createMember(Role.CLIENT);
        Category category = createCategory();
        FoodTruck foodTruck1 = createFoodTruck(member, category, "동현 된장삼겹", "010-1234-5678",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                40,
                10,
                true);
        FoodTruck foodTruck2 = createFoodTruck(member, category, "동현 된장삼겹", "010-1234-5678",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                40,
                10,
                true);
        createSchedules(foodTruck1);
        createSchedules(foodTruck2);

        createSale(foodTruck1, null);
        createSale(foodTruck2, null);

        // when
        List<FoodTruckMarkerItem> items = scheduleQueryRepository.getPositionsByCondition(
                SearchCondition.of(null, "",
                        BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253)));
        log.debug("items={}", items);

        // then
        assertThat(items).hasSize(6);
    }

    @DisplayName("영업 정보가 없어도 검색이 가능하다.")
    @Test
    void getPositionsByConditionWithOutSale() {
        // given
        Member member = createMember(Role.CLIENT);
        Category category = createCategory();
        FoodTruck foodTruck1 = createFoodTruck(member, category, "동현 된장삼겹", "010-1234-5678",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                40,
                10,
                true);
        FoodTruck foodTruck2 = createFoodTruck(member, category, "동현 된장삼겹", "010-1234-5678",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                40,
                10,
                true);
        createSchedules(foodTruck1);
        createSchedules(foodTruck2);

        // when
        List<FoodTruckMarkerItem> items = scheduleQueryRepository.getPositionsByCondition(
                SearchCondition.of(null, "",
                        BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253)));
        log.debug("items={}", items);

        // then
        assertThat(items).hasSize(6);
    }

    @DisplayName("근처에 푸드트럭이 없으면 빈 리스트가 반환된다.")
    @Test
    void getEmptyPositionsByCondition() {
        // given
        Member member = createMember(Role.CLIENT);
        Category category = createCategory();
        FoodTruck foodTruck2 = createFoodTruck(member, category, "동현 된장삼겹", "010-1234-5678",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                40,
                10,
                true);

        // when
        List<FoodTruckMarkerItem> items = scheduleQueryRepository.getPositionsByCondition(
                SearchCondition.of(null, "",
                        BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253)));
        log.debug("items={}", items);

        // then
        assertThat(items).isEmpty();
    }

    @DisplayName("사용자는 카테고리 식별키로 현재 위치 기준 반경 1Km 이내의 푸드트럭 위치 정보를 조회할 수 있다.")
    @Test
    void getEmptyPositionsByConditionWithCategoryId() {
        // given
        Member member = createMember(Role.CLIENT);
        Category category = createCategory();
        FoodTruck foodTruck1 = createFoodTruck(member, category, "동현 된장삼겹", "010-1234-5678",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                40,
                10,
                true);
        FoodTruck foodTruck2 = createFoodTruck(member, category, "동현 삼겹", "010-1234-5678",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                "삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                40,
                10,
                true);
        createSchedules(foodTruck1);
        createSchedules(foodTruck2);

        createSale(foodTruck1, null);
        createSale(foodTruck2, LocalDateTime.of(2023, 11, 2, 21, 30));

        // when
        List<FoodTruckMarkerItem> items = scheduleQueryRepository.getPositionsByCondition(
                SearchCondition.of(category.getId(), "",
                        BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253)));
        log.debug("items={}", items);

        // then
        assertThat(items).hasSize(6);
    }

    @DisplayName("사용자는 키워드로 현재 위치 기준 반경 1Km 이내의 푸드트럭 위치 정보를 조회할 수 있다.")
    @Test
    void getEmptyPositionsByConditionWithKeyword() {
        // given
        Member member = createMember(Role.CLIENT);
        Category category = createCategory();
        FoodTruck foodTruck1 = createFoodTruck(member, category, "동현 된장삼겹", "010-1234-5678",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                40,
                10,
                true);
        FoodTruck foodTruck2 = createFoodTruck(member, category, "동현 삼겹", "010-1234-5678",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                "삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                40,
                10,
                true);
        createSchedules(foodTruck1);
        createSchedules(foodTruck2);

        createSale(foodTruck1, null);
        createSale(foodTruck2, LocalDateTime.of(2023, 11, 2, 21, 30));

        // when
        List<FoodTruckMarkerItem> items = scheduleQueryRepository.getPositionsByCondition(
                SearchCondition.of(null, "된장",
                        BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253)));
        log.debug("items={}", items);

        // then
        assertThat(items).hasSize(3);
    }

    @DisplayName("사용자는 검색 조건 없이 현재 위치 기준 반경 1Km 이내의 푸드트럭을 조회할 수 있다.")
    @Test
    void getFoodTrucksByConditionWithoutReviewAndLike() {
        // given
        Member member = createMember(Role.CLIENT);
        Category category = createCategory();
        FoodTruck foodTruck1 = createFoodTruck(member, category, "동현 된장삼겹", "010-1234-5678",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                40,
                10,
                true);
        FoodTruck foodTruck2 = createFoodTruck(member, category, "동현 된장삼겹", "010-1234-5678",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                40,
                10,
                true);
        createSchedules(foodTruck1);
        createSchedules(foodTruck2);

        createSale(foodTruck1, null);
        createSale(foodTruck2, null);

        // when
        List<FoodTruckItem> items = scheduleQueryRepository.getFoodTrucksByCondition(
                SearchCondition.of(null, "",
                        BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253),
                        ""));
        for (FoodTruckItem item : items) {
            log.debug("item={}", item);
        }

        // then
        assertThat(items).hasSize(6);
    }

    @DisplayName("사용자는 검색 조건 없이 현재 위치 기준 반경 1Km 이내의 푸드트럭을 조회할 수 있다.")
    @Test
    void getFoodTrucksByConditionWithLike() {
        // given
        Member member = createMember(Role.CLIENT);
        Category category = createCategory();
        FoodTruck foodTruck1 = createFoodTruck(member, category, "동현 된장삼겹", "010-1234-5678",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                40,
                10,
                true);
        FoodTruck foodTruck2 = createFoodTruck(member, category, "동현 된장삼겹", "010-1234-5678",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                40,
                10,
                true);
        createSchedules(foodTruck1);
        createSchedules(foodTruck2);

        createSale(foodTruck1, null);
        createSale(foodTruck2, null);

        FoodTruckLike foodTruckLike = FoodTruckLike.builder()
                .foodTruck(foodTruck1)
                .member(member)
                .active(true)
                .build();
        foodTruckLikeRepository.save(foodTruckLike);

        // when
        List<FoodTruckItem> items = scheduleQueryRepository.getFoodTrucksByCondition(
                SearchCondition.of(null, "",
                        BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253),
                        ""));
        for (FoodTruckItem item : items) {
            log.debug("item={}", item);
        }

        // then
        assertThat(items).hasSize(6);
    }

    @DisplayName("사용자는 검색 조건 없이 현재 위치 기준 반경 1Km 이내의 푸드트럭을 조회할 수 있다.")
    @Test
    void getFoodTrucksByConditionWithReview() {
        // given
        Member member = createMember(Role.CLIENT);
        Category category = createCategory();
        FoodTruck foodTruck1 = createFoodTruck(member, category, "동현 된장삼겹", "010-1234-5678",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                40,
                10,
                true);
        FoodTruck foodTruck2 = createFoodTruck(member, category, "동현 된장삼겹", "010-1234-5678",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                40,
                10,
                true);
        createSchedules(foodTruck1);
        createSchedules(foodTruck2);

        createSale(foodTruck1, null);
        createSale(foodTruck2, null);

        // TODO: 2023-11-03 Review 생성

        // when
        List<FoodTruckItem> items = scheduleQueryRepository.getFoodTrucksByCondition(
                SearchCondition.of(null, "",
                        BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253),
                        ""));
        for (FoodTruckItem item : items) {
            log.debug("item={}", item);
        }

        // then
        assertThat(items).hasSize(6);
    }

    private void createSale(FoodTruck foodTruck, LocalDateTime endTime) {
        Sale sale = Sale.builder()
                .foodTruck(foodTruck)
                .latitude(BigDecimal.valueOf(35.204008))
                .longitude(BigDecimal.valueOf(126.807271))
                .orderNumber(0)
                .totalAmount(0)
                .startTime(LocalDateTime.now().minusHours(1))
                .endTime(endTime)
                .active(true)
                .build();
        saleRepository.save(sale);
    }

    private Member createMember(Role role) {
        Member member = Member.builder()
                .email("ssafy@ssafy.com")
                .nickname("매미킴")
                .encryptedPwd(passwordEncoder.encode("ssafy1234"))
                .name("김동현")
                .phoneNumber("010-1234-5678")
                .role(role)
                .active(true)
                .build();
        return memberRepository.save(member);
    }

    private Category createCategory() {
        Category category = Category.builder()
                .name("고기/구이")
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
                DayOfWeek.FRIDAY.name()
        );

        Schedule schedule2 = createSchedule(foodTruck,
                BigDecimal.valueOf(35.204349),
                BigDecimal.valueOf(126.807805),
                LocalDateTime.now().plusHours(3),
                LocalDateTime.now().plusHours(5),
                DayOfWeek.SATURDAY.name()
        );

        Schedule schedule3 = createSchedule(foodTruck,
                BigDecimal.valueOf(35.204349),
                BigDecimal.valueOf(126.807805),
                LocalDateTime.now().plusHours(5),
                LocalDateTime.now().plusHours(7),
                DayOfWeek.SATURDAY.name()
        );
        List<Schedule> schedules = List.of(schedule1, schedule2, schedule3);
        scheduleRepository.saveAll(schedules);
    }

    private Schedule createSchedule(FoodTruck foodTruck, BigDecimal latitude, BigDecimal longitude, LocalDateTime startTime, LocalDateTime endTime, String dayOfWeek) {
        return Schedule.builder()
                .address("주소정보")
                .latitude(latitude)
                .longitude(longitude)
                .dayOfWeek(dayOfWeek)
                .startTime(startTime)
                .endTime(endTime)
                .active(true)
                .foodTruck(foodTruck)
                .build();
    }
}