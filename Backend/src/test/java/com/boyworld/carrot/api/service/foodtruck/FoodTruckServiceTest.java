package com.boyworld.carrot.api.service.foodtruck;


import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.controller.foodtruck.response.FoodTruckLikeResponse;
import com.boyworld.carrot.api.service.foodtruck.dto.CreateFoodTruckDto;
import com.boyworld.carrot.api.service.foodtruck.dto.FoodTruckLikeDto;
import com.boyworld.carrot.api.service.foodtruck.dto.UpdateFoodTruckDto;
import com.boyworld.carrot.api.service.member.error.InValidAccessException;
import com.boyworld.carrot.domain.foodtruck.Category;
import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import com.boyworld.carrot.domain.foodtruck.FoodTruckImage;
import com.boyworld.carrot.domain.foodtruck.FoodTruckLike;
import com.boyworld.carrot.domain.foodtruck.repository.command.CategoryRepository;
import com.boyworld.carrot.domain.foodtruck.repository.command.FoodTruckImageRepository;
import com.boyworld.carrot.domain.foodtruck.repository.command.FoodTruckLikeRepository;
import com.boyworld.carrot.domain.foodtruck.repository.command.FoodTruckRepository;
import com.boyworld.carrot.domain.foodtruck.repository.query.FoodTruckImageQueryRepository;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
import com.boyworld.carrot.file.UploadFile;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 푸드트럭 CUD 테스트
 *
 * @author 최영환
 */
@Slf4j
class FoodTruckServiceTest extends IntegrationTestSupport {

    @Autowired
    private FoodTruckService foodTruckService;

    @Autowired
    private FoodTruckRepository foodTruckRepository;

    @Autowired
    private FoodTruckImageRepository foodTruckImageRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FoodTruckImageQueryRepository foodTruckImageQueryRepository;

    @Autowired
    private FoodTruckLikeRepository foodTruckLikeRepository;

    @DisplayName("사업자는 푸드트럭을 이미지 없이 생성할 수 있다.")
    @Test
    void createFoodTruckAsVendorWithoutImage() throws IOException {
        // given
        Member member = createMember(Role.VENDOR, true, "ssafy@gmail.com");
        Category category = createCategory("고기/구이");

        CreateFoodTruckDto dto = getCreateFoodTruckDto(category);

        // when
        Long savedId = foodTruckService.createFoodTruck(dto, "ssafy@gmail.com", null);

        // then
        assertThat(savedId).isNotNull();
    }

    @DisplayName("사업자가 아니면 푸드트럭을 생성할 수 없다.")
    @Test
    void createFoodTruckAsClient() throws IOException {
        // given
        Member member = createMember(Role.CLIENT, true, "ssafy@gmail.com");
        Category category = createCategory("고기/구이");

        CreateFoodTruckDto dto = getCreateFoodTruckDto(category);

        // when // then
        assertThatThrownBy(() -> foodTruckService.createFoodTruck(dto, "ssafy@gmail.com", null))
                .isInstanceOf(InValidAccessException.class)
                .hasMessage("잘못된 접근입니다.");
    }

    @DisplayName("사업자는 푸드트럭과 이미지를 함께 등록할 수 있다.")
    @Test
    void createFoodTruckAsVendorWithImage() throws IOException {
        // given
        Member member = createMember(Role.VENDOR, true, "ssafy@gmail.com");
        Category category = createCategory("고기/구이");

        String path = "test.png";
        String contentType = "image/png";
        MockMultipartFile file = new MockMultipartFile("test", path, contentType, "test".getBytes());

        CreateFoodTruckDto dto = getCreateFoodTruckDto(category);

        // when
        Long savedId = foodTruckService.createFoodTruck(dto, "ssafy@gmail.com", file);

        // then
        assertThat(savedId).isNotNull();
    }

    @DisplayName("푸드트럭을 소유한 사업자는 자신의 푸드트럭의 정보를 수정할 수 있다.")
    @Test
    void editFoodTruckAsOwner() throws IOException {
        // given
        Member member = createMember(Role.VENDOR, true, "ssafy@gmail.com");
        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        FoodTruck foodTruck = createFoodTruck(member, category1, true);

        UpdateFoodTruckDto dto = UpdateFoodTruckDto.builder()
                .foodTruckId(foodTruck.getId())
                .categoryId(category2.getId())
                .foodTruckName("동현 삼겹")
                .phoneNumber("010-5678-1234")
                .content("삼겹 구이 & 삼겹 덮밥 전문 푸드트럭")
                .originInfo("돼지고기(중국산), 고추가루(중국산), 참깨(중국산), 양파(중국산), 대파(중국산), 버터(프랑스)")
                .prepareTime(20)
                .waitLimits(5)
                .build();

        // when
        Long result = foodTruckService.editFoodTruck(dto, null, member.getEmail());
        FoodTruck editedFoodTruck = foodTruckRepository.findById(result)
                .orElseThrow();

        // then
        assertThat(editedFoodTruck)
                .extracting("name", "phoneNumber", "content",
                        "originInfo", "prepareTime", "waitLimits")
                .containsExactlyInAnyOrder("동현 삼겹", "010-5678-1234", "삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                        "돼지고기(중국산), 고추가루(중국산), 참깨(중국산), 양파(중국산), 대파(중국산), 버터(프랑스)",
                        20, 5);

    }

