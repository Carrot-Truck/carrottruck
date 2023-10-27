package com.boyworld.carrot.api.controller.menu.request;

import com.boyworld.carrot.api.service.menu.dto.EditMenuDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EditMenuRequest {

    private String menuName;
    private Integer menuPrice;
    private String menuDescription;

    @Builder
    public EditMenuRequest(String menuName, Integer menuPrice, String menuDescription) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuDescription = menuDescription;
    }


    public EditMenuDto toEditMenuDto(Long menuId) {
        return EditMenuDto.builder()
                .menuId(menuId)
                .menuName(this.menuName)
                .menuPrice(this.menuPrice)
                .menuDescription(this.menuDescription)
                .build();
    }
}
