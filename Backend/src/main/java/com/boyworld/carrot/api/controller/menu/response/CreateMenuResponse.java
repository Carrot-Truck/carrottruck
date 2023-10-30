package com.boyworld.carrot.api.controller.menu.response;

import lombok.Builder;
import lombok.Data;

@Data
public class CreateMenuResponse {

    private Long menuId;
    private Long foodTruckId;
    private String menuName;
    private Integer price;
    private String description;

    @Builder
    public CreateMenuResponse(Long menuId, Long foodTruckId, String menuName, Integer price, String description) {
        this.menuId = menuId;
        this.foodTruckId = foodTruckId;
        this.menuName = menuName;
        this.price = price;
        this.description = description;
    }
}
