package com.boyworld.carrot.api.service.foodtruck;

import com.boyworld.carrot.api.controller.foodtruck.response.CategoryDetailResponse;
import com.boyworld.carrot.api.controller.foodtruck.response.CategoryResponse;
import com.boyworld.carrot.domain.foodtruck.repository.query.CategoryQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 카테고리 조회 서비스
 *
 * @author 최영환
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryQueryService {

    private final CategoryQueryRepository categoryQueryRepository;

    /**
     * 카테고리 목록 조회
     *
     * @return 모든 카테고리 목록
     */
    public CategoryResponse getCategories() {
        List<CategoryDetailResponse> categories = categoryQueryRepository.getCategories();

        return CategoryResponse.of(categories);
    }
}
