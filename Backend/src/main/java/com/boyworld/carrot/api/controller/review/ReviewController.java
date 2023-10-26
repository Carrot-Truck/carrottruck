package com.boyworld.carrot.api.controller.review;

import com.boyworld.carrot.api.ApiResponse;
import com.boyworld.carrot.api.controller.review.request.ReviewRequest;
import com.boyworld.carrot.api.controller.review.response.FoodTruckReviewResponse;
import com.boyworld.carrot.api.service.review.ReviewService;
import com.boyworld.carrot.api.controller.review.response.MyReviewResponse;
import com.boyworld.carrot.api.service.review.dto.FoodTruckReviewDto;
import com.boyworld.carrot.domain.review.Review;
import com.boyworld.carrot.security.SecurityUtil;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Review Controller - Create Review - Read Review - Update Review - Delete Review
 *
 * @author Gunhoo Park
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    /*
     *  write review API
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Boolean> createReview(@Valid @RequestBody ReviewRequest request) {
        log.debug("ReviewController#createReview called !!!");
        log.debug("review= {}", request);

        return ApiResponse.created(reviewService.createReview(request));
    }

    /*
     * read my review-list API
     */
    @GetMapping
    public ApiResponse<MyReviewResponse> getMyReview() {
        log.debug("ReviewController#getMyReview called !!!");

        String userEmail = SecurityUtil.getCurrentLoginId();
        log.debug("id= {}", userEmail);
        MyReviewResponse response = reviewService.getMyReview(userEmail);
        return ApiResponse.found(response);
    }

    /*
     * read food truck's review-list API
     */
    @GetMapping("/{foodTruckId}")
    public ApiResponse<FoodTruckReviewResponse> getFoodTruckReview(@Valid @PathVariable Long foodTruckId){
        log.debug("ReviewController#getFoodTruckReview called! Food truck id = {}", foodTruckId);
        FoodTruckReviewResponse response = reviewService.getFoodTruckReview(foodTruckId);
        return ApiResponse.found(response);
    }

    /*
     * delete my review API
     */

}
