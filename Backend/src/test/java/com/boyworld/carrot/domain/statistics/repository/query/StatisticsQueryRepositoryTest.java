package com.boyworld.carrot.domain.statistics.repository.query;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.controller.statistics.response.StatisticsByMonthDetailsResponse;
import com.boyworld.carrot.api.controller.statistics.response.StatisticsBySalesDetailsResponse;
import com.boyworld.carrot.api.controller.statistics.response.StatisticsByWeekDetailsResponse;
import com.boyworld.carrot.domain.foodtruck.Category;
import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import com.boyworld.carrot.domain.foodtruck.repository.command.CategoryRepository;
import com.boyworld.carrot.domain.foodtruck.repository.command.FoodTruckRepository;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
import com.boyworld.carrot.domain.menu.Menu;
import com.boyworld.carrot.domain.menu.MenuInfo;
import com.boyworld.carrot.domain.menu.repository.command.MenuRepository;
import com.boyworld.carrot.domain.order.Order;
import com.boyworld.carrot.domain.order.OrderMenu;
import com.boyworld.carrot.domain.order.Status;
import com.boyworld.carrot.domain.order.repository.command.OrderMenuRepository;
import com.boyworld.carrot.domain.order.repository.command.OrderRepository;
import com.boyworld.carrot.domain.sale.Sale;
import com.boyworld.carrot.domain.sale.repository.command.SaleRepository;
import com.boyworld.carrot.domain.sale.repository.query.StatisticsQueryRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public class StatisticsQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    StatisticsQueryRepository statisticsQueryRepository;

    @Autowired
    FoodTruckRepository foodTruckRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    SaleRepository saleRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderMenuRepository orderMenuRepository;

    @DisplayName("영업 매출 통계의 상세 정보를 조회한다.")
    @Test
    void getSaleDetail() {
        Member vendor = createMember(Role.VENDOR, "vendor@ssafy.com");
        Member client1 = createMember(Role.CLIENT, "client1@ssafy.com");
        Member client2 = createMember(Role.CLIENT, "client2@ssafy.com");
        Member client3 = createMember(Role.CLIENT, "client3@ssafy.com");

        Category category = createCategory("고기/구이");

        FoodTruck foodTruck = createFoodTruck(vendor, category, "동현 된장삼겹", "010-1234-5678",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                40,
                10,
                true);

        Menu menu1 = Menu.builder()
                .foodTruck(foodTruck)
                .menuInfo(MenuInfo.builder().name("메뉴1").price(10000).description("메뉴 설명1").soldOut(false).build())
                .active(true)
                .build();

        Menu menu2 = Menu.builder()
                .foodTruck(foodTruck)
                .menuInfo(MenuInfo.builder().name("메뉴2").price(15000).description("메뉴 설명2").soldOut(false).build())
                .active(true)
                .build();
        menuRepository.saveAll(List.of(menu1, menu2));

        Sale sale1 = createSale(foodTruck, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                10,
                170000,
                LocalDateTime.now().minusDays(3).minusHours(8),
                LocalDateTime.now().minusDays(3).minusHours(4));

        Order order1 = createOrder(client1, sale1);
        OrderMenu orderMenu1 = createOrderMenu(order1, menu1, 1);
        OrderMenu orderMenu2 = createOrderMenu(order1, menu2, 2);

        Order order2 = createOrder(client2, sale1);
        OrderMenu orderMenu3 = createOrderMenu(order2, menu1, 1);

        Order order3 = createOrder(client3, sale1);
        OrderMenu orderMenu4 = createOrderMenu(order3, menu2, 3);

        Sale sale2 = createSale(foodTruck, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                10,
                170000,
                LocalDateTime.now().minusDays(3).minusHours(8),
                LocalDateTime.now().minusDays(3).minusHours(4));

        Order order4 = createOrder(client3, sale2);
        OrderMenu orderMenu5 = createOrderMenu(order4, menu2, 10);

        StatisticsBySalesDetailsResponse response = statisticsQueryRepository
                .getSaleDetail(foodTruck.getId(), sale1.getId());

        log.debug("StatisticsQRepo#StatisticsSalesDetailResponse={}", response);
    }

    @DisplayName("주별 매출 통계 정보의 상세 정보를 조회한다.")
    @Test
    void getWeekDetail() {
        Member vendor = createMember(Role.VENDOR, "vendor@ssafy.com");
        Member client1 = createMember(Role.CLIENT, "client1@ssafy.com");
        Member client2 = createMember(Role.CLIENT, "client2@ssafy.com");
        Member client3 = createMember(Role.CLIENT, "client3@ssafy.com");

        Category category = createCategory("고기/구이");

        FoodTruck foodTruck1 = createFoodTruck(vendor, category, "동현 된장삼겹", "010-1234-5678",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                40,
                10,
                true);
        FoodTruck foodTruck2 = createFoodTruck(vendor, category, "동현 된장삼겹", "010-1234-5678",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                40,
                10,
                true);

        Menu menu1 = Menu.builder()
                .foodTruck(foodTruck1)
                .menuInfo(MenuInfo.builder().name("메뉴1").price(10000).description("메뉴 설명1").soldOut(false).build())
                .active(true)
                .build();

        Menu menu2 = Menu.builder()
                .foodTruck(foodTruck1)
                .menuInfo(MenuInfo.builder().name("메뉴2").price(15000).description("메뉴 설명2").soldOut(false).build())
                .active(true)
                .build();

        Menu menu3 = Menu.builder()
                .foodTruck(foodTruck1)
                .menuInfo(MenuInfo.builder().name("메뉴2").price(99).description("메뉴 설명2").soldOut(false).build())
                .active(true)
                .build();
        
        menuRepository.saveAll(List.of(menu1, menu2, menu3));

        Sale sale1 = createSale(foodTruck1, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                10,
                170000,
                LocalDateTime.now().minusDays(10).minusHours(8),
                LocalDateTime.now().minusDays(10).minusHours(4));

        Order order1 = createOrder(client1, sale1);
        OrderMenu orderMenu1 = createOrderMenu(order1, menu1, 1);
        OrderMenu orderMenu2 = createOrderMenu(order1, menu2, 2);

        Order order2 = createOrder(client2, sale1);
        OrderMenu orderMenu3 = createOrderMenu(order2, menu1, 1);

        Order order3 = createOrder(client3, sale1);
        OrderMenu orderMenu4 = createOrderMenu(order3, menu2, 3);

        Sale sale2 = createSale(foodTruck1, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                10,
                170000,
                LocalDateTime.now().minusDays(2).minusHours(7),
                LocalDateTime.now().minusDays(2).minusHours(4));

        Order order4 = createOrder(client3, sale2);
        OrderMenu orderMenu5 = createOrderMenu(order4, menu2, 10);

        Sale sale3 = createSale(foodTruck2, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                10,
                170000,
                LocalDateTime.now().minusHours(7),
                LocalDateTime.now());

        Order order5 = createOrder(client1, sale3);
        OrderMenu orderMenu6 = createOrderMenu(order5, menu1, 99);

        StatisticsByWeekDetailsResponse response = statisticsQueryRepository
                .getWeeklyDetail(foodTruck1.getId(), LocalDateTime.now().minusWeeks(1), LocalDateTime.now());

        log.debug("StatisticsQRepo#StatisticsByWeekDetailsResponse={}", response);
    }

    @DisplayName("월별 매출 통계 정보의 상세 정보를 조회한다.")
    @Test
    void getMonthDetail() {
        Member vendor = createMember(Role.VENDOR, "vendor@ssafy.com");
        Member client1 = createMember(Role.CLIENT, "client1@ssafy.com");
        Member client2 = createMember(Role.CLIENT, "client2@ssafy.com");
        Member client3 = createMember(Role.CLIENT, "client3@ssafy.com");

        Category category = createCategory("고기/구이");

        FoodTruck foodTruck1 = createFoodTruck(vendor, category, "동현 된장삼겹", "010-1234-5678",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                40,
                10,
                true);
        FoodTruck foodTruck2 = createFoodTruck(vendor, category, "동현 된장삼겹", "010-1234-5678",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                40,
                10,
                true);

        Menu menu1 = Menu.builder()
                .foodTruck(foodTruck1)
                .menuInfo(MenuInfo.builder().name("메뉴1").price(10000).description("메뉴 설명1").soldOut(false).build())
                .active(true)
                .build();

        Menu menu2 = Menu.builder()
                .foodTruck(foodTruck1)
                .menuInfo(MenuInfo.builder().name("메뉴2").price(15000).description("메뉴 설명2").soldOut(false).build())
                .active(true)
                .build();

        Menu menu3 = Menu.builder()
                .foodTruck(foodTruck1)
                .menuInfo(MenuInfo.builder().name("메뉴2").price(99).description("메뉴 설명2").soldOut(false).build())
                .active(true)
                .build();

        menuRepository.saveAll(List.of(menu1, menu2, menu3));

        Sale sale1 = createSale(foodTruck1, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                10,
                170000,
                LocalDateTime.now().minusMonths(1).minusHours(8),
                LocalDateTime.now().minusMonths(1).minusHours(4));

        Order order1 = createOrder(client1, sale1);
        OrderMenu orderMenu1 = createOrderMenu(order1, menu1, 1);
        OrderMenu orderMenu2 = createOrderMenu(order1, menu2, 2);

        Order order2 = createOrder(client2, sale1);
        OrderMenu orderMenu3 = createOrderMenu(order2, menu1, 1);

        Order order3 = createOrder(client3, sale1);
        OrderMenu orderMenu4 = createOrderMenu(order3, menu2, 3);

        Sale sale2 = createSale(foodTruck1, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                10,
                170000,
                LocalDateTime.now().minusDays(2).minusHours(7),
                LocalDateTime.now().minusDays(2).minusHours(4));

        Order order4 = createOrder(client3, sale2);
        OrderMenu orderMenu5 = createOrderMenu(order4, menu2, 10);

        Sale sale3 = createSale(foodTruck2, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                10,
                170000,
                LocalDateTime.now().minusHours(7),
                LocalDateTime.now());

        Order order5 = createOrder(client1, sale3);
        OrderMenu orderMenu6 = createOrderMenu(order5, menu1, 99);

        StatisticsByMonthDetailsResponse response = statisticsQueryRepository
                .getMonthlyDetail(foodTruck1.getId(), LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue());

        log.debug("StatisticsQRepo#StatisticsByMonthDetailsResponse={}", response);
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

    private Sale createSale(FoodTruck foodTruck, BigDecimal latitude, BigDecimal longitude, String address,
                                 Integer orderNumber, Integer totalAmount, LocalDateTime startTime, LocalDateTime endTime) {
        Sale sale = Sale.builder()
                .foodTruck(foodTruck)
                .latitude(latitude)
                .longitude(longitude)
                .address(address)
                .orderNumber(orderNumber)
                .orderable(true)
                .totalAmount(totalAmount)
                .startTime(startTime)
                .endTime(endTime)
                .active(true)
                .build();
        return saleRepository.save(sale);
    }

    private OrderMenu createOrderMenu(Order order, Menu menu, Integer quantity) {
        OrderMenu orderMenu = OrderMenu.builder()
                .order(order)
                .menu(menu)
                .quantity(quantity)
                .active(true)
                .build();
        order.editOrderTotalPrice(order.getTotalPrice() + quantity * menu.getMenuInfo().getPrice());
        return orderMenuRepository.save(orderMenu);
    }

    private Order createOrder(Member member, Sale sale) {
        Order order = Order.builder()
                .member(member)
                .sale(sale)
                .status(Status.COMPLETE)
                .expectTime(null)
                .totalPrice(0)
                .active(true)
                .build();
        return orderRepository.save(order);
    }
}
