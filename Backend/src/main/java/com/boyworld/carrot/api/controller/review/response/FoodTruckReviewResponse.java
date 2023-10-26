package com.boyworld.carrot.api.controller.review.response;

import com.boyworld.carrot.api.service.review.dto.FoodTruckReviewDto;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
public class FoodTruckReviewResponse {

    private List<FoodTruckReviewDto> foodTruckReviewDtoList;

    private int averageGrade;

    @Builder
    public FoodTruckReviewResponse(List<FoodTruckReviewDto> foodTruckReviewDtoList){
        this.foodTruckReviewDtoList = foodTruckReviewDtoList;
        for(FoodTruckReviewDto foodTruckReviewDto : foodTruckReviewDtoList){
            this.averageGrade += foodTruckReviewDto.getGrade();
        }
        this.averageGrade /= foodTruckReviewDtoList.size();
    }
}