    @DisplayName("일반 사용자이거나 푸드트럭의 소유주가 아닌 회원은 해당 푸드트럭을 수정할 수 없다.")
    @Test
    void editFoodTruckAsMember() {
        // given
        Member vendor1 = createMember(Role.VENDOR, true, "ssafy@gmail.com");
        Member vendor2 = createMember(Role.VENDOR, true, "ssafy123@ssafy.com");
        Member client1 = createMember(Role.CLIENT, true, "client@ssafy.com");
        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        FoodTruck foodTruck = createFoodTruck(vendor1, category1, true);

        UpdateFoodTruckDto dto = UpdateFoodTruckDto.builder()
                .foodTruckId(foodTruck.getId())
                .categoryId(category2.getId())
                .foodTruckName("동현 삼겹")
                .phoneNumber("010-5678-1234")
                .content("삼겹 구이 & 삼겹 덮밥 전문 푸드트럭")
                .originInfo("돼지고기(중국산), 고추가루(중국산), 참깨(중국산), 양파(중국산), 대파(중국산), 버터(프랑스)")
                .prepareTime(20)
                .waitLimits(5)
                .build();

        // when // then
        assertThatThrownBy(() -> foodTruckService.editFoodTruck(dto, null, vendor2.getEmail()))
                .isInstanceOf(InValidAccessException.class)
                .hasMessage("잘못된 접근입니다.");

        assertThatThrownBy(() -> foodTruckService.editFoodTruck(dto, null, client1.getEmail()))
                .isInstanceOf(InValidAccessException.class)
                .hasMessage("잘못된 접근입니다.");
    }

    @DisplayName("푸드트럭을 소유한 사업자는 자신의 푸드트럭의 정보와 이미지를 수정할 수 있다.")
    @Test
    void editFoodTruckAsOwnerWithImage() throws IOException {
        // given
        Member member = createMember(Role.VENDOR, true, "ssafy@gmail.com");
        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        FoodTruck foodTruck = createFoodTruck(member, category1, true);

        FoodTruckImage foodTruckImage = FoodTruckImage.builder()
                .foodTruck(foodTruck)
                .uploadFile(UploadFile.builder().storeFileName("test File").uploadFileName("testImg.png").build())
                .active(true)
                .build();
        foodTruckImageRepository.save(foodTruckImage);

        UpdateFoodTruckDto dto = UpdateFoodTruckDto.builder()
                .foodTruckId(foodTruck.getId())
                .categoryId(category2.getId())
                .foodTruckName("동현 삼겹")
                .phoneNumber("010-5678-1234")
                .content("삼겹 구이 & 삼겹 덮밥 전문 푸드트럭")
                .originInfo("돼지고기(중국산), 고추가루(중국산), 참깨(중국산), 양파(중국산), 대파(중국산), 버터(프랑스)")
                .prepareTime(20)
                .waitLimits(5)
                .build();

        String path = "test.png";
        String contentType = "image/png";
        MockMultipartFile file = new MockMultipartFile("test", path, contentType, "test".getBytes());

        // when
        Long result = foodTruckService.editFoodTruck(dto, file, member.getEmail());
        FoodTruck editedFoodTruck = foodTruckRepository.findById(result)
                .orElseThrow();
        FoodTruckImage editedImage =
                foodTruckImageQueryRepository.getFoodTruckImageByFoodTruckId(editedFoodTruck.getId());

        // then
        assertThat(editedFoodTruck)
                .extracting("name", "phoneNumber", "content",
                        "originInfo", "prepareTime", "waitLimits")
                .containsExactlyInAnyOrder("동현 삼겹", "010-5678-1234", "삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                        "돼지고기(중국산), 고추가루(중국산), 참깨(중국산), 양파(중국산), 대파(중국산), 버터(프랑스)",
                        20, 5);

        assertThat(editedImage.getUploadFile()).extracting("uploadFileName")
                .isEqualTo("test.png");
    }

