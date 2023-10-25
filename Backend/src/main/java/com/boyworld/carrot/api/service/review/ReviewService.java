package com.boyworld.carrot.api.service.review;

import com.boyworld.carrot.api.controller.review.request.ReviewRequest;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.repository.MemberRepository;
import com.boyworld.carrot.domain.review.Review;
import com.boyworld.carrot.domain.review.repository.ReviewRepository;
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
    /*
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

    /*
     * read my review-list API
     *
     */
    public List<Review> getMyReview(String userEmail){
        try {
            Member member = memberRepository.findByEmail(userEmail).orElseThrow();
            return reviewRepository.findByMember(member).orElseThrow();
        } catch (Exception e){
            return null;
        }
    }

    /*
     * delete my review API
     */

    /*
     * read food truck's review-list API
     *
     */

}
