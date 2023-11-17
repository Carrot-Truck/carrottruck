package com.boyworld.carrot.api.service.order.dto;

import com.boyworld.carrot.domain.member.ClientInfo;
import com.boyworld.carrot.domain.order.Status;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
public class OrderItem {

    private Long orderId;
    private Status status;
    private ClientInfo clientInfo;
    private Integer orderCnt;
    private Integer totalPrice;
    private String createdTime;
    private String expectTime;
    private List<OrderMenuItem> orderMenuItems;

    public OrderItem(Long orderId, Status status, Integer totalPrice,
                     LocalDateTime createdTime, LocalDateTime expectTime) {
        this.orderId = orderId;
        this.status = status;
        this.totalPrice = totalPrice;
        this.createdTime = createdTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd E HH:mm"));
        this.expectTime = expectTime != null ? expectTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd E HH:mm")) : null;
    }

    public OrderItem(Long orderId, Status status, String nickname, String phoneNumber, Integer totalPrice,
                     LocalDateTime createdTime, LocalDateTime expectTime) {
        this.orderId = orderId;
        this.status = status;
        this.clientInfo = ClientInfo.builder().nickname(nickname).phoneNumber(phoneNumber).build();
        this.totalPrice = totalPrice;
        this.createdTime = createdTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd E HH:mm"));
        this.expectTime = expectTime != null ? expectTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd E HH:mm")) : null;
    }

    @Builder
    public OrderItem(Long orderId, Status status, String nickname, String phoneNumber, Integer totalPrice, Integer orderCnt,
                     LocalDateTime createdTime, LocalDateTime expectTime,
                     List<OrderMenuItem> orderMenuItems) {
        this(orderId, status, nickname, phoneNumber, totalPrice, createdTime, expectTime);
        this.orderCnt = orderCnt;
        this.orderMenuItems = orderMenuItems;
    }

}
