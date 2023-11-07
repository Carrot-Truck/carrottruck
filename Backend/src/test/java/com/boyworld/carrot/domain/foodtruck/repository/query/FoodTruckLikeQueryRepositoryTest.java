package com.boyworld.carrot.domain.foodtruck.repository.query;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.domain.foodtruck.Category;
import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import com.boyworld.carrot.domain.foodtruck.FoodTruckLike;
import com.boyworld.carrot.domain.foodtruck.repository.command.CategoryRepository;
import com.boyworld.carrot.domain.foodtruck.repository.command.FoodTruckLikeRepository;
import com.boyworld.carrot.domain.foodtruck.repository.command.FoodTruckRepository;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 푸드트럭 찜 조회 레포지토리
 *
 * @author 최영환
 */
@Slf4j
class FoodTruckLikeQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private FoodTruckLikeQueryRepository foodTruckLikeQueryRepository;

    @Autowired
    private FoodTruckLikeRepository foodTruckLikeRepository;

    @Autowired
    private FoodTruckRepository foodTruckRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @DisplayName("푸드트럭 식별키와 회원 식별키로 푸드트럭 찜을 조회한다.")
    @Test
    void getFoodTruckLikeByMemberIdAndFoodTruckId() {
        // given
        Member vendor1 = createMember(Role.VENDOR, true, "ssafy@ssafy.com");
        Member vendor2 = createMember(Role.VENDOR, true, "ssafy123@ssafy.com");
        Member client1 = createMember(Role.CLIENT, true, "client@ssafy.com");
        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        FoodTruck foodTruck = createFoodTruck(vendor1, category1);
        createFoodTruckLike(vendor1, foodTruck);
        createFoodTruckLike(vendor2, foodTruck);
        createFoodTruckLike(client1, foodTruck);

        // when
        FoodTruckLike foodTruckLike = foodTruckLikeQueryRepository
                .getFoodTruckLikeByMemberIdAndFoodTruckId(vendor1.getId(), foodTruck.getId());

        // then
        assertThat(foodTruckLike).extracting("member", "foodTruck")
                .containsExactly(vendor1, foodTruck);
    }

    @DisplayName("푸드트럭 식별키와 회원 식별키에 해당하는 찜이 없으면 null 이 반환된다.")
    @Test
    void getEmptyFoodTruckLikeByMemberIdAndFoodTruckId() {
        // given
        Member vendor1 = createMember(Role.VENDOR, true, "ssafy@ssafy.com");
        Member vendor2 = createMember(Role.VENDOR, true, "ssafy123@ssafy.com");
        Member client1 = createMember(Role.CLIENT, true, "client@ssafy.com");
        Category category1 = createCategory("고기/구이");
        Category category2 = createCategory("분식");

        FoodTruck foodTruck = createFoodTruck(vendor1, category1);

        // when
        FoodTruckLike foodTruckLike = foodTruckLikeQueryRepository
                .getFoodTruckLikeByMemberIdAndFoodTruckId(vendor1.getId(), foodTruck.getId());

        // then
        assertThat(foodTruckLike).isNull();
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

    private FoodTruck createFoodTruck(Member member, Category category) {
        FoodTruck foodTruck = FoodTruck.builder()
                .vendor(member)
                .category(category)
                .name("동현 된장삼겹")
                .phoneNumber("010-1234-5678")
                .content("된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭")
                .originInfo("돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)")
                .prepareTime(40)
                .waitLimits(10)
                .selected(true)
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