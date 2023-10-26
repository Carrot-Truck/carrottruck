package com.boyworld.carrot.api.service.review;

import com.boyworld.carrot.api.controller.review.request.ReviewRequest;
import com.boyworld.carrot.api.controller.review.response.FoodTruckReviewResponse;
import com.boyworld.carrot.api.controller.review.response.MyReviewResponse;
import com.boyworld.carrot.api.service.review.dto.MyReviewDto;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.repository.MemberRepository;
import com.boyworld.carrot.domain.review.Review;
import com.boyworld.carrot.domain.review.repository.ReviewRepository;
import java.util.ArrayList;
import java.util.List;
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
//    private final FoodTruckRepository foodTruckRepository;

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
     * read my review-list API
     *
     */
    public MyReviewResponse getMyReview(String userEmail) {
        try {
            Member member = memberRepository.findByEmail(userEmail).orElseThrow();
            List<Review> myReview = reviewRepository.findByMember(member).orElseThrow();
            List<MyReviewDto> myReviewDtoList = new ArrayList<>();
            myReview.forEach(review -> {
                myReviewDtoList.add(MyReviewDto.builder()
                    .id(review.getId())
                    .createdDate(review.getCreatedDate())
                    .foodTruck(review.getFoodTruck())
                    .grade(review.getGrade())
                    .content(review.getContent())
                    .build());
            });
            return MyReviewResponse.builder().myReviewDtoList(myReviewDtoList).build();
        } catch (Exception e) {
            return null;
        }
    }

    /*
     * read food truck's review-list API
     */
    public FoodTruckReviewResponse getFoodTruckReview(Long foodTruckId) {
        try {
//            FoodTruck foodTruck = foodTruckRepository.findById(foodTruckId);
//            return reviewRepository.findByFoodTruck(foodTruck).orElseThrow();
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /*
     * delete my review API
     */
    public Boolean withdrawal(String email, String password, Long foodTruckId) {
        return true;
    }
}
