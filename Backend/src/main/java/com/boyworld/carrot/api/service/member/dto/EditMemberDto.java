package com.boyworld.carrot.api.service.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class EditMemberDto {

    private String name;
    private String email;
    private String nickname;
    private String phoneNumber;

    @Builder
    public EditMemberDto(String name, String email, String nickname, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
    }
}
