package com.boyworld.carrot.api.controller.member.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class MemberAddressResponse {

    private Boolean hasNext;
    private List<MemberAddressDetailResponse> memberAddresses;

    @Builder
    public MemberAddressResponse(Boolean hasNext, List<MemberAddressDetailResponse> memberAddresses) {
        this.hasNext = hasNext;
        this.memberAddresses = memberAddresses;
    }

    public static MemberAddressResponse of(List<MemberAddressDetailResponse> memberAddresses, boolean hasNext) {
        return MemberAddressResponse.builder()
                .hasNext(hasNext)
                .memberAddresses(memberAddresses)
                .build();
    }
}
