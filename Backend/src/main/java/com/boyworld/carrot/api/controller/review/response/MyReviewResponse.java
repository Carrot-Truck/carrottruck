package com.boyworld.carrot.api.controller.review.response;

import com.boyworld.carrot.api.service.review.dto.MyReviewDto;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * To show Review entity
 *
 * @author Gunhoo Park
 */
@Data
public class MyReviewResponse {

    private List<MyReviewDto> myReviewDtoList;

    @Builder
    private MyReviewResponse(List<MyReviewDto> myReviewDtoList){
        this.myReviewDtoList = myReviewDtoList;
    }

    public static MyReviewResponse of(List<MyReviewDto> myReviewDto){
        return MyReviewResponse.builder()
            .myReviewDtoList(myReviewDto)
            .build();
    }
}
