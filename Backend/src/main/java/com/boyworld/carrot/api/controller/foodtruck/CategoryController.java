package com.boyworld.carrot.api.controller.foodtruck;

import com.boyworld.carrot.api.ApiResponse;
import com.boyworld.carrot.api.controller.foodtruck.response.CategoryResponse;
import com.boyworld.carrot.api.service.foodtruck.CategoryQueryService;
import com.boyworld.carrot.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 카테고리 API 컨트롤러
 *
 * @author 최영환
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryQueryService categoryQueryService;

    /**
     * 카테고리 목록 조회 API
     *
     * @return 서비스 내의 모든 카테고리 목록
     */
    @GetMapping
    public ApiResponse<CategoryResponse> getCategories() {
        log.debug("CategoryController#getCategories called");

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        CategoryResponse response = categoryQueryService.getCategories();
        log.debug("response={}", response);

        return ApiResponse.ok(response);
    }
}
