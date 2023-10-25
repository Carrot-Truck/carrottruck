package com.boyworld.carrot.api.controller.review;

import com.boyworld.carrot.api.ApiResponse;
import com.boyworld.carrot.api.controller.review.request.ReviewRequest;
import com.boyworld.carrot.api.service.review.ReviewService;
import com.boyworld.carrot.domain.review.Review;
import com.boyworld.carrot.security.SecurityUtil;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ApiResponse<Boolean> writeReview(@Valid @RequestBody ReviewRequest request) {
        log.debug("ReviewController#writeReview called !!!");
        log.debug("review= {}", request);

        return ApiResponse.created(reviewService.writeReview(request));
    }

    /*
     * read my review-list API
     */
    @GetMapping
    public ApiResponse<List<Review>> getMyReview() {
        log.debug("ReviewController#getMyReview called !!!");

        String userEmail = SecurityUtil.getCurrentLoginId();
        log.debug("id= {}", userEmail);

        return ApiResponse.found(reviewService.getMyReview(userEmail));
    }

    /*
     * delete my review API
     */

    /*
     * read food truck's review-list API
     *
     */

}
