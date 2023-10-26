package com.boyworld.carrot.api.service.menu.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateMenuDto {

    private Long foodTruckId;
    private String menuName;
    private Integer price;
    private String description;

    @Builder
    public CreateMenuDto(Long foodTruckId, String menuName, Integer price, String description) {
        this.foodTruckId = foodTruckId;
        this.menuName = menuName;
        this.price = price;
        this.description = description;
    }
}
