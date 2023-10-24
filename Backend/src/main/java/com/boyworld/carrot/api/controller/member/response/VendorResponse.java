package com.boyworld.carrot.api.controller.member.response;

import lombok.Builder;
import lombok.Data;

@Data
public class VendorResponse {

    private String name;
    private String nickname;
    private String email;
    private String phoneNumber;
    private String businessNumber;
    private String role;

    @Builder
    public VendorResponse(String name, String nickname, String email, String phoneNumber, String businessNumber, String role) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.businessNumber = businessNumber;
        this.role = role;
    }
}
