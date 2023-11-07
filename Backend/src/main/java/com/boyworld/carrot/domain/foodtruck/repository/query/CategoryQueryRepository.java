package com.boyworld.carrot.domain.foodtruck.repository.query;

import com.boyworld.carrot.api.controller.foodtruck.response.CategoryDetailResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.boyworld.carrot.domain.foodtruck.QCategory.category;

/**
 * 카테고리 조회 레포지토리
 *
 * @author 최영환
 */
@Repository
@RequiredArgsConstructor
public class CategoryQueryRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 모든 활성화된 카테고리 목록 조회 쿼리
     *
     * @return 모든 활성화된 카테고리 목록
     */
    public List<CategoryDetailResponse> getCategories() {
        List<CategoryDetailResponse> result = queryFactory
                .select(Projections.constructor(CategoryDetailResponse.class,
                        category.id,
                        category.name
                ))
                .from(category)
                .where(
                        category.active
                )
                .fetch();

        if (result == null || result.isEmpty()) {
            result = new ArrayList<>();
        }
        
        return result;
    }
}
