package com.boyworld.carrot.api.controller.member.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberAddressRequest {

    @NotBlank
    private String address;

    @Builder
    public MemberAddressRequest(String address) {
        this.address = address;
    }
}
