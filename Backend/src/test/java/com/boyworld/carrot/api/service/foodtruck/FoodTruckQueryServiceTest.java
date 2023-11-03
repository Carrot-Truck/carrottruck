package com.boyworld.carrot.api.service.foodtruck;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.controller.foodtruck.response.FoodTruckMarkerResponse;
import com.boyworld.carrot.api.controller.foodtruck.response.FoodTruckOverview;
import com.boyworld.carrot.api.controller.foodtruck.response.FoodTruckResponse;
import com.boyworld.carrot.api.service.foodtruck.dto.FoodTruckMarkerItem;
import com.boyworld.carrot.api.service.member.error.InvalidAccessException;
import com.boyworld.carrot.domain.foodtruck.Category;
import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import com.boyworld.carrot.domain.foodtruck.Schedule;
import com.boyworld.carrot.domain.foodtruck.repository.command.CategoryRepository;
import com.boyworld.carrot.domain.foodtruck.repository.command.FoodTruckRepository;
import com.boyworld.carrot.domain.foodtruck.repository.command.ScheduleRepository;
import com.boyworld.carrot.domain.foodtruck.repository.dto.SearchCondition;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class FoodTruckQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private FoodTruckQueryService foodTruckQueryService;

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
    private ScheduleRepository scheduleRepository;

    @DisplayName("사업자는 보유 푸드트럭 목록을 조회할 수 있다.")
    @Test
    void getFoodTruckOverviewsAsVendor() {
        // given
        Member member = createMember(Role.VENDOR);
        Category category = createCategory();
        FoodTruck foodTruck1 = createFoodTruck(member, category, "동현 된장삼겹", "010-1234-5678",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                40,
                10,
                true);
        FoodTruck foodTruck2 = createFoodTruck(member, category, "팔천순대", "010-1234-5678",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                "팔천순대 푸드트럭",
                20,
                10,
                false);

        // when
        FoodTruckResponse<List<FoodTruckOverview>> response =
                foodTruckQueryService.getFoodTruckOverviews(null, "ssafy@ssafy.com");

        // then
        assertThat(response).isNotNull();
        assertThat(response.getHasNext()).isFalse();
        assertThat(response.getItems()).isNotEmpty();
        assertThat(response.getItems()).hasSize(2);
    }

    @DisplayName("보유 푸드트럭이 없을 경우 빈 리스트가 반환된다.")
    @Test
    void getEmptyFoodTruckOverviewsAsVendor() {
        // given
        Member member = createMember(Role.VENDOR);
        Category category = createCategory();

        // when
        FoodTruckResponse<List<FoodTruckOverview>> response =
                foodTruckQueryService.getFoodTruckOverviews(null, "ssafy@ssafy.com");

        // then
        assertThat(response).isNotNull();
        assertThat(response.getHasNext()).isFalse();
        assertThat(response.getItems()).isEmpty();
    }

    @DisplayName("일반 사용자가 보유 푸드트럭 목록 조회를 요청하면 예외가 발생한다.")
    @Test
    void getFoodTruckOverviewsAsClient() {
        // given
        Member member = createMember(Role.CLIENT);
        Category category = createCategory();

        // when // then
        assertThatThrownBy(() -> foodTruckQueryService.getFoodTruckOverviews(null, "ssafy@ssafy.com"))
                .isInstanceOf(InvalidAccessException.class)
                .hasMessage("잘못된 접근입니다.");
    }

    @DisplayName("사용자는 검색 조건 없이 현재 위치 기준 반경 1Km 이내의 푸드트럭을 조회할 수 있다.")
    @Test
    void getAllFoodTruckMarkers() {
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

        Sale sale1 = createSale(foodTruck1, LocalDateTime.now().minusHours(1), LocalDateTime.now());
        Sale sale2 = createSale(foodTruck2, LocalDateTime.now().minusHours(3), LocalDateTime.now().minusHours(1));

        // when
        FoodTruckMarkerResponse response = foodTruckQueryService.getFoodTruckMarkers(SearchCondition.of(null, "",
                BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253)), true);
        log.debug("response={}", response);

        // then
        assertThat(response.getMarkerCount()).isEqualTo(6);
        assertThat(response.getMarkerItems()).hasSize(6);
    }

    @DisplayName("근처에 푸드트럭이 없으면 빈 리스트가 반환된다.")
    @Test
    void getEmptyAllFoodTruckMarkers() {
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

        // when
        FoodTruckMarkerResponse response = foodTruckQueryService.getFoodTruckMarkers(
                SearchCondition.of(null, "",
                        BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253)), true);
        log.debug("response={}", response);

        // then
        assertThat(response.getMarkerCount()).isZero();
        assertThat(response.getMarkerItems()).isEmpty();
    }

    @DisplayName("사용자는 카테고리 식별키로 현재 위치 기준 반경 1Km 이내의 푸드트럭을 조회할 수 있다.")
    @Test
    void getAllFoodTruckMarkersWithCategoryId() {
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

        Sale sale1 = createSale(foodTruck1, LocalDateTime.now().minusHours(1), LocalDateTime.now());
        Sale sale2 = createSale(foodTruck2, LocalDateTime.now().minusHours(3), LocalDateTime.now().minusHours(1));

        // when
        FoodTruckMarkerResponse response = foodTruckQueryService.getFoodTruckMarkers(
                SearchCondition.of(category.getId(), "",
                        BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253)), true);
        log.debug("response={}", response);

        // then
        assertThat(response.getMarkerCount()).isEqualTo(6);
        assertThat(response.getMarkerItems()).hasSize(6);
    }

    @DisplayName("사용자는 키워드에 해당하는 현재 위치 기준 반경 1Km 이내의 영업중인 푸드트럭을 조회할 수 있다.")
    @Test
    void getAllFoodTruckMarkersWithKeyword() {
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
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                40,
                10,
                true);

        createSchedules(foodTruck1);
        createSchedules(foodTruck2);

        Sale sale1 = createSale(foodTruck1, LocalDateTime.now().minusHours(1), null);
        Sale sale2 = createSale(foodTruck2, LocalDateTime.now().minusHours(3), null);

        // when
        FoodTruckMarkerResponse response = foodTruckQueryService.getFoodTruckMarkers(
                SearchCondition.of(null, "된장",
                        BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253)), true);
        log.debug("response={}", response);

        // then
        assertThat(response.getMarkerCount()).isEqualTo(3);
        assertThat(response.getMarkerItems()).hasSize(3);
    }

    @DisplayName("사용자는 검색 조건 없이 현재 위치 기준 반경 1Km 이내의 영업중인 푸드트럭을 조회할 수 있다.")
    @Test
    void getFoodTruckMarkersWithoutCondition() {
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

        Sale sale1 = createSale(foodTruck1, LocalDateTime.now().minusHours(1), null);
        Sale sale2 = createSale(foodTruck2, LocalDateTime.now().minusHours(3), LocalDateTime.now().minusHours(1));

        // when
        FoodTruckMarkerResponse response = foodTruckQueryService.getFoodTruckMarkers(
                SearchCondition.of(null, "",
                        BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253)), false);
        log.debug("response={}", response);

        // then
        assertThat(response.getMarkerCount()).isEqualTo(1);
        assertThat(response.getMarkerItems()).hasSize(1);
    }

    @DisplayName("근처에 영업 중인 푸드트럭이 없으면 빈 리스트가 반환된다.")
    @Test
    void getEmptyFoodTruckMarkers() {
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

        Sale sale1 = createSale(foodTruck1, LocalDateTime.now().minusHours(1), LocalDateTime.now());
        Sale sale2 = createSale(foodTruck2, LocalDateTime.now().minusHours(3), LocalDateTime.now().minusHours(1));

        // when
        FoodTruckMarkerResponse response = foodTruckQueryService.getFoodTruckMarkers(
                SearchCondition.of(null, "",
                        BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253)), false);
        log.debug("response={}", response);

        // then
        assertThat(response.getMarkerCount()).isZero();
        assertThat(response.getMarkerItems()).isEmpty();
    }

    @DisplayName("사용자는 카테고리 식별키에 해당하는 현재 위치 기준 반경 1Km 이내의 영업중인 푸드트럭을 조회할 수 있다.")
    @Test
    void getFoodTruckMarkersWithCategoryId() {
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

        Sale sale1 = createSale(foodTruck1, LocalDateTime.now().minusHours(1), null);
        Sale sale2 = createSale(foodTruck2, LocalDateTime.now().minusHours(3), null);

        // when
        FoodTruckMarkerResponse response = foodTruckQueryService.getFoodTruckMarkers(
                SearchCondition.of(category.getId(), "",
                        BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253)), false);
        log.debug("response={}", response);

        // then
        assertThat(response.getMarkerCount()).isEqualTo(2);
        assertThat(response.getMarkerItems()).hasSize(2);
    }

    @DisplayName("사용자는 키워드에 해당하는 현재 위치 기준 반경 1Km 이내의 영업중인 푸드트럭을 조회할 수 있다.")
    @Test
    void getFoodTruckMarkersWithKeyword() {
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

        Sale sale1 = createSale(foodTruck1, LocalDateTime.now().minusHours(1), null);
        Sale sale2 = createSale(foodTruck2, LocalDateTime.now().minusHours(3), null);

        // when
        FoodTruckMarkerResponse response = foodTruckQueryService.getFoodTruckMarkers(
                SearchCondition.of(null, "된장",
                        BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253)), false);
        log.debug("response={}", response);

        // then
        assertThat(response.getMarkerCount()).isEqualTo(2);
        assertThat(response.getMarkerItems()).hasSize(2);
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

    private Sale createSale(FoodTruck foodTruck, LocalDateTime startTime, LocalDateTime endTime) {
        Sale sale = Sale.builder()
                .foodTruck(foodTruck)
                .latitude(BigDecimal.valueOf(35.204008))
                .longitude(BigDecimal.valueOf(126.807271))
                .orderNumber(0)
                .totalAmount(0)
                .startTime(startTime)
                .endTime(endTime)
                .active(true)
                .build();
        return saleRepository.save(sale);
    }
}