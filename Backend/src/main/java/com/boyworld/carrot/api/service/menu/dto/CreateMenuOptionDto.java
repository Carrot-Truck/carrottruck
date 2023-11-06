package com.boyworld.carrot.api.service.menu.dto;

import com.boyworld.carrot.domain.menu.Menu;
import com.boyworld.carrot.domain.menu.MenuInfo;
import com.boyworld.carrot.domain.menu.MenuOption;
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

    public MenuOption toEntity(Menu menu) {
        return MenuOption.builder()
                .menuInfo(MenuInfo.builder()
                        .name(this.menuOptionName)
                        .description(this.menuOptionDescription)
                        .price(this.menuOptionPrice)
                        .soldOut(false)
                        .build())
                .active(true)
                .menu(menu)
                .build();
    }
}
