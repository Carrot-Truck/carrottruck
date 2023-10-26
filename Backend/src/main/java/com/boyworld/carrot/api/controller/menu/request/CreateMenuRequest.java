package com.boyworld.carrot.api.controller.menu.request;

import com.boyworld.carrot.api.service.menu.dto.CreateMenuDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateMenuRequest {

    private Long foodTruckId;
    private String menuName;
    private Integer price;
    private String description;

    @Builder
    public CreateMenuRequest(Long foodTruckId, String menuName, Integer price, String description) {
        this.foodTruckId = foodTruckId;
        this.menuName = menuName;
        this.price = price;
        this.description = description;
    }

    public CreateMenuDto toCreateMenuDto() {
        return CreateMenuDto.builder()
                .foodTruckId(this.foodTruckId)
                .menuName(this.menuName)
                .price(this.price)
                .description(this.description)
                .build();
    }
}
