package com.boyworld.carrot.api.controller.menu.response;

import com.boyworld.carrot.api.service.menu.dto.MenuDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class MenuResponse {

    private List<MenuDto> menus;

    @Builder
    public MenuResponse(List<MenuDto> menus) {
        this.menus = menus;
    }
}
