package com.boyworld.carrot.api.controller.menu.response;

import com.boyworld.carrot.api.service.menu.dto.MenuDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class MenuResponse {

    private Integer menuCount;
    private List<MenuDto> menus;

    @Builder
    public MenuResponse(List<MenuDto> menus) {
        this.menuCount = menus.size();
        this.menus = menus;
    }

    public static MenuResponse of(List<MenuDto> menus) {
        return MenuResponse.builder().menus(menus).build();
    }
}
