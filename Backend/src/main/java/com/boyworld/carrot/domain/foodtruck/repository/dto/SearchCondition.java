package com.boyworld.carrot.domain.foodtruck.repository.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

@Data
public class SearchCondition {

    private Long categoryId;
    private String keyword;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private OrderCondition orderCondition;

    public SearchCondition(Long categoryId, String keyword, BigDecimal longitude, BigDecimal latitude) {
        this.categoryId = categoryId;
        this.keyword = keyword;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Builder
    public SearchCondition(Long categoryId, String keyword, BigDecimal longitude, BigDecimal latitude, String orderCondition) {
        this.categoryId = categoryId;
        this.keyword = keyword;
        this.longitude = longitude;
        this.latitude = latitude;
        this.orderCondition = StringUtils.hasText(orderCondition) ? OrderCondition.valueOf(orderCondition) : null;
    }

    public static SearchCondition of(Long categoryId, String keyword, BigDecimal longitude, BigDecimal latitude) {
        return new SearchCondition(categoryId, keyword, longitude, latitude);
    }

    public static SearchCondition of(Long categoryId, String keyword, BigDecimal longitude, BigDecimal latitude, String orderCondition) {
        return new SearchCondition(categoryId, keyword, longitude, latitude, orderCondition);
    }
}
