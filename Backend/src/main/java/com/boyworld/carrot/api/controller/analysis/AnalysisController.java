package com.boyworld.carrot.api.controller.analysis;

import com.boyworld.carrot.api.ApiResponse;
import com.boyworld.carrot.api.controller.analysis.response.StoreAnalysisResponse;
import com.boyworld.carrot.api.service.analysis.AnalysisQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * 상권분석 API 컨트롤러
 *
 * @author 양진형
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/analysis")
public class AnalysisController {

    private final AnalysisQueryService analysisService;

    /**
     * 카테고리별 상권분석 API
     *
     * @param categoryId 검색 카테고리
     * @param latitude 위도
     * @param longitude 경도
     * @return 상권분석 정보
     */
    @GetMapping("/store/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<StoreAnalysisResponse> getStoreAnalysis(@PathVariable Long categoryId,
                                                               @RequestParam BigDecimal latitude,
                                                               @RequestParam BigDecimal longitude) {
        log.debug("AnalysisController#getStoreAnalysis called !!!");
        log.debug("CategoryID={},  latitude={}, longitude={}", categoryId, latitude, longitude);

        StoreAnalysisResponse response = analysisService.getStoreAnalysis(categoryId, latitude, longitude);
        log.debug("StoreAnalysisResponse={}", response);

        return ApiResponse.ok(response);
    }
}
