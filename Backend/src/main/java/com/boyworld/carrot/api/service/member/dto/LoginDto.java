package com.boyworld.carrot.api.service.member.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDto {

    private String email;

    private String password;

    private String role;

    @Builder
    public LoginDto(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
