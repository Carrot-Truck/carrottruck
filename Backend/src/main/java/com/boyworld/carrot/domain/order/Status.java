package com.boyworld.carrot.domain.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 주문 상태
 *
 * @author 박은규
 */
@Getter
@RequiredArgsConstructor
public enum Status {

    PENDING("대기중"),
    PROCESSING("준비중"),
    COMPLETE("주문 완료"),
    DECLINED("거절"),
    CANCELLED("취소");

    private final String text;
}
