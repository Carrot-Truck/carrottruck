package com.boyworld.carrot.api.controller.menu.request;

import com.boyworld.carrot.api.service.menu.dto.EditMenuDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class EditMenuRequest {

    private String menuName;
    private Integer menuPrice;
    private String menuDescription;
    private List<EditMenuOptionRequest> menuOptions;

    @Builder
    public EditMenuRequest(String menuName, Integer menuPrice, String menuDescription, List<EditMenuOptionRequest> menuOptions) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuDescription = menuDescription;
        this.menuOptions = menuOptions;
    }

    public EditMenuDto toEditMenuDto(Long menuId) {
        return EditMenuDto.builder()
                .menuId(menuId)
                .menuName(this.menuName)
                .menuPrice(this.menuPrice)
                .menuDescription(this.menuDescription)
                .menuOptions(this.menuOptions.stream()
                        .map(EditMenuOptionRequest::toEditMenuOptionDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
