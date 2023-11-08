package com.boyworld.carrot.api.service.analysis;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.controller.analysis.response.StoreAnalysisResponse;
import com.boyworld.carrot.domain.adong.AdongCode;
import com.boyworld.carrot.domain.adong.repository.AdongCodeRepository;
import com.boyworld.carrot.domain.foodtruck.Category;
import com.boyworld.carrot.domain.foodtruck.CategoryCode;
import com.boyworld.carrot.domain.foodtruck.repository.command.CategoryCodeRepository;
import com.boyworld.carrot.domain.foodtruck.repository.command.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
public class AnalysisQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    AnalysisQueryService analysisQueryService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryCodeRepository categoryCodeRepository;

    @Autowired
    AdongCodeRepository adongCodeRepository;

    @DisplayName("현재 위치를 기반으로 상권분석을 할 수 있다.")
    @Test
    void getStoreAnalysis() {
        Category category = createCategory("한식");
        CategoryCode categoryCode = createCategoryCode(category, "I201");
        AdongCode adongCode = createAdongCode();

        // 광주광역시 광산구 수완동
        StoreAnalysisResponse response = analysisQueryService.getStoreAnalysis(
                category.getId(),
                new BigDecimal("35.19508792"),
                new BigDecimal("126.8145971")
        );

        log.debug("getStoreAnalysis#response#(radiusCount={}, addressCount={})",
                response.getRadiusCount(), response.getAddressCount());
    }

    private Category createCategory(String name) {
        Category category = Category.builder()
                .name(name)
                .active(true)
                .build();
        return categoryRepository.save(category);
    }

    private CategoryCode createCategoryCode(Category category, String code) {
        CategoryCode categoryCode = CategoryCode.builder()
                .category(category)
                .code(code)
                .active(true)
                .build();
        return categoryCodeRepository.save(categoryCode);
    }

    private AdongCode createAdongCode() {
        AdongCode adongCode = adongCodeRepository.findAdongCodeByDong("수완동");
        if (adongCode != null) {
            return adongCode;
        }

        adongCode = AdongCode.builder()
                .adongCode("29200637")
                .sido("광주광역시")
                .sigungu("광산구")
                .dong("수완동")
                .createdDate(LocalDateTime.now())
                .build();

        return adongCodeRepository.save(adongCode);
    }
}
