package com.boyworld.carrot.api.service.menu.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateMenuOptionDto {

    private String menuOptionName;
    private Integer menuOptionPrice;
    private String menuOptionDescription;

    @Builder
    public CreateMenuOptionDto(String menuOptionName, Integer menuOptionPrice, String menuOptionDescription) {
        this.menuOptionName = menuOptionName;
        this.menuOptionPrice = menuOptionPrice;
        this.menuOptionDescription = menuOptionDescription;
    }
}
