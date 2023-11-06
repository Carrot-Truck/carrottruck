package com.boyworld.carrot.api.service.analysis.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StoreAnalysisDto {

    private String storeName;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private String sido;

    private String sigungu;

    private String dong;

    private String indsMclsCd;

    private String indsMclsNm;

    private String indsSclsCd;

    private String indsSclsNm;

    @Builder
    public StoreAnalysisDto(String storeName, BigDecimal latitude, BigDecimal longitude, String sido, String sigungu, String dong, String indsMclsCd, String indsMclsNm, String indsSclsCd, String indsSclsNm) {
        this.storeName = storeName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.sido = sido;
        this.sigungu = sigungu;
        this.dong = dong;
        this.indsMclsCd = indsMclsCd;
        this.indsMclsNm = indsMclsNm;
        this.indsSclsCd = indsSclsCd;
        this.indsSclsNm = indsSclsNm;
    }

    public static StoreAnalysisDto of(Store store) {
        return StoreAnalysisDto.builder()
                .storeName(store.getBizesNm())
                .latitude(new BigDecimal(store.getLat()))
                .longitude(new BigDecimal(store.getLon()))
                .sido(store.getCtprvnNm())
                .sigungu(store.getSignguNm())
                .dong(store.getLdongNm())
                .indsMclsCd(store.getIndsMclsCd())
                .indsMclsNm(store.getIndsMclsNm())
                .indsSclsCd(store.getIndsSclsCd())
                .indsSclsNm(store.getIndsSclsNm())
                .build();
    }
}
