package com.boyworld.carrot.api.service.member.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDto {

    private String email;

    private String password;


    @Builder
    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
