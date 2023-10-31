package com.boyworld.carrot.api.service.analysis;

import com.boyworld.carrot.api.controller.analysis.response.StoreAnalysisResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 상권분석 서비스
 *
 * @author 양진형
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnalysisQueryService {

    /**
     * 상권분석 서비스
     * 
     * @param categoryId 카테고리 ID
     * @param latitude 위도
     * @param longitude 경도
     * @return 상권분석 정보
     */
    public StoreAnalysisResponse getStoreAnalysis(Integer categoryId, BigDecimal latitude, BigDecimal longitude) {
        // GeocodingUtil 사용
        return null;
    }

}
