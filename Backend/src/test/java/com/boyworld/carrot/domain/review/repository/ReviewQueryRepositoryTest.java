package com.boyworld.carrot.domain.review.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.domain.foodtruck.Category;
import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import com.boyworld.carrot.domain.foodtruck.repository.command.CategoryRepository;
import com.boyworld.carrot.domain.foodtruck.repository.command.FoodTruckRepository;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
import com.boyworld.carrot.domain.order.Order;
import com.boyworld.carrot.domain.order.Status;
import com.boyworld.carrot.domain.order.repository.command.OrderRepository;
import com.boyworld.carrot.domain.review.Comment;
import com.boyworld.carrot.domain.review.Review;
import com.boyworld.carrot.domain.review.ReviewImage;
import com.boyworld.carrot.domain.sale.Sale;
import com.boyworld.carrot.domain.sale.repository.command.SaleRepository;
import com.boyworld.carrot.file.S3Uploader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public class ReviewQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewImageRepository reviewImageRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private FoodTruckRepository foodTruckRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private S3Uploader s3Uploader;


    @DisplayName("사용자가 리뷰 사진 없이 리뷰를 등록한다.")
    @Test
    void createCommentWithoutImage() {
        //given
        Member member = createMember(Role.CLIENT, "ssafy@ssafy.com");
        Member vendor = createMember(Role.VENDOR, "vendor@ssafy.com");
        Category category = createCategory();
        FoodTruck foodTruck = createFoodTruck(vendor, category, "동현 된장삼겹", "010-1234-5678",
            "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
            "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
            40,
            10
        );
        Sale sale = createSale(foodTruck);
        Order order = createOrder(member, sale);
        Review review = createReviewEntity(member, order, foodTruck);

        // when
        Review savedReview = reviewRepository.save(review);

        // then
        assertThat(review).isSameAs(savedReview);
        assertThat(review.getContent()).isEqualTo(savedReview.getContent());
        assertThat(review.getGrade()).isEqualTo(savedReview.getGrade());
        assertThat(savedReview.getId()).isNotNull();
        assertThat(reviewRepository.count()).isEqualTo(1);
    }

    @DisplayName("사용자가 리뷰 사진과 함께 리뷰를 등록한다.")
    @Test
    void createCommentWithImage() throws Exception {
        //given
        Member member = createMember(Role.CLIENT, "ssafy@ssafy.com");
        Member vendor = createMember(Role.VENDOR, "vendor@ssafy.com");
        Category category = createCategory();
        FoodTruck foodTruck = createFoodTruck(vendor, category, "동현 된장삼겹", "010-1234-5678",
            "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
            "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
            40,
            10
        );
        Sale sale = createSale(foodTruck);
        Order order = createOrder(member, sale);
        MultipartFile image = new MockMultipartFile("test1", "test1.PNG", MediaType.IMAGE_PNG_VALUE,
            "test1".getBytes());
        Review review = createReviewEntity(member, order, foodTruck);
        String uploadFileUrl = s3Uploader.uploadFiles(image, "review");
        ReviewImage reviewImage = ReviewImage.builder()
            .review(review)
            .storeFileName(uploadFileUrl)
            .uploadFileName(image.getOriginalFilename())
            .active(true)
            .build();

        // when
        Review savedReview = reviewRepository.save(review);
        ReviewImage savedReviewImage = reviewImageRepository.save(reviewImage);

        // then
        assertThat(review).isSameAs(savedReview);
        assertThat(review.getContent()).isEqualTo(savedReview.getContent());
        assertThat(review.getGrade()).isEqualTo(savedReview.getGrade());
        assertThat(savedReview.getId()).isNotNull();
        assertThat(reviewRepository.count()).isEqualTo(1);

        assertThat(reviewImage).isSameAs(savedReviewImage);
        assertThat(reviewImage.getStoreFileName()).isEqualTo(savedReviewImage.getStoreFileName());
        assertThat(reviewImage.getUploadFileName()).isEqualTo(savedReviewImage.getUploadFileName());
        assertThat(savedReviewImage.getId()).isNotNull();
        assertThat(reviewImageRepository.count()).isEqualTo(1);
    }

    @DisplayName("사업자는 리뷰에 댓글을 남길 수 있다.")
    @Test
    @WithMockUser(roles = "VENDOR")
    void createComment() {
        // given
        Member member = createMember(Role.CLIENT, "ssafy@ssafy.com");
        Member vendor = createMember(Role.VENDOR, "vendor@ssafy.com");
        Category category = createCategory();
        FoodTruck foodTruck = createFoodTruck(vendor, category, "동현 된장삼겹2", "010-1231-5678",
            "소고기(국산), 고추가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
            "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭 시즌 2",
            30,
            15
        );
        Sale sale = createSale(foodTruck);
        Order order = createOrder(member, sale);
        Review review = createReviewEntity(member, order, foodTruck);
        reviewRepository.save(review);

        Comment comment = Comment.builder()
            .review(review)
            .content("리뷰 감사합니다.")
            .vendor(vendor)
            .active(true)
            .build();

        // when
        Comment savedComment = commentRepository.save(comment);

        // then
        assertThat(comment).isSameAs(savedComment);
        assertThat(comment.getContent()).isEqualTo(savedComment.getContent());
        assertThat(comment.getId()).isNotNull();
        assertThat(commentRepository.count()).isEqualTo(1);
    }

    @DisplayName("사용자는 내가 작성한 리뷰 목록을 확인할 수 있다.")
    @Test
    void getMyReview(){
        // given
        Member member = createMember(Role.CLIENT, "ssafy@ssafy.com");
        Member vendor = createMember(Role.VENDOR, "vendor@ssafy.com");
        Category category = createCategory();
        FoodTruck foodTruck = createFoodTruck(vendor, category, "동현 된장삼겹", "010-1234-5678",
            "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
            "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
            40,
            10);
        Sale sale = createSale(foodTruck);
        Order order = createOrder(member, sale);
        reviewRepository.save(createReviewEntity(member, order, foodTruck));
        reviewRepository.save(createReviewEntity(member, order, foodTruck));

        // when
        List<Review> myReview = reviewRepository.findByMemberAndActive(member, true).orElseThrow();

        // then
        assertThat(myReview).isNotNull();
        assertThat(myReview.size())isNotZero();
    }

    @DisplayName("사용자는 특정 푸드트럭의 리뷰 목록을 조회할 수 있다.")
    @Test
    void getFoodTruckReview(){
        // given
        Member member = createMember(Role.CLIENT, "ssafy@ssafy.com");
        Member vendor = createMember(Role.VENDOR, "vendor@ssafy.com");
        Category category = createCategory();
        FoodTruck foodTruck = createFoodTruck(vendor, category, "동현 된장삼겹", "010-1234-5678",
            "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
            "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
            40,
            10);
        Sale sale = createSale(foodTruck);
        Order order = createOrder(member, sale);
        reviewRepository.save(createReviewEntity(member, order, foodTruck));
        reviewRepository.save(createReviewEntity(member, order, foodTruck));

        // when
        List<Review> items = reviewRepository.findByFoodTruckAndActive(foodTruck, true).orElseThrow();

        // then
        assertThat(items).isNotNull();
        assertThat(items.size())isNotZero();
    }

    @DisplayName("사용자는 리뷰를 삭제할 수 있다.")
    @Test
    @Transactional
    void withdrawal(){
        // given
        Member member = createMember(Role.CLIENT, "ssafy@ssafy.com");
        Member vendor = createMember(Role.VENDOR, "vendor@ssafy.com");
        Category category = createCategory();
        FoodTruck foodTruck = createFoodTruck(vendor, category, "동현 된장삼겹", "010-1234-5678",
            "돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)",
            "된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭",
            40,
            10);
        Sale sale = createSale(foodTruck);
        Order order = createOrder(member, sale);
        Long reviewId = reviewRepository.save(createReviewEntity(member, order, foodTruck)).getId();

        // when
        Review review = reviewRepository.findById(reviewId).orElseThrow();
        review.setActive(false);
        reviewRepository.save(review);
        Review deletedReview = reviewRepository.findById(reviewId).orElseThrow();

        // then
        assertThat(reviewRepository.count()).isEqualTo(1);
        assertThat(reviewRepository.findByMemberAndActive(member, true).isEmpty()).isFalse();
        assertThat(reviewRepository.findByMemberAndActive(member, true).get().size()).isEqualTo(0);
        assertThat(deletedReview.getActive()).isFalse();
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


    private Review createReviewEntity(Member member, Order order, FoodTruck foodTruck) {
        return Review.builder()
            .grade((int) (Math.random() * 10) % 6)
            .content("다음에 또 올게요~!")
            .order(order)
            .foodTruck(foodTruck)
            .active(true)
            .member(member)
            .build();
    }

    private FoodTruck createFoodTruck(Member member, Category category, String name,
        String phoneNumber,
        String content, String originInfo, Integer prepareTime,
        Integer waitLimits) {
        FoodTruck foodTruck = FoodTruck.builder()
            .vendor(member)
            .category(category)
            .name(name)
            .phoneNumber(phoneNumber)
            .content(content)
            .originInfo(originInfo)
            .prepareTime(prepareTime)
            .waitLimits(waitLimits)
            .selected(true)
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

    private Order createOrder(Member member, Sale sale) {
        return orderRepository.save(Order.builder()
            .member(member)
            .sale(sale)
            .totalPrice(10000)
            .active(true)
            .status(Status.PENDING)
            .expectTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 30)))
            .build());
    }

    private Sale createSale(FoodTruck foodTruck) {
        return saleRepository.save(Sale.builder()
            .address("my address")
            .orderable(true)
            .startTime(LocalDateTime.of(2023, 11, 2, 10, 0))
            .active(true)
            .longitude(BigDecimal.valueOf(130.21))
            .latitude(BigDecimal.valueOf(36.33))
            .foodTruck(foodTruck)
            .orderNumber(1)
            .totalAmount(10000)
            .build());
    }
}
