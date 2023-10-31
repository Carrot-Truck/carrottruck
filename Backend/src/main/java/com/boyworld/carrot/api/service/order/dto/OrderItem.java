package com.boyworld.carrot.api.service.order.dto;

import com.boyworld.carrot.domain.order.Status;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItem {

    private Long orderId;
    private Status status;
    private LocalDateTime createdTime;
    private LocalDateTime expectTime;
    private Integer totalPrice;

    @Builder
    public OrderItem(Long orderId, Status status, LocalDateTime createdTime, LocalDateTime expectTime, Integer totalPrice) {
        this.orderId = orderId;
        this.status = status;
        this.createdTime = createdTime;
        this.expectTime = expectTime;
        this.totalPrice = totalPrice;
    }
}
