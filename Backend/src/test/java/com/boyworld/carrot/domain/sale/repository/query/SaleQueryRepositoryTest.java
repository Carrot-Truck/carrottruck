package com.boyworld.carrot.domain.sale.repository.query;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.controller.foodtruck.response.FoodTruckItem;
import com.boyworld.carrot.api.service.foodtruck.dto.FoodTruckMarkerItem;
import com.boyworld.carrot.domain.foodtruck.Category;
import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import com.boyworld.carrot.domain.foodtruck.FoodTruckLike;
import com.boyworld.carrot.domain.foodtruck.repository.command.CategoryRepository;
import com.boyworld.carrot.domain.foodtruck.repository.command.FoodTruckLikeRepository;
import com.boyworld.carrot.domain.foodtruck.repository.command.FoodTruckRepository;
import com.boyworld.carrot.domain.foodtruck.repository.dto.OrderCondition;
import com.boyworld.carrot.domain.foodtruck.repository.dto.SearchCondition;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
import com.boyworld.carrot.domain.order.Order;
import com.boyworld.carrot.domain.order.Status;
import com.boyworld.carrot.domain.order.repository.command.OrderRepository;
import com.boyworld.carrot.domain.review.Review;
import com.boyworld.carrot.domain.review.repository.ReviewRepository;
import com.boyworld.carrot.domain.sale.Sale;
import com.boyworld.carrot.domain.sale.repository.command.SaleRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class SaleQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private SaleQueryRepository saleQueryRepository;

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

    @DisplayName("사용자는 검색 조건 없이 현재 위치 기준 반경 1Km 이내의 영업중인 푸드트럭 위치 정보를 조회할 수 있다.")
    @Test
    void getPositionsByConditionWithoutCondition() {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@gmail.com");
        Member vendor2 = createMember(Role.VENDOR, "hi@ssafy.com");
        Member client1 = createMember(Role.CLIENT, "ssafy123@ssafy.com");
        Member client2 = createMember(Role.CLIENT, "hello123@ssafy.com");

        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        setUpData(vendor1, vendor2, client1, client2, category1, category2);

        // when
        List<FoodTruckMarkerItem> items = saleQueryRepository.getPositionsByCondition(
                SearchCondition.of(null, "",
                        BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253)));
        for (FoodTruckMarkerItem item : items) {
            log.debug("item={}", item);
        }

        // then
        assertThat(items).isNotEmpty();
    }

    @DisplayName("근처에 영업 중인 푸드트럭이 없으면 빈 리스트가 반환된다.")
    @Test
    void getEmptyPositionsByCondition() {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@gmail.com");
        Member vendor2 = createMember(Role.VENDOR, "hi@ssafy.com");
        Member client1 = createMember(Role.CLIENT, "ssafy123@ssafy.com");
        Member client2 = createMember(Role.CLIENT, "hello123@ssafy.com");

        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        setUpData(vendor1, vendor2, client1, client2, category1, category2);

        // when
        List<FoodTruckMarkerItem> items = saleQueryRepository.getPositionsByCondition(
                SearchCondition.of(null, "",
                        BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(-56.807253)));

        // then
        assertThat(items).isEmpty();
    }

    @DisplayName("사용자는 카테고리 식별키에 해당하는 현재 위치 기준 반경 1Km 이내의 영업중인 푸드트럭 위치 정보를 조회할 수 있다.")
    @Test
    void getPositionsByConditionWithCategoryId() {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@gmail.com");
        Member vendor2 = createMember(Role.VENDOR, "hi@ssafy.com");
        Member client1 = createMember(Role.CLIENT, "ssafy123@ssafy.com");
        Member client2 = createMember(Role.CLIENT, "hello123@ssafy.com");

        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        setUpData(vendor1, vendor2, client1, client2, category1, category2);

        // when
        List<FoodTruckMarkerItem> items = saleQueryRepository.getPositionsByCondition(
                SearchCondition.of(category1.getId(), "",
                        BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253)));
        for (FoodTruckMarkerItem item : items) {
            log.debug("item={}", item);
        }

        // then
        assertThat(items).isNotEmpty();
    }

    @DisplayName("사용자는 키워드에 해당하는 현재 위치 기준 반경 1Km 이내의 영업중인 푸드트럭 위치 정보를 조회할 수 있다.")
    @Test
    void getPositionsByConditionWithKeyword() {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@gmail.com");
        Member vendor2 = createMember(Role.VENDOR, "hi@ssafy.com");
        Member client1 = createMember(Role.CLIENT, "ssafy123@ssafy.com");
        Member client2 = createMember(Role.CLIENT, "hello123@ssafy.com");

        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        setUpData(vendor1, vendor2, client1, client2, category1, category2);

        // when
        List<FoodTruckMarkerItem> items = saleQueryRepository.getPositionsByCondition(
                SearchCondition.of(null, "된장삼겹",
                        BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253)));
        for (FoodTruckMarkerItem item : items) {
            log.debug("item={}", item);
        }

        // then
        assertThat(items).isNotEmpty();
    }

    @DisplayName("사용자는 검색 조건 없이 가까운 순으로 현재 위치 기준 반경 1Km 이내의 영업중인 푸드트럭 목록을 조회할 수 있다.")
    @Test
    void getFoodTrucksWithoutConditionOrderByDistance() {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@gmail.com");
        Member vendor2 = createMember(Role.VENDOR, "hi@ssafy.com");
        Member client1 = createMember(Role.CLIENT, "ssafy123@ssafy.com");
        Member client2 = createMember(Role.CLIENT, "hello123@ssafy.com");

        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        setUpData(vendor1, vendor2, client1, client2, category1, category2);

        // when
        List<FoodTruckItem> items = saleQueryRepository.getFoodTrucksByCondition(
                SearchCondition.of(null, "",
                        BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253),
                        ""),
                client1.getEmail(), null);

        for (FoodTruckItem item : items) {
            log.debug("item={}", item);
        }

        // then
        assertThat(items).isNotEmpty();
    }

    @DisplayName("사용자는 가까운 순으로 현재 위치 기준 반경 1Km 이내의 선택된 카테고리에 해당하는 영업중인 푸드트럭 목록을 조회할 수 있다.")
    @Test
    void getFoodTrucksWithCategoryIdOrderByDistance() {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@gmail.com");
        Member vendor2 = createMember(Role.VENDOR, "hi@ssafy.com");
        Member client1 = createMember(Role.CLIENT, "ssafy123@ssafy.com");
        Member client2 = createMember(Role.CLIENT, "hello123@ssafy.com");

        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        setUpData(vendor1, vendor2, client1, client2, category1, category2);

        // when
        List<FoodTruckItem> items = saleQueryRepository.getFoodTrucksByCondition(
                SearchCondition.of(category1.getId(), "",
                        BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253),
                        ""),
                client1.getEmail(), null);

        for (FoodTruckItem item : items) {
            log.debug("item={}", item);
        }

        // then
        assertThat(items).isNotEmpty();
    }

    @DisplayName("사용자는 가까운 순으로 현재 위치 기준 반경 1Km 이내의 검색 키워드에 해당하는 영업중인 푸드트럭 목록을 조회할 수 있다.")
    @Test
    void getFoodTrucksWithKeywordOrderByDistance() {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@gmail.com");
        Member vendor2 = createMember(Role.VENDOR, "hi@ssafy.com");
        Member client1 = createMember(Role.CLIENT, "ssafy123@ssafy.com");
        Member client2 = createMember(Role.CLIENT, "hello123@ssafy.com");

        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        setUpData(vendor1, vendor2, client1, client2, category1, category2);

        // when
        List<FoodTruckItem> items = saleQueryRepository.getFoodTrucksByCondition(
                SearchCondition.of(null, "된장삼겹",
                        BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253),
                        ""),
                client1.getEmail(), null);

        for (FoodTruckItem item : items) {
            log.debug("item={}", item);
        }

        // then
        assertThat(items).isNotEmpty();
    }

    @DisplayName("사용자는 검색 조건 없이 찜(좋아요) 순으로 현재 위치 기준 반경 1Km 이내의 영업중인 푸드트럭 목록을 조회할 수 있다.")
    @Test
    void getFoodTrucksWithoutConditionOrderByLike() {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@gmail.com");
        Member vendor2 = createMember(Role.VENDOR, "hi@ssafy.com");
        Member client1 = createMember(Role.CLIENT, "ssafy123@ssafy.com");
        Member client2 = createMember(Role.CLIENT, "hello123@ssafy.com");

        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        setUpData(vendor1, vendor2, client1, client2, category1, category2);

        // when
        List<FoodTruckItem> items = saleQueryRepository.getFoodTrucksByCondition(
                SearchCondition.of(null, "",
                        BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253),
                        OrderCondition.LIKE.name()),
                client1.getEmail(), null);
        for (FoodTruckItem item : items) {
            log.debug("item={}", item);
        }

        // then
        assertThat(items).isNotEmpty();
    }

    @DisplayName("사용자는 찜(좋아요) 순으로 현재 위치 기준 반경 1Km 이내의 선택된 카테고리에 해당하는 영업중인 푸드트럭 목록을 조회할 수 있다.")
    @Test
    void getFoodTrucksWithCategoryIdOrderByLike() {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@gmail.com");
        Member vendor2 = createMember(Role.VENDOR, "hi@ssafy.com");
        Member client1 = createMember(Role.CLIENT, "ssafy123@ssafy.com");
        Member client2 = createMember(Role.CLIENT, "hello123@ssafy.com");

        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        setUpData(vendor1, vendor2, client1, client2, category1, category2);

        // when
        List<FoodTruckItem> items = saleQueryRepository.getFoodTrucksByCondition(
                SearchCondition.of(category1.getId(), "",
                        BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253),
                        OrderCondition.LIKE.name()),
                client1.getEmail(), null);

        for (FoodTruckItem item : items) {
            log.debug("item={}", item);
        }

        // then
        assertThat(items).isNotEmpty();
    }

    @DisplayName("사용자는 찜(좋아요) 순으로 현재 위치 기준 반경 1Km 이내의 검색 키워드에 해당하는 영업중인 푸드트럭 목록을 조회할 수 있다.")
    @Test
    void getFoodTrucksWithKeywordOrderByLike() {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@gmail.com");
        Member vendor2 = createMember(Role.VENDOR, "hi@ssafy.com");
        Member client1 = createMember(Role.CLIENT, "ssafy123@ssafy.com");
        Member client2 = createMember(Role.CLIENT, "hello123@ssafy.com");

        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        setUpData(vendor1, vendor2, client1, client2, category1, category2);

        // when
        List<FoodTruckItem> items = saleQueryRepository.getFoodTrucksByCondition(
                SearchCondition.of(null, "된장삼겹",
                        BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253),
                        OrderCondition.LIKE.name()),
                client1.getEmail(), null);

        for (FoodTruckItem item : items) {
            log.debug("item={}", item);
        }

        // then
        assertThat(items).isNotEmpty();
    }

    @DisplayName("사용자는 검색 조건 없이 리뷰 개수 순으로 현재 위치 기준 반경 1Km 이내의 영업중인 푸드트럭 목록을 조회할 수 있다.")
    @Test
    void getFoodTrucksWithoutConditionOrderByReview() {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@gmail.com");
        Member vendor2 = createMember(Role.VENDOR, "hi@ssafy.com");
        Member client1 = createMember(Role.CLIENT, "ssafy123@ssafy.com");
        Member client2 = createMember(Role.CLIENT, "hello123@ssafy.com");

        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        setUpData(vendor1, vendor2, client1, client2, category1, category2);

        // when
        List<FoodTruckItem> items = saleQueryRepository.getFoodTrucksByCondition(
                SearchCondition.of(null, "",
                        BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253),
                        OrderCondition.REVIEW.name()),
                client1.getEmail(), null);
        for (FoodTruckItem item : items) {
            log.debug("item={}", item);
        }

        // then
        assertThat(items).isNotEmpty();
    }

    @DisplayName("사용자는 리뷰 개수 순으로 현재 위치 기준 반경 1Km 이내의 선택된 카테고리에 해당하는 영업중인 푸드트럭 목록을 조회할 수 있다.")
    @Test
    void getFoodTrucksWithCategoryIdOrderByReview() {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@gmail.com");
        Member vendor2 = createMember(Role.VENDOR, "hi@ssafy.com");
        Member client1 = createMember(Role.CLIENT, "ssafy123@ssafy.com");
        Member client2 = createMember(Role.CLIENT, "hello123@ssafy.com");

        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        setUpData(vendor1, vendor2, client1, client2, category1, category2);

        // when
        List<FoodTruckItem> items = saleQueryRepository.getFoodTrucksByCondition(
                SearchCondition.of(category1.getId(), "",
                        BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253),
                        OrderCondition.REVIEW.name()),
                client1.getEmail(), null);
        for (FoodTruckItem item : items) {
            log.debug("item={}", item);
        }

        // then
        assertThat(items).isNotEmpty();
    }

    @DisplayName("사용자는 리뷰 개수 순으로 현재 위치 기준 반경 1Km 이내의 검색 키워드에 해당하는 영업중인 푸드트럭 목록을 조회할 수 있다.")
    @Test
    void getFoodTrucksWithKeywordOrderByReview() {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@gmail.com");
        Member vendor2 = createMember(Role.VENDOR, "hi@ssafy.com");
        Member client1 = createMember(Role.CLIENT, "ssafy123@ssafy.com");
        Member client2 = createMember(Role.CLIENT, "hello123@ssafy.com");

        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        setUpData(vendor1, vendor2, client1, client2, category1, category2);

        // when
        List<FoodTruckItem> items = saleQueryRepository.getFoodTrucksByCondition(
                SearchCondition.of(null, "된장삼겹",
                        BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253),
                        OrderCondition.REVIEW.name()),
                client1.getEmail(), null);
        for (FoodTruckItem item : items) {
            log.debug("item={}", item);
        }

        // then
        assertThat(items).isNotEmpty();
    }
    
    @DisplayName("사용자는 검색 조건 없이 별점 순으로 현재 위치 기준 반경 1Km 이내의 영업 중인 푸드트럭 목록을 조회할 수 있다.")
    @Test
    void getFoodTrucksWithoutConditionOrderByGrade() {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@gmail.com");
        Member vendor2 = createMember(Role.VENDOR, "hi@ssafy.com");
        Member client1 = createMember(Role.CLIENT, "ssafy123@ssafy.com");
        Member client2 = createMember(Role.CLIENT, "hello123@ssafy.com");

        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        setUpData(vendor1, vendor2, client1, client2, category1, category2);

        // when
        List<FoodTruckItem> items = saleQueryRepository.getFoodTrucksByCondition(
                SearchCondition.of(null, "",
                        BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253),
                        OrderCondition.GRADE.name()),
                client1.getEmail(), null);
        for (FoodTruckItem item : items) {
            log.debug("item={}", item);
        }

        // then
        assertThat(items).isNotEmpty();
    }

    @DisplayName("사용자는 별점 순으로 현재 위치 기준 반경 1Km 이내의 선택된 카테고리에 해당하는 영업중인 푸드트럭 목록을 조회할 수 있다.")
    @Test
    void getFoodTrucksWithCategoryIdOrderByGrade() {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@gmail.com");
        Member vendor2 = createMember(Role.VENDOR, "hi@ssafy.com");
        Member client1 = createMember(Role.CLIENT, "ssafy123@ssafy.com");
        Member client2 = createMember(Role.CLIENT, "hello123@ssafy.com");

        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        setUpData(vendor1, vendor2, client1, client2, category1, category2);

        // when
        List<FoodTruckItem> items = saleQueryRepository.getFoodTrucksByCondition(
                SearchCondition.of(category1.getId(), "",
                        BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253),
                        OrderCondition.GRADE.name()),
                client1.getEmail(), null);
        for (FoodTruckItem item : items) {
            log.debug("item={}", item);
        }

        // then
        assertThat(items).isNotEmpty();
    }

    @DisplayName("사용자는 별점 순으로 현재 위치 기준 반경 1Km 이내의 검색 키워드에 해당하는 영업중인 푸드트럭 목록을 조회할 수 있다.")
    @Test
    void getFoodTrucksWithKeywordOrderByGrade() {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@gmail.com");
        Member vendor2 = createMember(Role.VENDOR, "hi@ssafy.com");
        Member client1 = createMember(Role.CLIENT, "ssafy123@ssafy.com");
        Member client2 = createMember(Role.CLIENT, "hello123@ssafy.com");

        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        setUpData(vendor1, vendor2, client1, client2, category1, category2);

        // when
        List<FoodTruckItem> items = saleQueryRepository.getFoodTrucksByCondition(
                SearchCondition.of(null, "된장삼겹",
                        BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253),
                        OrderCondition.GRADE.name()),
                client1.getEmail(), null);
        for (FoodTruckItem item : items) {
            log.debug("item={}", item);
        }

        // then
        assertThat(items).isNotEmpty();
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

    private void setUpData(Member vendor1, Member vendor2, Member client1, Member client2, Category category1, Category category2) {
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

        Sale sale1 = createSale(foodTruck1, LocalDateTime.now().minusHours(1),
                BigDecimal.valueOf(35.204008), BigDecimal.valueOf(126.807271));

        Sale sale2 = createSale(foodTruck2, LocalDateTime.now().minusHours(2),
                BigDecimal.valueOf(35.204349), BigDecimal.valueOf(126.807805));

        createFoodTruckLikes(client1, client2, foodTruck1, foodTruck2);

        createOrdersAndReviews(client1, client2, foodTruck1, foodTruck2, sale1, sale2);
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

    private Sale createSale(FoodTruck foodTruck, LocalDateTime startTime, BigDecimal latitude, BigDecimal longitude) {
        Sale sale = Sale.builder()
                .address("sale address")
                .orderable(true)
                .foodTruck(foodTruck)
                .latitude(latitude)
                .longitude(longitude)
                .orderNumber(0)
                .totalAmount(0)
                .startTime(startTime)
                .endTime(null)
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

    private void createFoodTruckLike(Member member, FoodTruck foodTruck) {
        FoodTruckLike foodTruckLike = FoodTruckLike.builder()
                .foodTruck(foodTruck)
                .member(member)
                .active(true)
                .build();
        foodTruckLikeRepository.save(foodTruckLike);
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
}