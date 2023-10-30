package com.boyworld.carrot.api.service.member.dto;

import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import lombok.Builder;
import lombok.Data;

@Data
public class JoinMemberDto {

    private String email;

    private String nickname;

    private String password;

    private String name;

    private String phoneNumber;

    private String role;

    @Builder
    public JoinMemberDto(String email, String nickname, String password, String name, String phoneNumber, String role) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public Member toEntity(String encryptedPwd) {
        return Member.builder()
                .email(this.email)
                .nickname(this.nickname)
                .encryptedPwd(encryptedPwd)
                .name(this.name)
                .phoneNumber(this.phoneNumber)
                .role(Role.valueOf(this.role))
                .active(true)
                .build();
    }
}
