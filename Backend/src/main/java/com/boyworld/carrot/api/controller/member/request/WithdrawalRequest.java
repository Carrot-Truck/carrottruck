package com.boyworld.carrot.api.controller.member.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WithdrawalRequest {

    private String email;

    private String password;

    @Builder
    public WithdrawalRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
