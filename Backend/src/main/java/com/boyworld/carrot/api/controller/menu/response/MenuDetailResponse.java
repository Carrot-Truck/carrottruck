package com.boyworld.carrot.api.controller.menu.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class MenuDetailResponse {

    private Long menuId;
    private String menuName;
    private Integer menuPrice;
    private String menuDescription;
    private Boolean menuSoldOut;
    private Long menuImageId;
    private List<MenuOptionResponse> menuOptions;

    @Builder
    public MenuDetailResponse(Long menuId, String menuName, Integer menuPrice, String menuDescription, Boolean menuSoldOut, Long menuImageId, List<MenuOptionResponse> menuOptions) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuDescription = menuDescription;
        this.menuSoldOut = menuSoldOut;
        this.menuImageId = menuImageId;
        this.menuOptions = menuOptions;
    }
}
