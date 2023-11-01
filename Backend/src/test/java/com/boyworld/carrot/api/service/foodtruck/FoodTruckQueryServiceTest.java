package com.boyworld.carrot.api.service.foodtruck;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.controller.foodtruck.response.FoodTruckOverview;
import com.boyworld.carrot.api.controller.foodtruck.response.FoodTruckResponse;
import com.boyworld.carrot.api.service.member.error.InvalidAccessException;
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