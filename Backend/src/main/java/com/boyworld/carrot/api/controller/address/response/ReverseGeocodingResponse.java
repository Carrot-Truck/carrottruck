package com.boyworld.carrot.api.controller.address.response;

import lombok.Builder;
import lombok.Data;

@Data
public class ReverseGeocodingResponse {

    String address;

    @Builder
    public ReverseGeocodingResponse(String address) { this.address = address; }
}
