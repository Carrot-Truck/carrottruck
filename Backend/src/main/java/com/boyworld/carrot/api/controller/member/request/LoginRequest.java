package com.boyworld.carrot.api.controller.member.request;

import com.boyworld.carrot.api.service.member.dto.LoginDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String role;

    @Builder
    public LoginRequest(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public LoginDto toLoginDto() {
        return LoginDto.builder()
                .email(this.email)
                .password(this.password)
                .role(this.role)
                .build();
    }
}
