package com.boyworld.carrot.api.service.order.dto;

import com.boyworld.carrot.domain.order.Status;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItem {

    private Long orderId;
    private Long memberId;
    private String nickname;
    private String phoneNumber;
    private Status status;
    private Integer orderCnt;
    private Integer totalPrice;
    private LocalDateTime createdTime;
    private LocalDateTime expectTime;
    private List<OrderMenuItem> orderMenuItems;

    @Builder
    public OrderItem() {}

    @Builder
    public OrderItem(Long orderId, Long memberId, String nickname, String phoneNumber,
        Status status, Integer orderCnt, Integer totalPrice, LocalDateTime createdTime,
        LocalDateTime expectTime, List<OrderMenuItem> orderMenuItems) {
        this.orderId = orderId;
        this.memberId = memberId;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.orderCnt = orderCnt;
        this.totalPrice = totalPrice;
        this.createdTime = createdTime;
        this.expectTime = expectTime;
        this.orderMenuItems = orderMenuItems;
    }

}
