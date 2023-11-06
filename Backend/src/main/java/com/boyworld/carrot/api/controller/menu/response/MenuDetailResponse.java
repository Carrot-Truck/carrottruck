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
    private String menuImageUrl;
    private List<MenuOptionResponse> menuOptions;

    @Builder
    public MenuDetailResponse(Long menuId, String menuName, Integer menuPrice, String menuDescription,
                              Boolean menuSoldOut, String menuImageUrl, List<MenuOptionResponse> menuOptions) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuDescription = menuDescription;
        this.menuSoldOut = menuSoldOut;
        this.menuImageUrl = menuImageUrl;
        this.menuOptions = menuOptions;
    }
}
