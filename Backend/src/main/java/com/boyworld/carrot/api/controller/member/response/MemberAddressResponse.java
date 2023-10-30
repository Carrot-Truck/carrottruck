package com.boyworld.carrot.api.controller.member.response;


import lombok.Builder;
import lombok.Data;

@Data
public class MemberAddressResponse {

    private String address;

    @Builder
    public MemberAddressResponse(String address) {
        this.address = address;
    }
}
