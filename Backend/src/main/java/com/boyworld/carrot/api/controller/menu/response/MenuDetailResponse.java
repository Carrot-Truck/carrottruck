package com.boyworld.carrot.api.controller.menu.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class MenuDetailResponse {

    private Long menuId;
    private String menuName;
    private Integer price;
    private String description;
    private Boolean soldOut;
    private Long menuImageId;
    private List<MenuOptionResponse> menuOptions;

    @Builder
    public MenuDetailResponse(Long menuId, String menuName, Integer price, String description, Boolean soldOut, Long menuImageId, List<MenuOptionResponse> menuOptions) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.price = price;
        this.description = description;
        this.soldOut = soldOut;
        this.menuImageId = menuImageId;
        this.menuOptions = menuOptions;
    }
}
