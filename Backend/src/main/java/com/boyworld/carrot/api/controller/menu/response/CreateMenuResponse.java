package com.boyworld.carrot.api.controller.menu.response;

import com.boyworld.carrot.domain.menu.Menu;
import lombok.Builder;
import lombok.Data;

@Data
public class CreateMenuResponse {

    private Long menuId;
    private Long foodTruckId;
    private String menuName;
    private Integer price;
    private String description;
    private Integer menuOptionSize;

    @Builder
    public CreateMenuResponse(Long menuId, Long foodTruckId, String menuName,
                              Integer price, String description, Integer menuOptionSize) {
        this.menuId = menuId;
        this.foodTruckId = foodTruckId;
        this.menuName = menuName;
        this.price = price;
        this.description = description;
        this.menuOptionSize = menuOptionSize;
    }

    public static CreateMenuResponse of(Menu menu, int menuOptionSize) {
        return CreateMenuResponse.builder()
                .menuId(menu.getId())
                .foodTruckId(menu.getFoodTruck().getId())
                .menuName(menu.getMenuInfo().getName())
                .price(menu.getMenuInfo().getPrice())
                .description(menu.getMenuInfo().getDescription())
                .menuOptionSize(menuOptionSize)
                .build();
    }
}
