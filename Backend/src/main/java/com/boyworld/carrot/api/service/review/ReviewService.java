package com.boyworld.carrot.api.service.review;

import com.boyworld.carrot.api.controller.review.request.ReviewRequest;
import com.boyworld.carrot.domain.review.repository.ReviewRepository;
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

    /**
     *  write review API
     * @param request
     * @return boolean
     */
    public Boolean writeReview(ReviewRequest request){
        try {
            return true;
        } catch (Exception e){
            return false;
        }
    }

    /**
     * read my review-list API
     *
     */

    /**
     * delete my review API
     */

    /**
     * read food truck's review-list API
     *
     */

}
