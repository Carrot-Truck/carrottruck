package com.boyworld.carrot.api.service.member.dto;

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
}
