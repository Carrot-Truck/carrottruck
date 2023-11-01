package com.boyworld.carrot.api.controller.member.response;


import com.boyworld.carrot.domain.member.MemberAddress;
import lombok.Builder;
import lombok.Data;

@Data
public class MemberAddressDetailResponse {

    private Long memberAddressId;
    private String address;
    private Boolean selected;

    @Builder
    public MemberAddressDetailResponse(Long memberAddressId, String address, Boolean selected) {
        this.memberAddressId = memberAddressId;
        this.address = address;
        this.selected = selected;
    }

    public static MemberAddressDetailResponse of(MemberAddress memberAddress) {
        return MemberAddressDetailResponse.builder()
                .memberAddressId(memberAddress.getId())
                .address(memberAddress.getAddress())
                .selected(memberAddress.getSelected())
                .build();
    }
}
