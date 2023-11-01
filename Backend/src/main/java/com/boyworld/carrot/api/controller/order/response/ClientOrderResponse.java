package com.boyworld.carrot.api.controller.order.response;

import com.boyworld.carrot.api.service.order.dto.ClientOrderItem;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class ClientOrderResponse {
    List<ClientOrderItem> clientOrderItems;
}
