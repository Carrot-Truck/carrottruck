package com.boyworld.carrot.api.controller.order.response;

import com.boyworld.carrot.api.service.order.dto.VendorOrderItem;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VendorOrderResponse {

    List<VendorOrderItem> vendorOrderItems;
}
