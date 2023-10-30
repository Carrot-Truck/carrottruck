package com.boyworld.carrot.api.controller.menu;

import com.boyworld.carrot.api.service.menu.dto.MenuDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class MenuResponse {

    private Boolean hasNext;
    private List<MenuDto> menus;

    @Builder
    public MenuResponse(Boolean hasNext, List<MenuDto> menus) {
        this.hasNext = hasNext;
        this.menus = menus;
    }
}
