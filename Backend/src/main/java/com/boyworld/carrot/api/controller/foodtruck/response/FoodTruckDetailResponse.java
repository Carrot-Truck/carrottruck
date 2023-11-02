package com.boyworld.carrot.api.controller.foodtruck.response;

import com.boyworld.carrot.api.service.foodtruck.dto.FoodTruckDetailDto;
import com.boyworld.carrot.api.service.menu.dto.MenuDto;
import com.boyworld.carrot.api.service.review.dto.FoodTruckReviewDto;
import com.boyworld.carrot.api.service.schedule.dto.ScheduleDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class FoodTruckDetailResponse {

    private Boolean isOwner;
    private FoodTruckDetailDto foodTruckDetail;
    private List<MenuDto> menus;
    private List<ScheduleDto> schedules;
    private List<FoodTruckReviewDto> reviews;

    @Builder
    public FoodTruckDetailResponse(Boolean isOwner, FoodTruckDetailDto foodTruckDetail, List<MenuDto> menus,
                                   List<ScheduleDto> schedules, List<FoodTruckReviewDto> reviews) {
        this.isOwner = isOwner;
        this.foodTruckDetail = foodTruckDetail;
        this.menus = menus;
        this.schedules = schedules;
        this.reviews = reviews;
    }
}
