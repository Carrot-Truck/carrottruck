package com.boyworld.carrot.api.controller.review.response;

import com.boyworld.carrot.api.service.review.dto.FoodTruckReviewDto;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
public class FoodTruckReviewResponse {

    private List<FoodTruckReviewDto> foodTruckReviewDtoList;

    private float averageGrade;

    @Builder
    private FoodTruckReviewResponse(List<FoodTruckReviewDto> foodTruckReviewDtoList){
        this.foodTruckReviewDtoList = foodTruckReviewDtoList;
        if(foodTruckReviewDtoList != null && foodTruckReviewDtoList.size() != 0){
            averageGrade = 0;
            for(FoodTruckReviewDto foodTruckReviewDto : foodTruckReviewDtoList){
                this.averageGrade += foodTruckReviewDto.getGrade();
            }
            this.averageGrade /= foodTruckReviewDtoList.size();
        }
    }

    public static FoodTruckReviewResponse of(List<FoodTruckReviewDto> foodTruckReviewDtoList){
        return FoodTruckReviewResponse.builder()
            .foodTruckReviewDtoList(foodTruckReviewDtoList)
            .build();
    }
}
