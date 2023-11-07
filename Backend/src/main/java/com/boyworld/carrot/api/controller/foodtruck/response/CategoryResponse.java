package com.boyworld.carrot.api.controller.foodtruck.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class CategoryResponse {

    private Integer categoryCount;
    private List<CategoryDetailResponse> categories;

    @Builder
    public CategoryResponse(List<CategoryDetailResponse> categories) {
        this.categoryCount = categories.size();
        this.categories = categories;
    }

    public static CategoryResponse of(List<CategoryDetailResponse> categories) {
        return CategoryResponse.builder()
                .categories(categories)
                .build();
    }
}
