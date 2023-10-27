package com.boyworld.carrot.api.service.menu.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class MenuDto {

    private Long menuId;
    private String menuName;
    private Integer price;
    private String description;
    private Boolean soldOut;
    private Long menuImageId;

    @Builder
    public MenuDto(Long menuId, String menuName, Integer price, String description, Boolean soldOut, Long menuImageId) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.price = price;
        this.description = description;
        this.soldOut = soldOut;
        this.menuImageId = menuImageId;
    }
}
