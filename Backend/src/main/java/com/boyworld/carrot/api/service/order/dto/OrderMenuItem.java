package com.boyworld.carrot.api.service.order.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderMenuItem {

    Long menuId;
    String name;
    Integer quantity;
    Integer price;
    List<Long> menuOptionIdList;
}
