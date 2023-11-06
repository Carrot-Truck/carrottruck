package com.boyworld.carrot.api.controller.menu.response;

import com.boyworld.carrot.domain.menu.MenuOption;
import lombok.Builder;
import lombok.Data;

@Data
public class MenuOptionResponse {

    private Long menuOptionId;
    private String menuOptionName;
    private Integer menuOptionPrice;
    private String menuOptionDescription;
    private Boolean menuOptionSoldOut;

    @Builder
    public MenuOptionResponse(Long menuOptionId, String menuOptionName, Integer menuOptionPrice, String menuOptionDescription, Boolean menuOptionSoldOut) {
        this.menuOptionId = menuOptionId;
        this.menuOptionName = menuOptionName;
        this.menuOptionPrice = menuOptionPrice;
        this.menuOptionDescription = menuOptionDescription;
        this.menuOptionSoldOut = menuOptionSoldOut;
    }

    public static MenuOptionResponse of(MenuOption menuOption) {
        return MenuOptionResponse.builder()
                .menuOptionId(menuOption.getId())
                .menuOptionName(menuOption.getMenuInfo().getName())
                .menuOptionPrice(menuOption.getMenuInfo().getPrice())
                .menuOptionDescription(menuOption.getMenuInfo().getDescription())
                .menuOptionSoldOut(menuOption.getMenuInfo().getSoldOut())
                .build();
    }
}
