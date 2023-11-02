package com.boyworld.carrot.api.controller.sale.response;

import com.boyworld.carrot.api.service.sale.dto.SaleMenuItem;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OpenSaleResponse {

    private Long saleId;
    private List<SaleMenuItem> saleMenuItems;
}
