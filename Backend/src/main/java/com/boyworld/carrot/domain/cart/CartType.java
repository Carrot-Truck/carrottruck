package com.boyworld.carrot.domain.cart;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CartType {
    CART("CART"),
    CARTMENU("CARTMENU"),
    CARTMENUOPTION("CARTMENUOPTION");

    private final String text;

}
