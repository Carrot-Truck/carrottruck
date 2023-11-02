package com.boyworld.carrot.domain.foodtruck.repository.query;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.controller.foodtruck.response.FoodTruckOverview;
import com.boyworld.carrot.domain.foodtruck.Category;
import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import com.boyworld.carrot.domain.foodtruck.repository.command.CategoryRepository;
import com.boyworld.carrot.domain.foodtruck.repository.command.FoodTruckRepository;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @DisplayName("사용자 이메일로 선택된 푸드트럭의 개수를 조회한다.")
    @Test
    void getSelectedCountByEmail() {
        // given
        Member member = createMember(Role.VENDOR);
        Category category = createCategory();
        FoodTruck foodTruck1 = createFoodTruck(member, category, "동현 된장삼겹", "010-1234-5678",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                40,
                10,
                true);

        // when
        Long result = foodTruckQueryRepository.getSelectedCountByEmail("ssafy@ssafy.com");

        // when
        assertThat(result).isNotZero();
    }

    @DisplayName("선택된 푸드트럭 개수 조회 시 선택된 것이 없으면 0이 반환된다.")
    @Test
    void getSelectedCountByEmailWithoutSelected() {
        // given
        Member member = createMember(Role.VENDOR);
        Category category = createCategory();

        // when
        Long result = foodTruckQueryRepository.getSelectedCountByEmail("ssafy@ssafy.com");
        log.debug("result={}", result);

        // when
        assertThat(result).isZero();
    }

    @DisplayName("사업자는 이메일로 보유 푸드트럭을 조회할 수 있다.")
    @Test
    void getFoodTruckOverviewsByEmail() {
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
        List<FoodTruckOverview> overviews =
                foodTruckQueryRepository.getFoodTruckOverviewsByEmail(null, "ssafy@ssafy.com");

        // then
        assertThat(overviews).isNotEmpty();
        assertThat(overviews).hasSize(2);
    }

    @DisplayName("보유한 푸드트럭이 없으면 빈 리스트를 반환한다.")
    @Test
    void getEmptyFoodTruckOverviewsByEmail() {
        // given
        Member member = createMember(Role.VENDOR);
        Category category = createCategory();

        // when
        List<FoodTruckOverview> overviews =
                foodTruckQueryRepository.getFoodTruckOverviewsByEmail(null, "ssafy@ssafy.com");

        // then
        assertThat(overviews).isEmpty();
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
}