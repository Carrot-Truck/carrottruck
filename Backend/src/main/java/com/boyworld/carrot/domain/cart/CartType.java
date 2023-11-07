package com.boyworld.carrot.domain.cart;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CartType {
    CART("카트"),
    CARTMENU("카트메뉴"),
    CARTMENUOPTION("카트메뉴옵션");

    private final String text;

}
