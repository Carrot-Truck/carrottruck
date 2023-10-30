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
import com.boyworld.carrot.domain.member.repository.MemberRepository;
import com.boyworld.carrot.domain.review.Review;
import com.boyworld.carrot.domain.review.repository.ReviewRepository;
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
    private final MemberRepository memberRepository;
    private final FoodTruckRepository foodTruckRepository;

    /*
     *  write review API
     * @param request
     * @return boolean
     */
    public Boolean createReview(ReviewRequest request) {
        try {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*
     *  write comment API
     * @param request
     * @return boolean
     */
    public Boolean createComment(CommentRequest request) {
        try {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*
     * read my review-list API
     *
     */
    public MyReviewResponse getMyReview(String userEmail) {
        try {
            Member member = memberRepository.findByEmail(userEmail).orElseThrow();
            List<Review> myReview = reviewRepository.findByMember(member).orElseThrow();
            return MyReviewResponse.of(myReview.stream().map(MyReviewDto::of).collect(Collectors.toList()));
        } catch (Exception e) {
            return null;
        }
    }

    /*
     * read food truck's review-list API
     */
    public FoodTruckReviewResponse getFoodTruckReview(Long foodTruckId) {
        try {
            FoodTruck foodTruck = foodTruckRepository.findById(foodTruckId).orElseThrow();
            List<Review> list = reviewRepository.findByFoodTruck(foodTruck).orElseThrow();
            return FoodTruckReviewResponse.of(list.stream().map(FoodTruckReviewDto::of).collect(Collectors.toList()));
        } catch (Exception e) {
            return null;
        }
    }

    /*
     * delete my review API
     */
    public Boolean withdrawal(String email, Long reviewId) {
        if(email.equals(SecurityUtil.getCurrentLoginId())){
            return true;
        } else {
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
