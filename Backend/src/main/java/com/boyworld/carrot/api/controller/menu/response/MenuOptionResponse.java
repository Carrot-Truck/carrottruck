package com.boyworld.carrot.api.controller.menu.response;

import lombok.Builder;
import lombok.Data;

@Data
public class MenuOptionResponse {

    private Long menuOptionId;
    private String menuOptionName;
    private Integer menuOptionPrice;
    private String menuOptionDescription;

    @Builder
    public MenuOptionResponse(Long menuOptionId, String menuOptionName, Integer menuOptionPrice, String menuOptionDescription) {
        this.menuOptionId = menuOptionId;
        this.menuOptionName = menuOptionName;
        this.menuOptionPrice = menuOptionPrice;
        this.menuOptionDescription = menuOptionDescription;
    }
}
