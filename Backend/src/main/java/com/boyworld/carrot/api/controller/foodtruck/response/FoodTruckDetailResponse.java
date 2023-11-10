package com.boyworld.carrot.api.controller.foodtruck.response;

import com.boyworld.carrot.api.service.foodtruck.dto.FoodTruckClientDetailDto;
import com.boyworld.carrot.api.service.menu.dto.MenuDto;
import com.boyworld.carrot.api.service.schedule.dto.ScheduleDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class FoodTruckDetailResponse<T> {

    private T foodTruck;
    private List<MenuDto> menus;
    private List<ScheduleDto> schedules;
    @Builder
    public FoodTruckDetailResponse(T foodTruck, List<MenuDto> menus,
                                   List<ScheduleDto> schedules) {
        this.foodTruck = foodTruck;
        this.menus = menus;
        this.schedules = schedules;
    }

    public static <T> FoodTruckDetailResponse<T> of(T foodTruck, List<MenuDto> menus,
                                             List<ScheduleDto> schedules) {
        return new FoodTruckDetailResponse<>(foodTruck, menus, schedules);
    }
}
