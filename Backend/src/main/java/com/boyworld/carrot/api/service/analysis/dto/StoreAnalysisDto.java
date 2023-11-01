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

    private String middleClassName;

    private String smallClassCode;

    private String smallClassName;

    @Builder
    public StoreAnalysisDto(String storeName, BigDecimal latitude, BigDecimal longitude, String sido, String sigungu, String dong, String middleClassCode, String middleClassName, String smallClassCode, String smallClassName) {
        this.storeName = storeName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.sido = sido;
        this.sigungu = sigungu;
        this.dong = dong;
        this.middleClassCode = middleClassCode;
        this.middleClassName = middleClassName;
        this.smallClassCode = smallClassCode;
        this.smallClassName = smallClassName;
    }
}
