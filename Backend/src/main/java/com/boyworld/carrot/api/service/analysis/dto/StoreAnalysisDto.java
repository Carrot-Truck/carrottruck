package com.boyworld.carrot.api.service.analysis.dto;

import lombok.Builder;
import lombok.Data;
import org.apache.catalina.Store;

import java.math.BigDecimal;

@Data
public class StoreAnalysisDto {

    private String storeName;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private String sido;

    private String sigungu;

    private String dong;

    private String middleClassCode;

    private String smallClassCode;

    @Builder
    public StoreAnalysisDto(String storeName, BigDecimal latitude, BigDecimal longitude, String sido, String sigungu, String dong, String middleClassCode, String smallClassCode) {
        this.storeName = storeName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.sido = sido;
        this.sigungu = sigungu;
        this.dong = dong;
        this.middleClassCode = middleClassCode;
        this.smallClassCode = smallClassCode;
    }
}
