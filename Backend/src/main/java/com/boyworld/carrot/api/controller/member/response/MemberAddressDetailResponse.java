package com.boyworld.carrot.api.controller.member.response;


import com.boyworld.carrot.domain.member.MemberAddress;
import lombok.Builder;
import lombok.Data;

@Data
public class MemberAddressDetailResponse {

    private Long memberAddressId;
    private String address;

    @Builder
    public MemberAddressDetailResponse(Long memberAddressId, String address) {
        this.memberAddressId = memberAddressId;
        this.address = address;
    }

    public static MemberAddressDetailResponse of(MemberAddress memberAddress) {
        return MemberAddressDetailResponse.builder()
                .memberAddressId(memberAddress.getId())
                .address(memberAddress.getAddress())
                .build();
    }
}
