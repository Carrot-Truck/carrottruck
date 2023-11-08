package com.boyworld.carrot.domain.foodtruck.repository.query;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.controller.foodtruck.response.CategoryDetailResponse;
import com.boyworld.carrot.domain.foodtruck.Category;
import com.boyworld.carrot.domain.foodtruck.repository.command.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 카테고리 조회 레포지토리 테스트
 *
 * @author 최영환
 */
@Slf4j
class CategoryQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private CategoryQueryRepository categoryQueryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @DisplayName("저장된 모든 카테고리를 조회한다.")
    @Test
    void getCategories() {
        // given
//        Category category1 = createCategory("한식/분식", true);
//
//        Category category2 = createCategory("고기/구이", true);

        // when
        List<CategoryDetailResponse> responses = categoryQueryRepository.getCategories();
        log.debug("responses={}", responses);

        // then
        assertThat(responses).isNotEmpty();
    }

    private Category createCategory(String name, boolean active) {
        Category category = Category.builder()
                .name(name)
                .active(active)
                .build();
        return categoryRepository.save(category);
    }
}