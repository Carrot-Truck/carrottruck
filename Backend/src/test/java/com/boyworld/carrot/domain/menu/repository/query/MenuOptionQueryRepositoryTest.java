package com.boyworld.carrot.domain.menu.repository.query;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.controller.menu.response.MenuOptionResponse;
import com.boyworld.carrot.domain.foodtruck.Category;
import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import com.boyworld.carrot.domain.foodtruck.repository.command.CategoryRepository;
import com.boyworld.carrot.domain.foodtruck.repository.command.FoodTruckRepository;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
import com.boyworld.carrot.domain.menu.Menu;
import com.boyworld.carrot.domain.menu.MenuInfo;
import com.boyworld.carrot.domain.menu.MenuOption;
import com.boyworld.carrot.domain.menu.repository.command.MenuOptionRepository;
import com.boyworld.carrot.domain.menu.repository.command.MenuRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 메뉴 옵션 조회 레포지토리 테스트
 *
 * @author 최영환
 */
@Slf4j
class MenuOptionQueryRepositoryTest extends IntegrationTestSupport {
    @Autowired
    private MenuOptionQueryRepository menuOptionQueryRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuOptionRepository menuOptionRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FoodTruckRepository foodTruckRepository;

    @DisplayName("메뉴 식별키로 메뉴 옵션 리스트를 조회한다.")
    @Test
    void getMenuOptionsByMenuId() {
        // given
        Member vendor = createMember(Role.VENDOR, "ssafy@gmail.com");

        Category category = createCategory("고기/구이");

        FoodTruck foodTruck = createFoodTruck(vendor, category, "동현 된장삼겹", "010-1234-5678",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                40,
                10,
                true);

        Menu menu = Menu.builder()
                .foodTruck(foodTruck)
                .menuInfo(MenuInfo.builder().name("메뉴1").price(10000).description("메뉴 설명1").soldOut(false).build())
                .active(true)
                .build();

        MenuOption option1 = MenuOption.builder()
                .menu(menu)
                .menuInfo(MenuInfo.builder().name("메뉴옵션1").price(500).description("메뉴 옵션 설명1").soldOut(false).build())
                .active(true)
                .build();

        MenuOption option2 = MenuOption.builder()
                .menu(menu)
                .menuInfo(MenuInfo.builder().name("메뉴옵션2").price(300).description("메뉴 옵션 설명2").soldOut(false).build())
                .active(true)
                .build();
        menuOptionRepository.saveAll(List.of(option1, option2));

        // when
        List<MenuOptionResponse> options = menuOptionQueryRepository.getMenuOptionsByMenuId(menu.getId());
        for (MenuOptionResponse option : options) {
            log.debug("option={}", option);
        }

        // then
        assertThat(options).isNotEmpty();
    }

    @DisplayName("메뉴 식별키에 해당하는 메뉴 옵션이 없으면 빈 리스트가 반환된다.")
    @Test
    void getEmptyMenuOptionsByMenuId() {
        // given
        Member vendor = createMember(Role.VENDOR, "ssafy@gmail.com");

        Category category = createCategory("고기/구이");

        FoodTruck foodTruck = createFoodTruck(vendor, category, "동현 된장삼겹", "010-1234-5678",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                40,
                10,
                true);

        Menu menu = Menu.builder()
                .foodTruck(foodTruck)
                .menuInfo(MenuInfo.builder().name("메뉴1").price(10000).description("메뉴 설명1").soldOut(false).build())
                .active(true)
                .build();
        Menu saveMenu = menuRepository.save(menu);

        // when
        List<MenuOptionResponse> options = menuOptionQueryRepository.getMenuOptionsByMenuId(saveMenu.getId());
        for (MenuOptionResponse option : options) {
            log.debug("option={}", option);
        }

        // then
        assertThat(options).isEmpty();
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
}