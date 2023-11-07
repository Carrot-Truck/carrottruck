package com.boyworld.carrot.api.service.statistics;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.controller.statistics.response.StatisticsByMonthResponse;
import com.boyworld.carrot.api.controller.statistics.response.StatisticsBySalesResponse;
import com.boyworld.carrot.api.controller.statistics.response.StatisticsByWeekResponse;
import com.boyworld.carrot.domain.foodtruck.Category;
import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import com.boyworld.carrot.domain.foodtruck.repository.command.CategoryRepository;
import com.boyworld.carrot.domain.foodtruck.repository.command.FoodTruckRepository;
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
import java.time.LocalDateTime;

@Slf4j
public class StatisticsQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    StatisticsQueryService statisticsQueryService;

    @Autowired
    FoodTruckRepository foodTruckRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SaleRepository saleRepository;

    @DisplayName("사업자는 영업별 매출 통계 리스트를 확인할 수 있다.")
    @Test
    void getStatisticsBySales() {
        Member member = createMember(Role.VENDOR, "ssafy@ssafy.com");
        Category category = createCategory("한식");
        FoodTruck foodTruck = createFoodTruck(member, category, "동현 된장삼겹", "010-1234-5678",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                40,
                10,
                true);

        Sale sale1 = createSale(foodTruck, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                    10,
                    170000,
                    LocalDateTime.now().minusDays(3).minusHours(8),
                    LocalDateTime.now().minusDays(3).minusHours(4));

        Sale sale2 = createSale(foodTruck, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                2,
                30000,
                LocalDateTime.now().minusDays(2).minusHours(7),
                LocalDateTime.now().minusDays(2).minusHours(3).minusMinutes(10));

        Sale sale3 = createSale(foodTruck, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                50,
                800000,
                LocalDateTime.now().minusDays(1).minusHours(9),
                LocalDateTime.now().minusDays(1).minusHours(5).minusMinutes(15));

        StatisticsBySalesResponse response = statisticsQueryService.getStatisticsBySales(
                foodTruck.getId(),
                LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonthValue(),
                null
        );

        log.debug("StatisticsQueryServiceTest#getStatisticsBySales#response={}", response);
    }

    @DisplayName("사업자는 주별 매출 통계 리스트를 확인할 수 있다.")
    @Test
    void getStatisticsByWeek() {
        Member member = createMember(Role.VENDOR, "ssafy@ssafy.com");
        Category category = createCategory("한식");
        FoodTruck foodTruck = createFoodTruck(member, category, "동현 된장삼겹", "010-1234-5678",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                40,
                10,
                true);

        Sale sale1 = createSale(foodTruck, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                10,
                1,
                LocalDateTime.parse("2023-01-02T00:00:00"),
                LocalDateTime.parse("2023-01-02T00:00:00").plusHours(5).minusMinutes(15));

        Sale sale2 = createSale(foodTruck, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                2,
                10,
                LocalDateTime.parse("2023-01-03T00:00:00"),
                LocalDateTime.parse("2023-01-03T00:00:00").plusHours(5).minusMinutes(15));

        Sale sale3 = createSale(foodTruck, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                50,
                100,
                LocalDateTime.parse("2023-01-04T00:00:00"),
                LocalDateTime.parse("2023-01-04T00:00:00").plusHours(5).minusMinutes(15));

        Sale sale4 = createSale(foodTruck, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                22,
                1000,
                LocalDateTime.parse("2023-12-30T00:00:00"),
                LocalDateTime.parse("2023-12-30T00:00:00").plusHours(5).minusMinutes(15));

        Sale sale5 = createSale(foodTruck, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                22,
                10000,
                LocalDateTime.parse("2023-12-31T00:00:00"),
                LocalDateTime.parse("2023-12-31T00:00:00").plusHours(5).minusMinutes(15));

        Sale sale6 = createSale(foodTruck, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                22,
                100000,
                LocalDateTime.parse("2024-01-01T00:00:00"),
                LocalDateTime.parse("2024-01-01T00:00:00").plusHours(5).minusMinutes(15));

        Sale sale7 = createSale(foodTruck, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                22,
                1000000,
                LocalDateTime.parse("2024-01-02T00:00:00"),
                LocalDateTime.parse("2024-01-02T00:00:00").plusHours(5).minusMinutes(15));

        Sale sale8 = createSale(foodTruck, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                22,
                10000000,
                LocalDateTime.parse("2024-01-03T00:00:00"),
                LocalDateTime.parse("2024-01-03T00:00:00").plusHours(5).minusMinutes(15));

        Sale sale9 = createSale(foodTruck, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                22,
                100000000,
                LocalDateTime.parse("2024-01-04T00:00:00"),
                LocalDateTime.parse("2024-01-04T00:00:00").plusHours(5).minusMinutes(15));
        Sale sale10 = createSale(foodTruck, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                22,
                1000000000,
                LocalDateTime.parse("2024-05-05T00:00:00"),
                LocalDateTime.parse("2024-05-05T00:00:00").plusHours(5).minusMinutes(15));
        Sale sale11 = createSale(foodTruck, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                22,
                2,
                LocalDateTime.parse("2024-06-06T00:00:00"),
                LocalDateTime.parse("2024-06-06T00:00:00").plusHours(5).minusMinutes(15));
        Sale sale12 = createSale(foodTruck, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                22,
                20,
                LocalDateTime.parse("2024-07-07T00:00:00"),
                LocalDateTime.parse("2024-07-07T00:00:00").plusHours(5).minusMinutes(15));
        Sale sale13 = createSale(foodTruck, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                22,
                20,
                LocalDateTime.parse("2024-08-07T00:00:00"),
                LocalDateTime.parse("2024-08-07T00:00:00").plusHours(5).minusMinutes(15));
        Sale sale14 = createSale(foodTruck, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                22,
                20,
                LocalDateTime.parse("2024-09-17T00:00:00"),
                LocalDateTime.parse("2024-09-17T00:00:00").plusHours(5).minusMinutes(15));
        Sale sale15 = createSale(foodTruck, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                22,
                20,
                LocalDateTime.parse("2024-10-07T00:00:00"),
                LocalDateTime.parse("2024-10-07T00:00:00").plusHours(5).minusMinutes(15));
        Sale sale16 = createSale(foodTruck, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                22,
                20,
                LocalDateTime.parse("2024-10-17T00:00:00"),
                LocalDateTime.parse("2024-10-17T00:00:00").plusHours(5).minusMinutes(15));
        Sale sale17 = createSale(foodTruck, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                22,
                20,
                LocalDateTime.parse("2024-10-27T00:00:00"),
                LocalDateTime.parse("2024-10-27T00:00:00").plusHours(5).minusMinutes(15));
        Sale sale18 = createSale(foodTruck, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                22,
                20,
                LocalDateTime.parse("2024-11-07T00:00:00"),
                LocalDateTime.parse("2024-11-07T00:00:00").plusHours(5).minusMinutes(15));
        Sale sale19 = createSale(foodTruck, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                22,
                20,
                LocalDateTime.parse("2024-11-17T00:00:00"),
                LocalDateTime.parse("2024-11-17T00:00:00").plusHours(5).minusMinutes(15));
        Sale sale20 = createSale(foodTruck, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                22,
                20,
                LocalDateTime.parse("2024-11-27T00:00:00"),
                LocalDateTime.parse("2024-11-27T00:00:00").plusHours(5).minusMinutes(15));
        Sale sale21 = createSale(foodTruck, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                22,
                20,
                LocalDateTime.parse("2024-12-07T00:00:00"),
                LocalDateTime.parse("2024-12-07T00:00:00").plusHours(5).minusMinutes(15));

        log.debug("sale1={}|{}", sale1.getId(), sale1.getStartTime());
        log.debug("sale2={}|{}", sale2.getId(), sale2.getStartTime());
        log.debug("sale3={}|{}", sale3.getId(), sale3.getStartTime());
        log.debug("sale4={}|{}", sale4.getId(), sale4.getStartTime());
        log.debug("sale5={}|{}", sale5.getId(), sale5.getStartTime());
        log.debug("sale6={}|{}", sale6.getId(), sale6.getStartTime());
        log.debug("sale7={}|{}", sale7.getId(), sale7.getStartTime());
        log.debug("sale8={}|{}", sale8.getId(), sale8.getStartTime());
        log.debug("sale9={}|{}", sale9.getId(), sale9.getStartTime());
        log.debug("sale10={}|{}", sale10.getId(), sale10.getStartTime());
        log.debug("sale11={}|{}", sale11.getId(), sale11.getStartTime());
        log.debug("sale12={}|{}", sale12.getId(), sale12.getStartTime());

        StatisticsByWeekResponse response = statisticsQueryService.getStatisticsByWeek(
                foodTruck.getId(),
                LocalDateTime.now().getYear() + 1,
                null
        );

        log.debug("StatisticsQueryServiceTest#getStatisticsByWeek#response={}", response);
    }

    @DisplayName("사업자는 월별 매출 통계 리스트를 확인할 수 있다.")
    @Test
    void getStatisticsByMonth() {
        Member member = createMember(Role.VENDOR, "ssafy@ssafy.com");
        Category category = createCategory("한식");
        FoodTruck foodTruck = createFoodTruck(member, category, "동현 된장삼겹", "010-1234-5678",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                40,
                10,
                true);

        Sale sale13 = createSale(foodTruck, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                22,
                20,
                LocalDateTime.parse("2024-08-07T00:00:00"),
                LocalDateTime.parse("2024-08-07T00:00:00").plusHours(5).minusMinutes(15));
        Sale sale14 = createSale(foodTruck, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                22,
                20,
                LocalDateTime.parse("2024-09-17T00:00:00"),
                LocalDateTime.parse("2024-09-17T00:00:00").plusHours(5).minusMinutes(15));
        Sale sale15 = createSale(foodTruck, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                22,
                20,
                LocalDateTime.parse("2024-10-07T00:00:00"),
                LocalDateTime.parse("2024-10-07T00:00:00").plusHours(5).minusMinutes(15));
        Sale sale16 = createSale(foodTruck, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                22,
                20,
                LocalDateTime.parse("2024-10-17T00:00:00"),
                LocalDateTime.parse("2024-10-17T00:00:00").plusHours(5).minusMinutes(15));
        Sale sale17 = createSale(foodTruck, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                22,
                20,
                LocalDateTime.parse("2024-10-27T00:00:00"),
                LocalDateTime.parse("2024-10-27T00:00:00").plusHours(5).minusMinutes(15));
        Sale sale18 = createSale(foodTruck, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                22,
                20,
                LocalDateTime.parse("2024-11-07T00:00:00"),
                LocalDateTime.parse("2024-11-07T00:00:00").plusHours(5).minusMinutes(15));
        Sale sale19 = createSale(foodTruck, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                22,
                20,
                LocalDateTime.parse("2024-11-17T00:00:00"),
                LocalDateTime.parse("2024-11-17T00:00:00").plusHours(5).minusMinutes(15));

        StatisticsByMonthResponse response = statisticsQueryService.getStatisticsByMonth(
                foodTruck.getId(),
                LocalDateTime.now().getYear() + 1
        );

        log.debug("StatisticsQueryServiceTest#getStatisticsByMonth#response={}", response);
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
                .totalAmount(totalAmount)
                .startTime(startTime)
                .endTime(endTime)
                .active(true)
                .build();
        return saleRepository.save(sale);
    }

}
