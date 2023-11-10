package com.boyworld.carrot.api.service.menu.dto;

import lombok.Builder;
import lombok.Data;

import static com.boyworld.carrot.domain.StringConstants.NO_IMG;

@Data
public class MenuDto {

    private Long menuId;
    private String menuName;
    private Integer menuPrice;
    private String menuDescription;
    private Boolean menuSoldOut;
    private String menuImageUrl;

    @Builder
    public MenuDto(Long menuId, String menuName, Integer menuPrice, String menuDescription, Boolean menuSoldOut, String menuImageUrl) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuDescription = menuDescription;
        this.menuSoldOut = menuSoldOut;
        this.menuImageUrl = menuImageUrl != null ? menuImageUrl : NO_IMG;
    }
}
