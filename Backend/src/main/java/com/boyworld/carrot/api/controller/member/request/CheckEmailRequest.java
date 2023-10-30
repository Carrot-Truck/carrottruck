package com.boyworld.carrot.api.controller.member.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CheckEmailRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String role;

    @Builder
    public CheckEmailRequest(String email, String role) {
        this.email = email;
        this.role = role;
    }
}
