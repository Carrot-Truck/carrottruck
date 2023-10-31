package com.boyworld.carrot.api.service.cart.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateCartMenuDto {
    private Long foodTruckId;
    private String menuName;
    private Integer menuPrice;
    private Long menuId;
    private Integer cartMenuQuantity;
    private List<CreateCartMenuOptionDto> cartMenuOptionDtos;

    @Builder
    public CreateCartMenuDto(Long foodTruckId, String menuName, Integer menuPrice, Long menuId, Integer cartMenuQuantity, List<CreateCartMenuOptionDto> cartMenuOptionDtos) {
        this.foodTruckId = foodTruckId;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuId = menuId;
        this.cartMenuQuantity = cartMenuQuantity;
        this.cartMenuOptionDtos = cartMenuOptionDtos;
    }
}
