package com.boyworld.carrot.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 회원 등급
 *
 * @author 최영환
 */
@Getter
@RequiredArgsConstructor
public enum Role {
    CLIENT("고객"),
    VENDOR("사업자"),
    ADMIN("관리자");

    private final String text;
}
