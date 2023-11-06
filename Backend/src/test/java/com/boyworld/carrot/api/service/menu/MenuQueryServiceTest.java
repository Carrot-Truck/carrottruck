package com.boyworld.carrot.api.service.menu;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.controller.menu.response.MenuResponse;
import com.boyworld.carrot.api.service.member.error.InValidAccessException;
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
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 메뉴 조회 서비스 테스트
 *
 * @author 최영환
 */
@Slf4j
class MenuQueryServiceTest extends IntegrationTestSupport {
    @Autowired
    private MenuQueryService menuQueryService;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FoodTruckRepository foodTruckRepository;

    @DisplayName("사업자는 푸드트럭 식별키로 보유한 푸드트럭의 메뉴 목록을 조회할 수 있다.")
    @Test
    void getMenus() {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@ssafy.com");
        Member vendor2 = createMember(Role.VENDOR, "hi@ssafy.com");

        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        List<FoodTruck> foodTrucks = createFoodTrucks(vendor1, vendor2, category1, category2);

        FoodTruck foodTruck1 = foodTrucks.get(0);
        FoodTruck foodTruck2 = foodTrucks.get(1);

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
        menuRepository.saveAll(List.of(menu1, menu2));

        // when
        MenuResponse response = menuQueryService.getMenus(foodTruck1.getId(), vendor1.getEmail());

        // then
        assertThat(response).isNotNull();
        assertThat(response.getMenuCount()).isEqualTo(2);
        assertThat(response.getMenus()).hasSize(2);
    }

    @DisplayName("일반 사용자나 소유주가 아닌 사업자가 접근하면 예외가 발생한다.")
    @Test
    void getMenusThrowException() {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@ssafy.com");
        Member vendor2 = createMember(Role.VENDOR, "hi@ssafy.com");
        Member client = createMember(Role.CLIENT, "hi123@ssafy.com");

        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        List<FoodTruck> foodTrucks = createFoodTrucks(vendor1, vendor2, category1, category2);

        FoodTruck foodTruck1 = foodTrucks.get(0);
        FoodTruck foodTruck2 = foodTrucks.get(1);

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
        menuRepository.saveAll(List.of(menu1, menu2));

        // when // then
        assertThatThrownBy(() -> menuQueryService.getMenus(foodTruck2.getId(), vendor1.getEmail()))
                .isInstanceOf(InValidAccessException.class)
                .hasMessage("잘못된 접근입니다.");

        assertThatThrownBy(() -> menuQueryService.getMenus(foodTruck1.getId(), vendor2.getEmail()))
                .isInstanceOf(InValidAccessException.class)
                .hasMessage("잘못된 접근입니다.");

        assertThatThrownBy(() -> menuQueryService.getMenus(foodTruck1.getId(), client.getEmail()))
                .isInstanceOf(InValidAccessException.class)
                .hasMessage("잘못된 접근입니다.");

        assertThatThrownBy(() -> menuQueryService.getMenus(foodTruck2.getId(), client.getEmail()))
                .isInstanceOf(InValidAccessException.class)
                .hasMessage("잘못된 접근입니다.");
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
}