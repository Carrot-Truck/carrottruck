package com.boyworld.carrot.api.service.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class EditMemberDto {

    private String name;

    private String nickname;

    private String phoneNumber;

    private String role;

    @Builder
    public EditMemberDto(String name, String nickname, String phoneNumber, String role) {
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }
}
