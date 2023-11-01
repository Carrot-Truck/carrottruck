package com.boyworld.carrot.api.service.member.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SelectedMemberAddressDto {
    private Long selectedMemberAddressId;
    private Long targetMemberAddressId;

    @Builder
    public SelectedMemberAddressDto(Long selectedMemberAddressId, Long targetMemberAddressId) {
        this.selectedMemberAddressId = selectedMemberAddressId;
        this.targetMemberAddressId = targetMemberAddressId;
    }
}
