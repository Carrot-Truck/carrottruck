package com.boyworld.carrot.api.service.menu.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EditMenuOptionDto {

    private Long menuOptionId;
    private String menuOptionName;
    private Integer menuOptionPrice;
    private String menuOptionDescription;

    @Builder
    public EditMenuOptionDto(Long menuOptionId, String menuOptionName, Integer menuOptionPrice, String menuOptionDescription) {
        this.menuOptionId = menuOptionId;
        this.menuOptionName = menuOptionName;
        this.menuOptionPrice = menuOptionPrice;
        this.menuOptionDescription = menuOptionDescription;
    }
}
