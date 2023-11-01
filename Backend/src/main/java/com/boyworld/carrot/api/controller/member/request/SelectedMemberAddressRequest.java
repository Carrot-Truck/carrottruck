package com.boyworld.carrot.api.controller.member.request;

import com.boyworld.carrot.api.service.member.dto.SelectedMemberAddressDto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SelectedMemberAddressRequest {

    @NotNull
    private Long selectedMemberAddressId;

    @NotNull
    private Long targetMemberAddressId;

    @Builder
    public SelectedMemberAddressRequest(Long selectedMemberAddressId, Long targetMemberAddressId) {
        this.selectedMemberAddressId = selectedMemberAddressId;
        this.targetMemberAddressId = targetMemberAddressId;
    }

    public SelectedMemberAddressDto toSelectedMemberAddressDto() {
        return SelectedMemberAddressDto.builder()
                .selectedMemberAddressId(this.selectedMemberAddressId)
                .targetMemberAddressId(this.targetMemberAddressId)
                .build();
    }
}
