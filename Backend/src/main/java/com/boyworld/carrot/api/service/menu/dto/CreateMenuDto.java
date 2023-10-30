package com.boyworld.carrot.api.service.menu.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateMenuDto {

    private Long foodTruckId;
    private String menuName;
    private Integer price;
    private String description;
    private List<CreateMenuOptionDto> menuOptionDtos;

    @Builder
    public CreateMenuDto(Long foodTruckId, String menuName, Integer price, String description, List<CreateMenuOptionDto> menuOptionDtos) {
        this.foodTruckId = foodTruckId;
        this.menuName = menuName;
        this.price = price;
        this.description = description;
        this.menuOptionDtos = menuOptionDtos;
    }
}
