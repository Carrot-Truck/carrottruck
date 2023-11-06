package com.boyworld.carrot.domain.foodtruck.repository.query;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.controller.foodtruck.response.FoodTruckOverview;
import com.boyworld.carrot.api.service.foodtruck.dto.FoodTruckDetailDto;
import com.boyworld.carrot.domain.foodtruck.*;
import com.boyworld.carrot.domain.foodtruck.repository.command.*;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.VendorInfo;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
import com.boyworld.carrot.domain.member.repository.command.VendorInfoRepository;
import com.boyworld.carrot.domain.order.Order;
import com.boyworld.carrot.domain.order.Status;
import com.boyworld.carrot.domain.order.repository.command.OrderRepository;
import com.boyworld.carrot.domain.review.Review;
import com.boyworld.carrot.domain.review.repository.ReviewRepository;
import com.boyworld.carrot.domain.sale.Sale;
import com.boyworld.carrot.domain.sale.repository.command.SaleRepository;
import com.boyworld.carrot.file.UploadFile;
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

@Slf4j
class FoodTruckQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private FoodTruckQueryRepository foodTruckQueryRepository;

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

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private FoodTruckImageRepository foodTruckImageRepository;

    @Autowired
    private VendorInfoRepository vendorInfoRepository;

    @DisplayName("사용자 이메일로 선택된 푸드트럭의 개수를 조회한다.")
    @Test
    void getSelectedCountByEmail() {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@ssafy.com");
        Member vendor2 = createMember(Role.VENDOR, "hi@ssafy.com");

        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        createFoodTrucks(vendor1, vendor2, category1, category2);

        // when
        Long result = foodTruckQueryRepository.getSelectedCountByEmail(vendor1.getEmail());

        // when
        assertThat(result).isNotZero();
    }

    @DisplayName("선택된 푸드트럭 개수 조회 시 선택된 것이 없으면 0이 반환된다.")
    @Test
    void getSelectedCountByEmailWithoutSelected() {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@ssafy.com");
        Member vendor2 = createMember(Role.VENDOR, "hi@ssafy.com");

        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        createFoodTrucks(vendor1, vendor2, category1, category2);

        // when
        Long result = foodTruckQueryRepository.getSelectedCountByEmail(vendor2.getEmail());
        log.debug("result={}", result);

        // when
        assertThat(result).isZero();
    }

    @DisplayName("사업자는 이메일로 보유 푸드트럭을 조회할 수 있다.")
    @Test
    void getFoodTruckOverviewsByEmail() {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@ssafy.com");
        Member vendor2 = createMember(Role.VENDOR, "hi@ssafy.com");

        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        createFoodTrucks(vendor1, vendor2, category1, category2);

        // when
        List<FoodTruckOverview> overviews =
                foodTruckQueryRepository.getFoodTruckOverviewsByEmail(null, vendor1.getEmail());

        // then
        assertThat(overviews).isNotEmpty();
        assertThat(overviews).hasSize(1);
    }

    @DisplayName("보유한 푸드트럭이 없으면 빈 리스트를 반환한다.")
    @Test
    void getEmptyFoodTruckOverviewsByEmail() {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@ssafy.com");
        Member vendor2 = createMember(Role.VENDOR, "hi@ssafy.com");
        Member vendor3 = createMember(Role.VENDOR, "hello@ssafy.com");

        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        createFoodTrucks(vendor1, vendor2, category1, category2);

        // when
        List<FoodTruckOverview> overviews =
                foodTruckQueryRepository.getFoodTruckOverviewsByEmail(null, vendor3.getEmail());

        // then
        assertThat(overviews).isEmpty();
    }

    @DisplayName("사용자는 푸드트럭 식별키와 현재 자신의 위도 경도, 이메일로 푸드트럭을 조회할 수 있다.")
    @Test
    void getFoodTruckById() {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@ssafy.com");
        Member vendor2 = createMember(Role.VENDOR, "hi@ssafy.com");
        Member client1 = createMember(Role.CLIENT, "ssafy123@ssafy.com");
        Member client2 = createMember(Role.CLIENT, "hello123@ssafy.com");

        createVendorInfo(vendor1);

        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        List<FoodTruck> foodTrucks = createFoodTrucks(vendor1, vendor2, category1, category2);
        FoodTruck foodTruck1 = foodTrucks.get(0);
        FoodTruck foodTruck2 = foodTrucks.get(1);

        createFoodTruckImage(foodTruck1);

        createSchedules(foodTruck1);
        createSchedules(foodTruck2);

        Sale sale1 = createSale(foodTruck1, null);
        Sale sale2 = createSale(foodTruck2, null);

        createFoodTruckLikes(client1, client2, foodTruck1, foodTruck2);

        createOrdersAndReviews(client1, client2, foodTruck1, foodTruck2, sale1, sale2);

        // when
        FoodTruckDetailDto foodTruck = foodTruckQueryRepository.getFoodTruckById(foodTruck1.getId(), client1.getEmail(),
                BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253));
        log.debug("foodTruck={}", foodTruck);

        // then
        assertThat(foodTruck).isNotNull();
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

    private void createVendorInfo(Member member) {
        VendorInfo vendorInfo = VendorInfo.builder()
                .vendorName("김동현")
                .phoneNumber("010-5678-1234")
                .businessNumber("123-456-789999")
                .tradeName("된삼")
                .member(member)
                .active(true)
                .build();
        vendorInfoRepository.save(vendorInfo);
    }

    private Category createCategory(String name) {
        Category category = Category.builder()
                .name(name)
                .active(true)
                .build();
        return categoryRepository.save(category);
    }

    private List<FoodTruck> createFoodTrucks(Member vendor1, Member vendor2, Category category1, Category category2) {
        FoodTruck foodTruck1 = createFoodTruck(vendor1, category1, "동현 된장삼겹", "010-1234-5678",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                40,
                10,
                true);
        FoodTruck foodTruck2 = createFoodTruck(vendor2, category2, "팔천순대", "010-1234-5678",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                "삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                30,
                20,
                false);
        return List.of(foodTruck1, foodTruck2);
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

    private Sale createSale(FoodTruck foodTruck, LocalDateTime endTime) {
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
        return saleRepository.save(sale);
    }

    private void createFoodTruckLikes(Member client1, Member client2, FoodTruck foodTruck1, FoodTruck foodTruck2) {
        createFoodTruckLike(client1, foodTruck1);
        createFoodTruckLike(client2, foodTruck1);

        createFoodTruckLike(client2, foodTruck2);
    }

    private void createOrdersAndReviews(Member client1, Member client2, FoodTruck foodTruck1, FoodTruck foodTruck2, Sale sale1, Sale sale2) {
        Order order1 = createOrder(client1, sale1, 30000);

        Order order2 = createOrder(client1, sale2, 50000);
        Order order3 = createOrder(client2, sale2, 40000);

        createReview(client1, foodTruck1, order1, "별로에요", 3);

        createReview(client2, foodTruck2, order3, "아 진짜 별로", 1);
        createReview(client1, foodTruck2, order2, "마시써요", 4);
    }

    private Order createOrder(Member client, Sale sale, int totalPrice) {
        Order order = Order.builder()
                .status(Status.COMPLETE)
                .member(client)
                .sale(sale)
                .totalPrice(totalPrice)
                .active(true)
                .build();
        return orderRepository.save(order);
    }

    private Review createReview(Member client, FoodTruck foodTruck, Order order, String content, int grade) {
        Review review = Review.builder()
                .member(client)
                .foodTruck(foodTruck)
                .order(order)
                .content(content)
                .grade(grade)
                .active(true)
                .build();
        return reviewRepository.save(review);
    }

    private void createFoodTruckLike(Member member, FoodTruck foodTruck) {
        FoodTruckLike foodTruckLike = FoodTruckLike.builder()
                .foodTruck(foodTruck)
                .member(member)
                .active(true)
                .build();
        foodTruckLikeRepository.save(foodTruckLike);
    }

    private void createFoodTruckImage(FoodTruck foodTruck) {
        FoodTruckImage image = FoodTruckImage.builder()
                .foodTruck(foodTruck)
                .uploadFile(UploadFile.builder().uploadFileName("testUploadName").storeFileName("test1234").build())
                .active(true)
                .build();
        foodTruckImageRepository.save(image);
    }
}