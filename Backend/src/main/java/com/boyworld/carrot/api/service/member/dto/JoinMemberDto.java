package com.boyworld.carrot.api.service.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class JoinMemberDto {

    private String email;

    private String nickname;

    private String password;

    private String name;

    private String phoneNumber;

    @Builder
    public JoinMemberDto(String email, String nickname, String password, String name, String phoneNumber) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