    @DisplayName("푸드트럭을 소유한 사업자는 자신의 푸드트럭의 정보를 수정하고 이미지가 없으면 이미지를 등록할 수 있다.")
    @Test
    void editFoodTruckAsOwnerWithCreateImage() throws IOException {
        // given
        Member member = createMember(Role.VENDOR, true, "ssafy@gmail.com");
        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        FoodTruck foodTruck = createFoodTruck(member, category1, true);

        UpdateFoodTruckDto dto = UpdateFoodTruckDto.builder()
                .foodTruckId(foodTruck.getId())
                .categoryId(category2.getId())
                .foodTruckName("동현 삼겹")
                .phoneNumber("010-5678-1234")
                .content("삼겹 구이 & 삼겹 덮밥 전문 푸드트럭")
                .originInfo("돼지고기(중국산), 고추가루(중국산), 참깨(중국산), 양파(중국산), 대파(중국산), 버터(프랑스)")
                .prepareTime(20)
                .waitLimits(5)
                .build();

        String path = "test.png";
        String contentType = "image/png";
        MockMultipartFile file = new MockMultipartFile("test", path, contentType, "test".getBytes());

        // when
        Long result = foodTruckService.editFoodTruck(dto, file, member.getEmail());
        FoodTruck editedFoodTruck = foodTruckRepository.findById(result)
                .orElseThrow();
        FoodTruckImage editedImage =
                foodTruckImageQueryRepository.getFoodTruckImageByFoodTruckId(editedFoodTruck.getId());

        // then
        assertThat(editedFoodTruck)
                .extracting("name", "phoneNumber", "content",
                        "originInfo", "prepareTime", "waitLimits")
                .containsExactlyInAnyOrder("동현 삼겹", "010-5678-1234", "삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
                        "돼지고기(중국산), 고추가루(중국산), 참깨(중국산), 양파(중국산), 대파(중국산), 버터(프랑스)",
                        20, 5);

        assertThat(editedImage.getUploadFile()).extracting("uploadFileName")
                .isEqualTo("test.png");
    }

    @DisplayName("푸드트럭을 소유한 사업자는 자신의 푸드트럭을 삭제할 수 있다.")
    @Test
    void deleteFoodTruckAsOwner() {
        // given
        Member member = createMember(Role.VENDOR, true, "ssafy@gmail.com");
        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        FoodTruck foodTruck = createFoodTruck(member, category1, true);

        // when
        Long deletedId = foodTruckService.deleteFoodTruck(foodTruck.getId(), member.getEmail());
        FoodTruck deletedFoodTruck = foodTruckRepository.findById(deletedId)
                .orElseThrow();

        // then
        assertThat(deletedFoodTruck).extracting("active")
                .isEqualTo(false);
    }

    @DisplayName("일반 사용자이거나 푸드트럭의 소유주가 아닌 회원은 해당 푸드트럭을 삭제할 수 없다.")
    @Test
    void deleteFoodTruckAsMember() {
        // given
        Member vendor1 = createMember(Role.VENDOR, true, "ssafy@gmail.com");
        Member vendor2 = createMember(Role.VENDOR, true, "ssafy123@ssafy.com");
        Member client1 = createMember(Role.CLIENT, true, "client@ssafy.com");
        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        FoodTruck foodTruck = createFoodTruck(vendor1, category1, true);

        // when // then
        assertThatThrownBy(() -> foodTruckService.deleteFoodTruck(foodTruck.getId(), vendor2.getEmail()))
                .isInstanceOf(InValidAccessException.class)
                .hasMessage("잘못된 접근입니다.");

        assertThatThrownBy(() -> foodTruckService.deleteFoodTruck(foodTruck.getId(), client1.getEmail()))
                .isInstanceOf(InValidAccessException.class)
                .hasMessage("잘못된 접근입니다.");
    }

    @DisplayName("사용자는 푸드트럭 찜을 할 수 있다.")
    @Test
    void createFoodTruckLike() {
        // given
        Member vendor1 = createMember(Role.VENDOR, true, "ssafy@gmail.com");
        Member vendor2 = createMember(Role.VENDOR, true, "ssafy123@ssafy.com");
        Member client1 = createMember(Role.CLIENT, true, "client@ssafy.com");
        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        FoodTruck foodTruck = createFoodTruck(vendor1, category1, true);

        FoodTruckLikeDto dto = FoodTruckLikeDto.builder()
                .foodTruckId(foodTruck.getId())
                .build();

        // when
        FoodTruckLikeResponse response = foodTruckService.foodTruckLike(dto, vendor1.getEmail());
        log.debug("response={}", response);

        // then
        assertThat(response).extracting("foodTruckId", "isLiked")
                .containsExactly(foodTruck.getId(), true);
    }

