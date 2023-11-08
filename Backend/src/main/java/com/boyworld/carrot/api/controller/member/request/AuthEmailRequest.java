package com.boyworld.carrot.api.controller.member.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthEmailRequest {

    @NotBlank
    private String email;

    @Builder
    public AuthEmailRequest(String email) {
        this.email = email;
    }
}
