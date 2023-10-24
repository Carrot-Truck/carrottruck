package com.boyworld.carrot.domain.review;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Review Report status enum
 *
 * @author Gunhoo Park
 */
@Getter
@RequiredArgsConstructor
public enum Status {
    PROCESSING("처리중"),
    COMPLETE("처리완료");

    private final String text;
}
