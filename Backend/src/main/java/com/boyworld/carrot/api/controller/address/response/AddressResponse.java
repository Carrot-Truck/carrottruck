package com.boyworld.carrot.api.controller.address.response;

import com.boyworld.carrot.api.service.address.dto.AddressInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class AddressResponse {

    List<AddressInfoDto> address;

    @Builder
    public AddressResponse(List<AddressInfoDto> address) {
        this.address = address;
    }

    public static AddressResponse of(List<AddressInfoDto> address) {
        return AddressResponse.builder()
                .address(address)
                .build();
    }
}
