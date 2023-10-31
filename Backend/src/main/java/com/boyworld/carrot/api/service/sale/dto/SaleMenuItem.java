package com.boyworld.carrot.api.service.sale.dto;

import java.util.List;
import lombok.Data;

@Data
public class SaleMenuItem {

    Long menuId;
    List<Long> menuOptionId;
}
