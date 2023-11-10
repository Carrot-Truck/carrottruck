package com.boyworld.carrot.api.controller.member.request;

import com.boyworld.carrot.api.service.member.dto.JoinMemberDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JoinRequest {

    @NotBlank
    @Pattern(regexp = "^[0-9a-zA-Z]([-_￦.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_￦.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$")
    private String email;

    @NotBlank
    private String nickname;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    @Pattern(regexp = "^010-\\\\d{3,4}=\\\\d{4}$")
    private String phoneNumber;

    @NotBlank
    private String role;

    @Builder
    public JoinRequest(String email, String nickname, String password, String name, String phoneNumber, String role) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public JoinMemberDto toJoinMemberDto() {
        return JoinMemberDto.builder()
                .email(this.email)
                .nickname(this.nickname)
                .password(this.password)
                .name(this.name)
                .phoneNumber(this.phoneNumber)
                .role(this.role)
                .build();
    }
}
