package com.boyworld.carrot.api.controller.foodtruck.response;

import com.boyworld.carrot.domain.foodtruck.Category;
import lombok.Builder;
import lombok.Data;

@Data
public class CategoryDetailResponse {

    private Long categoryId;
    private String categoryName;

    @Builder
    public CategoryDetailResponse(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public static CategoryDetailResponse of(Category category) {
        return CategoryDetailResponse.builder()
                .categoryId(category.getId())
                .categoryName(category.getName())
                .build();
    }
}
