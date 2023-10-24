package com.boyworld.carrot.api.controller.member.request;

import com.boyworld.carrot.api.service.member.dto.EditMemberDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EditMemberRequest {

    private String name;

    private String nickname;

    private String phoneNumber;

    private String role;

    @Builder
    public EditMemberRequest(String name, String nickname, String phoneNumber, String role) {
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }


    public EditMemberDto toEditMemberDto(String email) {
        return EditMemberDto.builder()
                .name(this.name)
                .nickname(this.nickname)
                .phoneNumber(this.phoneNumber)
                .role(role)
                .build();
    }
}
