package com.boyworld.carrot.api.controller.foodtruck.response;

import com.boyworld.carrot.api.service.foodtruck.dto.FoodTruckDetailDto;
import com.boyworld.carrot.api.service.menu.dto.MenuDto;
import com.boyworld.carrot.api.service.schedule.dto.ScheduleDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class FoodTruckDetailResponse {

    private FoodTruckDetailDto foodTruck;
    private List<MenuDto> menus;
    private List<ScheduleDto> schedules;
    @Builder
    public FoodTruckDetailResponse(FoodTruckDetailDto foodTruck, List<MenuDto> menus,
                                   List<ScheduleDto> schedules) {
        this.foodTruck = foodTruck;
        this.menus = menus;
        this.schedules = schedules;
    }

    public static FoodTruckDetailResponse of(FoodTruckDetailDto foodTruck, List<MenuDto> menus,
                                             List<ScheduleDto> schedules) {
        return FoodTruckDetailResponse.builder()
                .foodTruck(foodTruck)
                .menus(menus)
                .schedules(schedules)
                .build();
    }
}
