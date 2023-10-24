package com.boyworld.carrot.api.controller.member.response;

import com.boyworld.carrot.domain.member.Member;
import lombok.Builder;
import lombok.Data;

@Data
public class JoinMemberResponse {

    private String email;
    private String nickname;
    private String name;
    private String phoneNumber;
    private String role;

    @Builder
    public JoinMemberResponse(String email, String nickname, String name, String phoneNumber, String role) {
        this.email = email;
        this.nickname = nickname;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public static JoinMemberResponse of(Member member) {
        return JoinMemberResponse.builder()
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .phoneNumber(member.getPhoneNumber())
                .role(member.getRole().toString())
                .build();
    }
}
