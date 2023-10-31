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


    @Builder
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginDto toLoginDto() {
        return LoginDto.builder()
                .email(this.email)
                .password(this.password)
                .build();
    }
}
