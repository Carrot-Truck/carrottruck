package com.boyworld.carrot.api.controller.analysis.response;

import com.boyworld.carrot.api.service.analysis.dto.StoreAnalysisDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class StoreAnalysisResponse {

    private Long categoryId;

    private String categoryName;

    private String sido;

    private String sigungu;

    private String dong;

    private Integer radiusCount;

    private Integer addressCount;

    private List<StoreAnalysisDto> stores;

    @Builder
    public StoreAnalysisResponse(Long categoryId, String categoryName, String sido, String sigungu, String dong, Integer radiusCount, Integer addressCount, List<StoreAnalysisDto> stores) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.sido = sido;
        this.sigungu = sigungu;
        this.dong = dong;
        this.radiusCount = radiusCount;
        this.addressCount = addressCount;
        this.stores = stores;
    }
}
