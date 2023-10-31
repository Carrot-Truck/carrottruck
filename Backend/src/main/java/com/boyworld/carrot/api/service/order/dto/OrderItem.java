package com.boyworld.carrot.api.service.order.dto;

import com.boyworld.carrot.domain.order.Status;
import java.time.LocalDateTime;

public class OrderItem {

    private Long orderId;
    private Status status;
    private LocalDateTime createdTime;
    private LocalDateTime expectTime;
    private Integer totalPrice;

}