    @DisplayName("사용자는 푸드트럭 찜을 취소 할 수 있다.")
    @Test
    void cancelFoodTruckLike() {
        // given
        Member vendor1 = createMember(Role.VENDOR, true, "ssafy@gmail.com");
        Member vendor2 = createMember(Role.VENDOR, true, "ssafy123@ssafy.com");
        Member client1 = createMember(Role.CLIENT, true, "client@ssafy.com");
        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        FoodTruck foodTruck = createFoodTruck(vendor1, category1, true);
        createFoodTruckLike(vendor1, foodTruck);
        createFoodTruckLike(vendor2, foodTruck);
        createFoodTruckLike(client1, foodTruck);

        FoodTruckLikeDto dto = FoodTruckLikeDto.builder()
                .foodTruckId(foodTruck.getId())
                .build();

        // when
        FoodTruckLikeResponse response = foodTruckService.foodTruckLike(dto, vendor1.getEmail());
        log.debug("response={}", response);

        // then
        assertThat(response).extracting("foodTruckId", "isLiked")
                .containsExactly(foodTruck.getId(), false);
    }

    @DisplayName("사업자는 선택된 푸드트럭을 수정할 수 있다.")
    @Test
    void editSelected() {
        // given
        Member vendor1 = createMember(Role.VENDOR, true, "ssafy@gmail.com");
        Member vendor2 = createMember(Role.VENDOR, true, "ssafy123@ssafy.com");
        Member client1 = createMember(Role.CLIENT, true, "client@ssafy.com");
        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        FoodTruck selectedFoodTruck = createFoodTruck(vendor1, category1, true);
        FoodTruck foodTruck = createFoodTruck(vendor1, category2, false);

        // when
        Long result = foodTruckService.editSelected(foodTruck.getId(), vendor1.getEmail());
        Optional<FoodTruck> editedFoodTruck = foodTruckRepository.findById(result);

        // then
        assertThat(selectedFoodTruck.getSelected()).isFalse();
        assertThat(editedFoodTruck.isPresent()).isTrue();
        assertThat(editedFoodTruck.get().getSelected()).isTrue();
    }

    @DisplayName("일반 사용자거나 소유주가 아닌 경우 예외가 발생한다.")
    @Test
    void editSelectedWithInValiAccess() {
        // given
        Member vendor1 = createMember(Role.VENDOR, true, "ssafy@gmail.com");
        Member vendor2 = createMember(Role.VENDOR, true, "ssafy123@ssafy.com");
        Member client1 = createMember(Role.CLIENT, true, "client@ssafy.com");
        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        FoodTruck selectedFoodTruck = createFoodTruck(vendor1, category1, true);
        FoodTruck foodTruck = createFoodTruck(vendor1, category2, false);

        // when // then
        assertThatThrownBy(() -> foodTruckService.editSelected(foodTruck.getId(), vendor2.getEmail()))
                .isInstanceOf(InValidAccessException.class)
                .hasMessage("잘못된 접근입니다.");

        assertThatThrownBy(() -> foodTruckService.editSelected(foodTruck.getId(), client1.getEmail()))
                .isInstanceOf(InValidAccessException.class)
                .hasMessage("잘못된 접근입니다.");
    }

    private Member createMember(Role role, boolean active, String email) {
        Member member = Member.builder()
                .email(email)
                .nickname("매미킴")
                .encryptedPwd(passwordEncoder.encode("ssafy1234"))
                .name("김동현")
                .phoneNumber("010-1234-5678")
                .role(role)
                .active(active)
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

    private CreateFoodTruckDto getCreateFoodTruckDto(Category category) {
        return CreateFoodTruckDto.builder()
                .categoryId(category.getId())
                .foodTruckName("동현 된장삼겹")
                .phoneNumber("010-1234-5678")
                .content("된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭")
                .originInfo("돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)")
                .prepareTime(40)
                .waitLimits(10)
                .build();
    }

    private FoodTruck createFoodTruck(Member member, Category category, boolean selected) {
        FoodTruck foodTruck = FoodTruck.builder()
                .vendor(member)
                .category(category)
                .name("동현 된장삼겹")
                .phoneNumber("010-1234-5678")
                .content("된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭")
                .originInfo("돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)")
                .prepareTime(40)
                .waitLimits(10)
                .selected(selected)
                .active(true)
                .build();
        return foodTruckRepository.save(foodTruck);
    }

    private void createFoodTruckLike(Member vendor1, FoodTruck foodTruck) {
        FoodTruckLike foodTruckLike = FoodTruckLike.builder()
                .member(vendor1)
                .foodTruck(foodTruck)
                .active(true)
                .build();
        foodTruckLikeRepository.save(foodTruckLike);
    }
}