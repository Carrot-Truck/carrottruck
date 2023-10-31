package com.boyworld.carrot.api.controller.member.request;

import com.boyworld.carrot.api.service.member.dto.CreateVendorInfoDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateVendorInfoRequest {

    private String tradeName;

    private String vendorName;

    private String businessNumber;

    private String phoneNumber;

    @Builder
    public CreateVendorInfoRequest(String tradeName, String vendorName, String businessNumber, String phoneNumber) {
        this.tradeName = tradeName;
        this.vendorName = vendorName;
        this.businessNumber = businessNumber;
        this.phoneNumber = phoneNumber;
    }

    public CreateVendorInfoDto toCreateVendorInfoDto() {
        return CreateVendorInfoDto.builder()
                .tradeName(this.tradeName)
                .vendorName(this.vendorName)
                .businessNumber(this.businessNumber)
                .phoneNumber(this.phoneNumber)
                .build();
    }
}
