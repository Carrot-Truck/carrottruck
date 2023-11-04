package com.boyworld.carrot.domain.foodtruck.repository.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

@Data
public class SearchCondition {

    private Long categoryId;
    private String keyword;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private OrderCondition orderCondition;

    public SearchCondition(Long categoryId, String keyword, BigDecimal latitude, BigDecimal longitude) {
        this.categoryId = categoryId;
        this.keyword = keyword;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Builder
    public SearchCondition(Long categoryId, String keyword, BigDecimal latitude, BigDecimal longitude, String orderCondition) {
        this.categoryId = categoryId;
        this.keyword = keyword;
        this.longitude = longitude;
        this.latitude = latitude;
        this.orderCondition = StringUtils.hasText(orderCondition) ? OrderCondition.valueOf(orderCondition) : null;
    }

    public static SearchCondition of(Long categoryId, String keyword, BigDecimal latitude, BigDecimal longitude) {
        return new SearchCondition(categoryId, keyword, latitude, longitude);
    }

    public static SearchCondition of(Long categoryId, String keyword, BigDecimal latitude, BigDecimal longitude, String orderCondition) {
        return new SearchCondition(categoryId, keyword, latitude, longitude, orderCondition);
    }
}
