package com.boyworld.carrot.api.controller.member.request;

import com.boyworld.carrot.api.service.member.dto.EditMemberDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EditMemberRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String nickname;

    @NotBlank
    @Pattern(regexp = "^010-\\\\d{3,4}=\\\\d{4}$")
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
