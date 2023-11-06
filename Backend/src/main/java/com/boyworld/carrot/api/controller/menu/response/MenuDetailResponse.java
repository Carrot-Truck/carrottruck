package com.boyworld.carrot.api.controller.menu.response;

import com.boyworld.carrot.api.service.menu.dto.MenuDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class MenuDetailResponse {

    private MenuDto menu;
    private Integer menuOptionCount;
    private List<MenuOptionResponse> menuOptions;

    @Builder
    public MenuDetailResponse(MenuDto menu, List<MenuOptionResponse> menuOptions) {
        this.menu = menu;
        this.menuOptionCount = menuOptions.size();
        this.menuOptions = menuOptions;
    }

    public static MenuDetailResponse of(MenuDto menu, List<MenuOptionResponse> menuOptions) {
        return MenuDetailResponse.builder()
                .menu(menu)
                .menuOptions(menuOptions)
                .build();
    }
}
