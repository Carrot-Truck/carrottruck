package com.boyworld.carrot.domain.foodtruck.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.boyworld.carrot.domain.foodtruck.QCategoryCode.categoryCode;

/**
 * 카테고리 코드 조회 레포지토리
 *
 * @author 양진형
 */
@Repository
@RequiredArgsConstructor
public class CategoryCodeQueryRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * 카테고리 아이디로 카테고리 코드 조회 쿼리
     * 
     * @param categoryId 카테고리 아이디
     * @return 카테고리 코드
     */
    public List<String> getCodeByCategoryId(Long categoryId) {
        return queryFactory
                .select(categoryCode.code)
                .from(categoryCode)
                .where(categoryCode.category.id.eq(categoryId))
                .fetch();
    }
}
