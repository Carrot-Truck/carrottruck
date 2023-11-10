package com.boyworld.carrot.api.controller.review;

import com.boyworld.carrot.api.ApiResponse;
import com.boyworld.carrot.api.controller.review.request.CommentRequest;
import com.boyworld.carrot.api.controller.review.request.ReportRequest;
import com.boyworld.carrot.api.controller.review.request.WithdrawalRequest;
import com.boyworld.carrot.api.controller.review.request.ReviewRequest;
import com.boyworld.carrot.api.controller.review.response.FoodTruckReviewResponse;
import com.boyworld.carrot.api.controller.review.response.MyReviewResponse;
import com.boyworld.carrot.api.service.review.ReviewService;
import com.boyworld.carrot.security.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Review Controller - Review CRD(Create/Read/Delete), Report and Comment
 *
 * @author Gunhoo Park
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    /**
     *  create review API
     * @param request memberId, foodTruckId, orderId, content, grade(max 5)
     * @return review 등록 여부
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Boolean> createReview(@Valid @ModelAttribute ReviewRequest request) {
        log.debug("ReviewController#createReview called !!!");
        log.debug("review= {}", request);

        return ApiResponse.created(reviewService.createReview(request));
    }

    /**
     *  create review comment API
     * @param request reviewId, comment
     * @return review comment 등록 여부
     */
    @PostMapping("/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Boolean> createComment(@Valid @RequestBody CommentRequest request) {
        log.debug("ReviewController#createComment called !!!");
        log.debug("review= {}", request);

        return ApiResponse.created(reviewService.createComment(request, SecurityUtil.getCurrentLoginId()));
    }

    /**
     * read my review-list API
     * @return MyReviewResponse : List of MyReviewDto(reviewId, foodTruck, grade, createdDate, content)
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<MyReviewResponse> getMyReview() {
        log.debug("ReviewController#getMyReview called !!!");

        MyReviewResponse response = reviewService.getMyReview(SecurityUtil.getCurrentLoginId());
        return ApiResponse.ok(response);
    }

    /**
     * read food truck's review-list API
     * @param foodTruckId foodTruckId
     * @return FoodTruckReviewResponse : averageGrade and List of FoodTruckReviewDto(reviewId, grade, content)
     */
    @GetMapping("/{foodTruckId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<FoodTruckReviewResponse> getFoodTruckReview(@Valid @PathVariable Long foodTruckId){
        log.debug("ReviewController#getFoodTruckReview called! Food truck id = {}", foodTruckId);
        FoodTruckReviewResponse response = reviewService.getFoodTruckReview(foodTruckId);
        return ApiResponse.ok(response);
    }

    /**
     * delete my review API
     * @param request email, password, reviewId
     * @return Boolean
     */
    @PutMapping("/withdrawal")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Boolean> withdrawal(@Valid @RequestBody WithdrawalRequest request) {
        log.debug("ReviewController#withdrawal called !!!");
        log.debug("WithdrawalRequest={}", request);

        Boolean result = reviewService.withdrawal(request.getReviewId(), SecurityUtil.getCurrentLoginId());
        log.debug("result={}", result);

        return ApiResponse.ok(true);
    }

    /**
     * report review API
     * @param request reviewId, content
     * @return Boolean
     */
    @PostMapping("/report")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Boolean> report(@Valid @RequestBody ReportRequest request){
        log.debug("ReviewController#report called !!!");
        log.debug("ReportRequest={}", request);

        Boolean result = reviewService.report(request.getReviewId(), request.getContent());
        log.debug("result={}", result);

        return ApiResponse.ok(result);
    }
}
