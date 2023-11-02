package com.boyworld.carrot.api.controller.sale.request;

import com.boyworld.carrot.api.service.sale.dto.OpenSaleDto;
import com.boyworld.carrot.api.service.sale.dto.SaleMenuItem;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OpenSaleRequest {

    Long foodTruckId;
    BigDecimal longitude;
    BigDecimal latitude;
    List<SaleMenuItem> saleMenuItems;

    @Builder
    public OpenSaleRequest(Long foodTruckId, BigDecimal longitude, BigDecimal latitude, List<SaleMenuItem> saleMenuItems) {
        this.foodTruckId = foodTruckId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.saleMenuItems = saleMenuItems;
    }

    public OpenSaleDto toOpenSaleDto() {
        return OpenSaleDto.builder()
            .foodTruckId(this.foodTruckId)
            .longitude(this.longitude)
            .latitude(this.latitude)
            .saleMenuItems(this.saleMenuItems)
            .build();
    }
}
