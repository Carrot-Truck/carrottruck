package com.boyworld.carrot.api.controller.menu.request;

import com.boyworld.carrot.api.service.menu.dto.EditMenuOptionDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EditMenuOptionRequest {

    private Long menuOptionId;
    private String menuOptionName;
    private Integer menuOptionPrice;
    private String menuOptionDescription;

    @Builder
    public EditMenuOptionRequest(Long menuOptionId, String menuOptionName, Integer menuOptionPrice, String menuOptionDescription) {
        this.menuOptionId = menuOptionId;
        this.menuOptionName = menuOptionName;
        this.menuOptionPrice = menuOptionPrice;
        this.menuOptionDescription = menuOptionDescription;
    }

    public EditMenuOptionDto toEditMenuOptionDto() {
        return EditMenuOptionDto.builder()
                .menuOptionId(this.menuOptionId)
                .menuOptionName(this.menuOptionName)
                .menuOptionPrice(this.menuOptionPrice)
                .menuOptionDescription(this.menuOptionDescription)
                .build();
    }
}
