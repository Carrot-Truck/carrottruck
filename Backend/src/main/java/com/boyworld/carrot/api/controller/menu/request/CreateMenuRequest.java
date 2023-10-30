package com.boyworld.carrot.api.controller.menu.request;

import com.boyworld.carrot.api.service.menu.dto.CreateMenuDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CreateMenuRequest {

    private Long foodTruckId;
    private String menuName;
    private Integer price;
    private String description;
    private List<CreateMenuOptionRequest> menuOptions;

    @Builder
    public CreateMenuRequest(Long foodTruckId, String menuName, Integer price, String description,
                             List<CreateMenuOptionRequest> menuOptions) {
        this.foodTruckId = foodTruckId;
        this.menuName = menuName;
        this.price = price;
        this.description = description;
        this.menuOptions = menuOptions;
    }

    public CreateMenuDto toCreateMenuDto() {
        return CreateMenuDto.builder()
                .foodTruckId(this.foodTruckId)
                .menuName(this.menuName)
                .price(this.price)
                .description(this.description)
                .menuOptionDtos(this.menuOptions.stream()
                        .map(CreateMenuOptionRequest::toCreateMenuOptionDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
