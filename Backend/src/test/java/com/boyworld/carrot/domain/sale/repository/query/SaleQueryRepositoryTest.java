package com.boyworld.carrot.domain.sale.repository.query;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.service.foodtruck.dto.FoodTruckMarkerItem;
import com.boyworld.carrot.domain.foodtruck.Category;
import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import com.boyworld.carrot.domain.foodtruck.repository.command.CategoryRepository;
import com.boyworld.carrot.domain.foodtruck.repository.command.FoodTruckRepository;
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

    @DisplayName("사용자는 검색 조건 없이 현재 위치 기준 반경 1Km 이내의 영업중인 푸드트럭을 조회할 수 있다.")
    @Test
    void getPositionsByConditionWithoutCondition() {
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
        List<FoodTruckMarkerItem> items = saleQueryRepository.getPositionsByCondition(
                SearchCondition.of(null, "",
                        BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253)));
        for (FoodTruckMarkerItem item : items) {
            log.debug("item={}", item);
        }

        // then
        assertThat(items).hasSize(1);
    }

    @DisplayName("근처에 영업 중인 푸드트럭이 없으면 빈 리스트가 반환된다.")
    @Test
    void getEmptyPositionsByCondition() {
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
        List<FoodTruckMarkerItem> items = saleQueryRepository.getPositionsByCondition(
                SearchCondition.of(null, "",
                        BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253)));

        // then
        assertThat(items).isEmpty();
    }

    @DisplayName("사용자는 카테고리 식별키에 해당하는 현재 위치 기준 반경 1Km 이내의 영업중인 푸드트럭을 조회할 수 있다.")
    @Test
    void getPositionsByConditionWithCategoryId() {
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
        List<FoodTruckMarkerItem> items = saleQueryRepository.getPositionsByCondition(
                SearchCondition.of(category.getId(), "",
                        BigDecimal.valueOf(35.2094264), BigDecimal.valueOf(126.807253)));
        for (FoodTruckMarkerItem item : items) {
            log.debug("item={}", item);
        }

        // then
        assertThat(items).hasSize(2);
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