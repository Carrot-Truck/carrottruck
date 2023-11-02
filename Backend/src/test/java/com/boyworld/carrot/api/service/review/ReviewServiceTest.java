package com.boyworld.carrot.api.service.review;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.controller.review.request.ReviewRequest;
import com.boyworld.carrot.domain.foodtruck.Category;
import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import com.boyworld.carrot.domain.foodtruck.repository.command.CategoryRepository;
import com.boyworld.carrot.domain.foodtruck.repository.command.FoodTruckRepository;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
import com.boyworld.carrot.domain.order.Order;
import com.boyworld.carrot.domain.order.repository.OrderRepository;
import com.boyworld.carrot.domain.review.Review;
import com.boyworld.carrot.domain.review.ReviewImage;
import com.boyworld.carrot.domain.review.repository.ReviewImageRepository;
import com.boyworld.carrot.domain.review.repository.ReviewRepository;
import com.boyworld.carrot.domain.sale.Sale;
import com.boyworld.carrot.file.S3Uploader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public class ReviewServiceTest extends IntegrationTestSupport {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private FoodTruckRepository foodTruckRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ReviewImageRepository reviewImageRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private S3Uploader s3Uploader;

    @DisplayName("사용자가 리뷰 사진 없이 리뷰를 등록한다.")
    @Test
    void createCommentWithoutImage(){
        //given
        Member member = createMember(Role.CLIENT, true);
        Category category = createCategory();
        FoodTruck foodTruck = createFoodTruck(member, category, "동현 된장삼겹", "010-1234-5678",
            "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
            "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
            40,
            10,
            true);
        Order order = createOrder(member, foodTruck);

        Review review = createReview(member, order, foodTruck);

        // when
        Boolean result = reviewService.createReview(ReviewRequest.builder()
                .orderId(order.getId())
                .foodTruckId(foodTruck.getId())
                .grade(review.getGrade())
                .content(review.getContent())
                .memberId(review.getMember().getId())
                .build());

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("사용자가 리뷰 사진과 함께 리뷰를 등록한다.")
    @Test
    void createCommentWithImage(){

    }

    @DisplayName("사업자는 리뷰에 댓글을 남길 수 있다.")
    @Test
    void createComment(){

    }

    @DisplayName("사용자는 리뷰에 댓글을 남길 수 있다.")
    @Test
    void createCommentAsClient(){

    }

    @DisplayName("사업자는 내 푸드트럭이 아닌 리뷰에 댓글을 남길 수 없다.")
    @Test
    void createCommentAtAnotherFoodTruck(){

    }

    @DisplayName("사용자는 내가 작성한 리뷰 목록을 확인할 수 있다.")
    @Test
    void getMyReview(){

    }

    @DisplayName("사용자는 삭제한 리뷰를 볼 수 없다.")
    @Test
    void getDeletedMyReview(){

    }

    @DisplayName("사용자는 내가 작성한 리뷰가 없을때는 빈 목록을 확인할 수 있다.")
    @Test
    void getMyEmptyReview(){

    }

    @DisplayName("사용자는 특정 푸드트럭의 리뷰 목록을 조회할 수 있다.")
    @Test
    void getFoodTruckReview(){

    }

    @DisplayName("사용자는 특정 푸드트럭의 빈 리뷰 목록을 조회할 수 있다.")
    @Test
    void getEmptyFoodTruckReview(){

    }

    @DisplayName("사용자는 리뷰를 삭제할 수 있다.")
    @Test
    void withdrawal(){

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

    private Review createReview(Member member, Order order, FoodTruck foodTruck){
        return Review.builder()
            .grade((int)(Math.random()*10)%6)
            .content("다음에 또 올게요~!")
            .order(order)
            .foodTruck(foodTruck)
            .active(true)
            .member(member)
            .build();
    }

    private Review createReviewWithImage(Member member, Order order, FoodTruck foodTruck, MultipartFile image) throws Exception{
        Review review =  reviewRepository.save(Review.builder()
            .grade((int)(Math.random()*10)%6)
            .content("다음에 또 올게요~!")
            .order(order)
            .foodTruck(foodTruck)
            .active(true)
            .member(member)
            .build());

        String uploadFileUrl = s3Uploader.uploadFiles(image, "review");

        ReviewImage reviewImage = ReviewImage.builder()
            .review(review)
            .saveFileName(uploadFileUrl)
            .uploadFileName(image.getOriginalFilename())
            .active(true)
            .build();
        reviewImageRepository.save(reviewImage);

        return review;
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

    private Category createCategory() {
        Category category = Category.builder()
            .name("고기/구이")
            .active(true)
            .build();
        return categoryRepository.save(category);
    }

    private Order createOrder(Member member, FoodTruck foodTruck){
        return orderRepository.save(Order.builder()
            .member(member)
            .sale(Sale.builder()
                .startTime(LocalDateTime.of(2023, 11, 2, 10, 0))
                .active(true)
                .longitude(BigDecimal.valueOf(36.33))
                .latitude(BigDecimal.valueOf(130.21))
                .foodTruck(foodTruck)
                .build())
            .build());
    }

}
