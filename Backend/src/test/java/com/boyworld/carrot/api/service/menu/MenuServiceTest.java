package com.boyworld.carrot.api.service.menu;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.controller.menu.response.CreateMenuResponse;
import com.boyworld.carrot.api.service.member.error.InValidAccessException;
import com.boyworld.carrot.api.service.menu.dto.CreateMenuDto;
import com.boyworld.carrot.api.service.menu.dto.CreateMenuOptionDto;
import com.boyworld.carrot.api.service.menu.dto.EditMenuDto;
import com.boyworld.carrot.domain.foodtruck.Category;
import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import com.boyworld.carrot.domain.foodtruck.repository.command.CategoryRepository;
import com.boyworld.carrot.domain.foodtruck.repository.command.FoodTruckRepository;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
import com.boyworld.carrot.domain.menu.Menu;
import com.boyworld.carrot.domain.menu.MenuInfo;
import com.boyworld.carrot.domain.menu.repository.command.MenuImageRepository;
import com.boyworld.carrot.domain.menu.repository.command.MenuOptionRepository;
import com.boyworld.carrot.domain.menu.repository.command.MenuRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuServiceTest extends IntegrationTestSupport {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FoodTruckRepository foodTruckRepository;

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuImageRepository menuImageRepository;

    @Autowired
    private MenuOptionRepository menuOptionRepository;

    @DisplayName("사업자는 본인 소유의 푸드트럭에 메뉴와 이미지, 메뉴 옵션을 함께 등록할 수 있다.")
    @Test
    void createMenuWithMenuImageAndOptions() throws IOException {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@ssafy.com");
        Member vendor2 = createMember(Role.VENDOR, "hi@ssafy.com");
        Member client1 = createMember(Role.CLIENT, "ssafy123@ssafy.com");
        Member client2 = createMember(Role.CLIENT, "hello123@ssafy.com");

        Category category = createCategory("고기/구이");

        FoodTruck foodTruck = createFoodTruck(vendor1, category, "동현 된장삼겹", "010-1234-5678",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                40,
                10,
                true);

        CreateMenuOptionDto option1 = CreateMenuOptionDto.builder()
                .menuOptionName("옵션1")
                .menuOptionPrice(500)
                .menuOptionDescription("설명1")
                .build();

        CreateMenuOptionDto option2 = CreateMenuOptionDto.builder()
                .menuOptionName("옵션2")
                .menuOptionPrice(300)
                .menuOptionDescription("설명2")
                .build();

        CreateMenuDto dto = CreateMenuDto.builder()
                .foodTruckId(foodTruck.getId())
                .menuName("달콤짭짤한 밥도둑 된장 삼겹살 구이")
                .price(8900)
                .description("동현 된장삼겹의 시그니쳐. 오직 된장 삼겹살 구이만!")
                .menuOptionDtos(List.of(option1, option2))
                .build();

        String path = "menu_test.png";
        String contentType = "image/png";
        MockMultipartFile file = new MockMultipartFile("menu_test", path, contentType, "menu_test".getBytes());

        // when
        CreateMenuResponse response = menuService.createMenu(dto, file, vendor1.getEmail());

        // then
        assertThat(response).extracting("menuName", "foodTruckId", "price",
                        "description", "menuOptionSize")
                .containsExactlyInAnyOrder(dto.getMenuName(), foodTruck.getId(), dto.getPrice(),
                        dto.getDescription(), dto.getMenuOptionDtos().size());
    }

    @DisplayName("일반 사용자이거나 본인 소유의 푸드트럭이 아닐 경우 예외가 발생한다.")
    @Test
    void createMenuInValidAccess() {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@ssafy.com");
        Member vendor2 = createMember(Role.VENDOR, "hi@ssafy.com");
        Member client1 = createMember(Role.CLIENT, "ssafy123@ssafy.com");
        Member client2 = createMember(Role.CLIENT, "hello123@ssafy.com");

        Category category = createCategory("고기/구이");

        FoodTruck foodTruck = createFoodTruck(vendor1, category, "동현 된장삼겹", "010-1234-5678",
                "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
                "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                40,
                10,
                true);

        CreateMenuOptionDto option1 = CreateMenuOptionDto.builder()
                .menuOptionName("옵션1")
                .menuOptionPrice(500)
                .menuOptionDescription("설명1")
                .build();

        CreateMenuOptionDto option2 = CreateMenuOptionDto.builder()
                .menuOptionName("옵션2")
                .menuOptionPrice(300)
                .menuOptionDescription("설명2")
                .build();

        CreateMenuDto dto = CreateMenuDto.builder()
                .foodTruckId(foodTruck.getId())
                .menuName("달콤짭짤한 밥도둑 된장 삼겹살 구이")
                .price(8900)
                .description("동현 된장삼겹의 시그니쳐. 오직 된장 삼겹살 구이만!")
                .menuOptionDtos(List.of(option1, option2))
                .build();

        String path = "menu_test.png";
        String contentType = "image/png";
        MockMultipartFile file = new MockMultipartFile("menu_test", path, contentType, "menu_test".getBytes());

        // when // then
        assertThatThrownBy(() -> menuService.createMenu(dto, file, vendor2.getEmail()))
                .isInstanceOf(InValidAccessException.class)
                .hasMessage("잘못된 접근입니다.");

        assertThatThrownBy(() -> menuService.createMenu(dto, file, client1.getEmail()))
                .isInstanceOf(InValidAccessException.class)
                .hasMessage("잘못된 접근입니다.");
    }

    @DisplayName("사업자는 본인 소유의 푸드트럭의 메뉴와 이미지를 수정할 수 있다.")
    @Test
    void editMenuWithMenuImageAndOptions() throws IOException {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@ssafy.com");
        Member vendor2 = createMember(Role.VENDOR, "hi@ssafy.com");
        Member client1 = createMember(Role.CLIENT, "ssafy123@ssafy.com");
        Member client2 = createMember(Role.CLIENT, "hello123@ssafy.com");

        Category category = createCategory("고기/구이");

        FoodTruck foodTruck = createFoodTruck(vendor1, category, "동현 된장삼겹", "010-1234-5678",
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
        menu = menuRepository.save(menu);

        EditMenuDto dto = EditMenuDto.builder()
                .menuId(menu.getId())
                .menuName("짭짤한 밥도둑 삼겹살 구이")
                .menuPrice(9900)
                .menuDescription("동현 삼겹의 시그니쳐. 오직 된장 삼겹살 구이만!")
                .build();

        String path = "menu_edit_test.png";
        String contentType = "image/png";
        MockMultipartFile file = new MockMultipartFile("menu_edit_test", path, contentType, "menu_edit_test".getBytes());

        // when
        Long menuId = menuService.editMenu(dto, file, vendor1.getEmail());
        Menu editedMenu = menuRepository.findById(menuId)
                .orElseThrow();

        // then
        assertThat(editedMenu.getMenuInfo()).extracting("name", "description", "price", "soldOut")
                .containsExactly(dto.getMenuName(), dto.getMenuDescription(), dto.getMenuPrice(), false);
    }

    @DisplayName("일반 사용자이거나 본인 소유의 푸드트럭이 아닌 경우 예외가 발생한다.")
    @Test
    void editMenuInValidAccess() throws IOException {
        // given
        Member vendor1 = createMember(Role.VENDOR, "ssafy@ssafy.com");
        Member vendor2 = createMember(Role.VENDOR, "hi@ssafy.com");
        Member client1 = createMember(Role.CLIENT, "ssafy123@ssafy.com");
        Member client2 = createMember(Role.CLIENT, "hello123@ssafy.com");

        Category category = createCategory("고기/구이");

        FoodTruck foodTruck = createFoodTruck(vendor1, category, "동현 된장삼겹", "010-1234-5678",
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
        menu = menuRepository.save(menu);

        EditMenuDto dto = EditMenuDto.builder()
                .menuId(menu.getId())
                .menuName("짭짤한 밥도둑 삼겹살 구이")
                .menuPrice(9900)
                .menuDescription("동현 삼겹의 시그니쳐. 오직 된장 삼겹살 구이만!")
                .build();

        String path = "menu_edit_test.png";
        String contentType = "image/png";
        MockMultipartFile file = new MockMultipartFile("menu_edit_test", path, contentType, "menu_edit_test".getBytes());

        // when // then
        assertThatThrownBy(() -> menuService.editMenu(dto, file, vendor2.getEmail()))
                .isInstanceOf(InValidAccessException.class)
                .hasMessage("잘못된 접근입니다.");

        assertThatThrownBy(() -> menuService.editMenu(dto, file, client1.getEmail()))
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