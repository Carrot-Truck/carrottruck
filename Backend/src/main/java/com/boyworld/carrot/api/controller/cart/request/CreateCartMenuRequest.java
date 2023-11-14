package com.boyworld.carrot.api.controller.cart.request;

import com.boyworld.carrot.api.service.cart.dto.CreateCartMenuDto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateCartMenuRequest {
    @NotNull
    private Long menuId;

    @NotNull
    private Integer cartMenuQuantity;

    private List<Long> menuOptionIds;


    @Builder
    public CreateCartMenuRequest(Long menuId, Integer cartMenuQuantity, List<Long> menuOptionIds) {
        this.menuId = menuId;
        this.cartMenuQuantity = cartMenuQuantity;
        this.menuOptionIds = menuOptionIds;
    }


    public CreateCartMenuDto toCreateMenuDto() {
        return CreateCartMenuDto.builder()
                .menuId(this.menuId)
                .cartMenuQuantity(this.cartMenuQuantity)
                .menuOptionIds(this.menuOptionIds)
                .build();
    }
}
