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
    private Integer orderCnt;
    private Status status;
    private Integer totalPrice;
    private LocalDateTime createdTime;
    private LocalDateTime expectTime;
    private List<OrderMenuItem> orderMenuItems;
}
