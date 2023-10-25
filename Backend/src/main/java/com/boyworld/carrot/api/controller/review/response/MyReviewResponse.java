package com.boyworld.carrot.api.controller.review.response;

import com.boyworld.carrot.domain.review.Review;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MyReviewResponse {

    private List<Review> myReviewList;

}