package com.boyworld.carrot.api.service.statistics;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.controller.statistics.response.StatisticsBySalesResponse;
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
                LocalDateTime.now().minusDays(2).minusHours(3));

        Sale sale3 = createSale(foodTruck, new BigDecimal("35.19508792"), new BigDecimal("126.8145971"),
                "광주광역시 광산구 장덕동",
                50,
                800000,
                LocalDateTime.now().minusDays(1).minusHours(9),
                LocalDateTime.now().minusDays(1).minusHours(5));

        StatisticsBySalesResponse response = statisticsQueryService.getStatisticsBySales(
                foodTruck.getId(),
                LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonthValue(),
                null
        );

        log.debug("StatisticsQueryServiceTest#getStatisticsBySales#response={}", response);
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
