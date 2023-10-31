package com.boyworld.carrot.api.service.order.dto;

import java.util.List;
import lombok.Data;

@Data
public class OrderMenuItem {

    Long menuId;
    Integer quantity;
    List<Long> menuOptionIdList;
}
