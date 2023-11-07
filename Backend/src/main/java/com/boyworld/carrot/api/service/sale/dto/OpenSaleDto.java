package com.boyworld.carrot.api.service.sale.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
public class OpenSaleDto {
    Long foodTruckId;
    String address;
    BigDecimal longitude;
    BigDecimal latitude;
    List<SaleMenuItem> saleMenuItems;

    @Builder
    public OpenSaleDto(Long foodTruckId, String address, BigDecimal longitude, BigDecimal latitude, List<SaleMenuItem> saleMenuItems) {
        this.foodTruckId = foodTruckId;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.saleMenuItems = saleMenuItems;
    }
}
