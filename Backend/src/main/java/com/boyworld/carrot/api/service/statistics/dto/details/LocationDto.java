package com.boyworld.carrot.api.service.statistics.dto.details;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LocationDto {

    private BigDecimal latitude;

    private BigDecimal longitude;

    @Builder
    public LocationDto(BigDecimal latitude, BigDecimal longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
