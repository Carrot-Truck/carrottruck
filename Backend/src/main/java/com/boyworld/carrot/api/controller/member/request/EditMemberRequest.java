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


    @Builder
    public EditMemberRequest(String name, String nickname, String phoneNumber) {
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
    }


    public EditMemberDto toEditMemberDto(String email) {
        return EditMemberDto.builder()
                .name(this.name)
                .email(email)
                .nickname(this.nickname)
                .phoneNumber(this.phoneNumber)
                .build();
    }
}
