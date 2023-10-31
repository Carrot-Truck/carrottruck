package com.boyworld.carrot.api.controller.member.response;

import lombok.Builder;
import lombok.Data;

@Data
public class VendorInfoResponse {

    private Long vendorInfoId;

    private String tradeName;

    private String vendorName;

    private String businessNumber;

    private String phoneNumber;

    @Builder
    public VendorInfoResponse(Long vendorInfoId, String tradeName, String vendorName, String businessNumber, String phoneNumber) {
        this.vendorInfoId = vendorInfoId;
        this.tradeName = tradeName;
        this.vendorName = vendorName;
        this.businessNumber = businessNumber;
        this.phoneNumber = phoneNumber;
    }
}
