package com.boyworld.carrot.api.service.sale.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaleMenuItem {

    Long menuId;
    List<Long> menuOptionId;

    @Builder
    public SaleMenuItem(Long menuId, List<Long> menuOptionId) {
        this.menuId = menuId;
        this.menuOptionId = menuOptionId;
    }
}
