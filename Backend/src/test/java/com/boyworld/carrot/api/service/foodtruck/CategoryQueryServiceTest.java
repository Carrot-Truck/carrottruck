package com.boyworld.carrot.api.service.foodtruck;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.controller.foodtruck.response.CategoryResponse;
import com.boyworld.carrot.domain.foodtruck.Category;
import com.boyworld.carrot.domain.foodtruck.repository.command.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 카테고리 조회 서비스 테스트
 *
 * @author 최영환
 */
@Slf4j
class CategoryQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private CategoryQueryService categoryQueryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @DisplayName("사용자는 저장된 모든 카테고리를 조회할 수 있다.")
    @Test
    void getCategories() {
        // given
//        Category category1 = createCategory("한식/분식", true);
//
//        Category category2 = createCategory("고기/구이", true);

        // when
        CategoryResponse response = categoryQueryService.getCategories();
        log.debug("response={}", response);

        // then
        assertThat(response.getCategoryCount()).isNotZero();
        assertThat(response.getCategories()).isNotEmpty();
    }

    private Category createCategory(String name, boolean active) {
        Category category = Category.builder()
                .name(name)
                .active(active)
                .build();
        return categoryRepository.save(category);
    }
}