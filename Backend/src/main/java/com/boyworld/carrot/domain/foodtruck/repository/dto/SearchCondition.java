package com.boyworld.carrot.domain.foodtruck.repository.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class SearchCondition {

    private String categoryId;
    private String keyword;
    private String longitude;
    private String latitude;

    @Builder
    public SearchCondition(String categoryId, String keyword, String longitude, String latitude) {
        this.categoryId = categoryId;
        this.keyword = keyword;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public static SearchCondition of(String categoryId, String keyword, String longitude, String latitude) {
        return SearchCondition.builder()
                .categoryId(categoryId)
                .keyword(keyword)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
