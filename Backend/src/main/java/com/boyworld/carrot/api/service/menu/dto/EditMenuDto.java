package com.boyworld.carrot.api.service.menu.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class EditMenuDto {

    private Long menuId;
    private String menuName;
    private Integer menuPrice;
    private String menuDescription;
    private List<EditMenuOptionDto> menuOptions;

    @Builder
    public EditMenuDto(Long menuId, String menuName, Integer menuPrice, String menuDescription, List<EditMenuOptionDto> menuOptions) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuDescription = menuDescription;
        this.menuOptions = menuOptions;
    }
}
