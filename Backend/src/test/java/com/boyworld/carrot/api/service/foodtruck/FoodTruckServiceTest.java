package com.boyworld.carrot.api.service.foodtruck;


import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.service.foodtruck.dto.CreateFoodTruckDto;
import com.boyworld.carrot.api.service.member.error.InvalidAccessException;
import com.boyworld.carrot.domain.foodtruck.Category;
import com.boyworld.carrot.domain.foodtruck.repository.command.CategoryRepository;
import com.boyworld.carrot.domain.foodtruck.repository.command.FoodTruckImageRepository;
import com.boyworld.carrot.domain.foodtruck.repository.command.FoodTruckRepository;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
import com.boyworld.carrot.file.S3Uploader;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;

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
    private S3Uploader s3Uploader;

    @DisplayName("사업자는 푸드트럭을 이미지 없이 생성할 수 있다.")
    @Test
    void createFoodTruckAsVendorWithoutImage() throws IOException {
        // given
        Member member = createMember(Role.VENDOR, true);
        Category category = createCategory();

        CreateFoodTruckDto dto = getCreateFoodTruckDto(category);

        // when
        Long savedId = foodTruckService.createFoodTruck(dto, "ssafy@ssafy.com", null);

        // then
        assertThat(savedId).isNotNull();
    }

    @DisplayName("사업자가 아니면 푸드트럭을 생성할 수 없다.")
    @Test
    void createFoodTruckAsClient() throws IOException {
        // given
        Member member = createMember(Role.CLIENT, true);
        Category category = createCategory();

        CreateFoodTruckDto dto = getCreateFoodTruckDto(category);

        // when // then
        assertThatThrownBy(() -> foodTruckService.createFoodTruck(dto, "ssafy@ssafy.com", null))
                .isInstanceOf(InvalidAccessException.class)
                .hasMessage("잘못된 접근입니다.");
    }

    @DisplayName("사업자는 푸드트럭과 이미지를 함께 등록할 수 있다.")
    @Test
    void createFoodTruckAsVendorWithImage() throws IOException {
        // given
        Member member = createMember(Role.VENDOR, true);
        Category category = createCategory();
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "image.jpg",
                MediaType.IMAGE_JPEG_VALUE, "image data".getBytes());

        CreateFoodTruckDto dto = getCreateFoodTruckDto(category);

        // when
        Long savedId = foodTruckService.createFoodTruck(dto, "ssafy@ssafy.com", file);

        // then
        assertThat(savedId).isNotNull();
        assertThat(foodTruckImageRepository.findAll()).hasSize(1);
    }

    private Member createMember(Role role, boolean active) {
        Member member = Member.builder()
                .email("ssafy@ssafy.com")
                .nickname("매미킴")
                .encryptedPwd(passwordEncoder.encode("ssafy1234"))
                .name("김동현")
                .phoneNumber("010-1234-5678")
                .role(role)
                .active(active)
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
}