package com.boyworld.carrot.api.controller.cart.request;

import com.boyworld.carrot.api.service.cart.dto.CreateCartMenuDto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.Collator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CreateCartMenuRequest {

    @NotNull
    private Long foodTruckId;

    @NotNull
    private String menuName;

    @NotNull
    private Integer menuPrice;

    @NotNull
    private Long menuId;

    @NotNull
    private Integer cartMenuQuantity;

    private List<CreateCartMenuOptionRequest> cartMenuOptions;

    @Builder
    public CreateCartMenuRequest(@NotNull Long foodTruckId, @NotNull String menuName, @NotNull Integer menuPrice, @NotNull Long menuId, @NotNull Integer cartMenuQuantity, List<CreateCartMenuOptionRequest> cartMenuOptions) {
        this.foodTruckId = foodTruckId;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuId = menuId;
        this.cartMenuQuantity = cartMenuQuantity;
        this.cartMenuOptions = cartMenuOptions;
    }

    public CreateCartMenuDto toCreateMenuDto() {
        return CreateCartMenuDto.builder()
                .foodTruckId(this.foodTruckId)
                .menuName(this.menuName)
                .menuPrice(this.menuPrice)
                .menuId(this.menuId)
                .cartMenuQuantity(this.cartMenuQuantity)
                .cartMenuOptionDtos(this.cartMenuOptions.stream()
                        .map(CreateCartMenuOptionRequest::toCreateCartMenuOptionDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
