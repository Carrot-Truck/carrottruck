package com.boyworld.carrot.api.service.member.dto;

import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.VendorInfo;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateVendorInfoDto {

    private String tradeName;

    private String vendorName;

    private String businessNumber;

    private String phoneNumber;

    @Builder
    public CreateVendorInfoDto(String tradeName, String vendorName, String businessNumber, String phoneNumber) {
        this.tradeName = tradeName;
        this.vendorName = vendorName;
        this.businessNumber = businessNumber;
        this.phoneNumber = phoneNumber;
    }

    public VendorInfo toEntity(Member member) {
        return VendorInfo.builder()
                .vendorName(this.vendorName)
                .tradeName(this.tradeName)
                .businessNumber(this.businessNumber)
                .phoneNumber(this.phoneNumber)
                .member(member)
                .active(true)
                .build();
    }
}
