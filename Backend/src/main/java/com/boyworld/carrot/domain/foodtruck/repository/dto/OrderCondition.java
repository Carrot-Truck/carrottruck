package com.boyworld.carrot.domain.foodtruck.repository.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 정렬 조건
 *
 * @author 최영환
 */
@Getter
@RequiredArgsConstructor
public enum OrderCondition {
    LIKE("찜(좋아요)순"),
    GRADE("별점순"),
    REVIEW("리뷰순");

    private final String text;
}
