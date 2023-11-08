package com.boyworld.carrot.api.controller.member.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthCheckEmailRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String authNumber;

    @Builder
    private AuthCheckEmailRequest(String email, String authNumber) {
        this.email = email;
        this.authNumber = authNumber;
    }
}