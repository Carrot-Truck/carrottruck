package com.boyworld.carrot.domain.review;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Review Report status enum
 * @author Gunhoo Park
 */
@Getter
@RequiredArgsConstructor
public enum Status {
    WAITING("대기중"),
    PROCESSING("처리중"),
    DONE("처리완료");

    private final String text;
}
