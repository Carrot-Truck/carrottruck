package com.boyworld.carrot.api.controller.menu.request;

import com.boyworld.carrot.api.service.menu.dto.CreateMenuOptionDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateMenuOptionRequest {

    private String menuOptionName;
    private Integer menuOptionPrice;
    private String menuOptionDescription;

    @Builder
    public CreateMenuOptionRequest(String menuOptionName, Integer menuOptionPrice, String menuOptionDescription) {
        this.menuOptionName = menuOptionName;
        this.menuOptionPrice = menuOptionPrice;
        this.menuOptionDescription = menuOptionDescription;
    }

    public CreateMenuOptionDto toCreateMenuOptionDto() {
        return CreateMenuOptionDto.builder()
                .menuOptionName(this.menuOptionName)
                .menuOptionPrice(this.menuOptionPrice)
                .menuOptionDescription(this.menuOptionDescription)
                .build();
    }
}
