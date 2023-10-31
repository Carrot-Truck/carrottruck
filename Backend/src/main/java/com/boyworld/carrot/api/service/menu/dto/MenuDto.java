package com.boyworld.carrot.api.service.menu.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class MenuDto {

    private Long menuId;
    private String menuName;
    private Integer menuPrice;
    private String menuDescription;
    private Boolean menuSoldOut;
    private Long menuImageId;

    @Builder
    public MenuDto(Long menuId, String menuName, Integer menuPrice, String menuDescription, Boolean menuSoldOut, Long menuImageId) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuDescription = menuDescription;
        this.menuSoldOut = menuSoldOut;
        this.menuImageId = menuImageId;
    }
}
