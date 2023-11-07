package com.boyworld.carrot.api.service.address.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class AddressInfoDto {

    private Long id;

    private String name;

    @Builder
    public AddressInfoDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
