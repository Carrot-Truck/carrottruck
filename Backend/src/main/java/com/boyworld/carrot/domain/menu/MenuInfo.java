package com.boyworld.carrot.domain.menu;

import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 메뉴, 메뉴옵션 상세정보 값 타입 클래스
 *
 * @author 최영환
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuInfo {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(columnDefinition = "text")
    private String description;

    @Column(nullable = false)
    private Boolean soldOut;

    @Builder
    public MenuInfo(String name, Integer price, String description, Boolean soldOut) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.soldOut = soldOut;
    }
}
