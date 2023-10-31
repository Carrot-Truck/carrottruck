package com.boyworld.carrot.api.service.review;

import com.boyworld.carrot.api.controller.review.request.CommentRequest;
import com.boyworld.carrot.api.controller.review.request.ReviewRequest;
import com.boyworld.carrot.api.controller.review.response.FoodTruckReviewResponse;
import com.boyworld.carrot.api.controller.review.response.MyReviewResponse;
import com.boyworld.carrot.api.service.review.dto.FoodTruckReviewDto;
import com.boyworld.carrot.api.service.review.dto.MyReviewDto;
import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import com.boyworld.carrot.domain.foodtruck.repository.FoodTruckRepository;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.order.Order;
import com.boyworld.carrot.domain.order.repository.OrderRepository;
import com.boyworld.carrot.domain.review.Comment;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
import com.boyworld.carrot.domain.review.Review;
import com.boyworld.carrot.domain.review.ReviewImage;
import com.boyworld.carrot.domain.review.repository.CommentRepository;
import com.boyworld.carrot.domain.review.repository.ReviewImageRepository;
import com.boyworld.carrot.domain.review.repository.ReviewRepository;
import com.boyworld.carrot.file.S3Uploader;
import com.boyworld.carrot.security.SecurityUtil;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Review Service
 *
 * @author Gunhoo Park
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final FoodTruckRepository foodTruckRepository;
    private final OrderRepository orderRepository;
    private final S3Uploader s3Uploader;

    /**
     *  write review API
     * @param request memberId, orderId, foodTruckId, content, grade, image
     * @return boolean
     */
    public Boolean createReview(ReviewRequest request) {
        try {
            Member member = memberRepository.findById(request.getMemberId()).orElseThrow();
            Order order = orderRepository.findById(request.getOrderId()).orElseThrow();
            FoodTruck foodTruck = foodTruckRepository.findById(request.getFoodTruckId()).orElseThrow();
            Review review = Review.builder()
                .grade(request.getGrade())
                .member(member)
                .order(order)
                .foodTruck(foodTruck)
                .content(request.getContent())
                .active(true)
                .build();
            // Save review
            Review savedReview = reviewRepository.save(review);

            // Image upload at AWS S3
            String uploadFileUrl = s3Uploader.uploadFiles(request.getImage(), savedReview.getId(), "review");
            // Save info at ReviewImage
            ReviewImage reviewImage = ReviewImage.builder()
                    .review(review)
                    .saveFileName(uploadFileUrl)
                    .uploadFileName(request.getImage().getOriginalFilename())
                    .active(true)
                    .build();
            reviewImageRepository.save(reviewImage);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     *  write comment API
     * @param request reviewId, comment
     * @return boolean
     */
    public Boolean createComment(CommentRequest request) {
        try {
            String email = SecurityUtil.getCurrentLoginId();
            // 만약 해당 리뷰가 달린 푸드트럭의 사업자의 email 과 답글을 남기려는 사업자의 이메일이 같지 않으면
            if(!reviewRepository.findById(request.getReviewId()).orElseThrow().getFoodTruck().getVendor().getEmail().equals(email)){
                return false;
            }
            // 답글 저장
            Comment comment = Comment.builder()
                .review(reviewRepository.findById(request.getReviewId()).orElseThrow())
                .content(request.getComment())
                .vendor(memberRepository.findByEmail(email).orElseThrow())
                .active(true)
                .build();
            commentRepository.save(comment);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * read my review-list API
     * from SecurityUtil.getCurrentLoginId()
     */
    public MyReviewResponse getMyReview() {
        try {
            String userEmail = SecurityUtil.getCurrentLoginId();
            Member member = memberRepository.findByEmail(userEmail).orElseThrow();
            List<Review> myReview = reviewRepository.findByMemberAndActive(member, true).orElseThrow();
            return MyReviewResponse.of(
                myReview.stream().map(MyReviewDto::of).collect(Collectors.toList()));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * read food truck's review-list API
     * from footTruckId, get list of reviews
     */
    public FoodTruckReviewResponse getFoodTruckReview(Long foodTruckId) {
        try {
            FoodTruck foodTruck = foodTruckRepository.findById(foodTruckId).orElseThrow();
            List<Review> list = reviewRepository.findByFoodTruckAndActive(foodTruck, true).orElseThrow();
            return FoodTruckReviewResponse.of(
                list.stream().map(FoodTruckReviewDto::of).collect(Collectors.toList()));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * delete my review API
     */
    public Boolean withdrawal(Long reviewId) {
        try{
            String email = SecurityUtil.getCurrentLoginId();
            Review review = reviewRepository.findById(reviewId).orElseThrow();
            // 만약 삭제하고자 하는 댓글의 email과 현재 로그인한 email이 동일하다면
            if(review.getMember().getEmail().equals(email)){
                review.setActive(false); // 비활성화
            } else {
                return false;
            }
            return true;
        } catch (Exception e){
            return false;
        }

    }

    /*
     * report review API
     */
    public Boolean report(Long reviewId, String content) {
        return true;
    }
}
