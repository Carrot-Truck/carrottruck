package com.boyworld.carrot.domain.cart;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

import static com.boyworld.carrot.domain.StringConstants.NO_IMG;

/**
 * 장바구니-메뉴 엔티티
 *
 * @author 김동현
 */
@Slf4j
@Getter
@NoArgsConstructor
@RedisHash("cartMenu")
public class CartMenu {

    @Id
    private String id;
    private String cartId;
    private Long menuId;
    private String name;
    private Integer price;
    private Integer cartMenuTotalPrice;
    private Integer quantity;
    private String menuImageUrl;
    private List<String> cartMenuOptionIds;

    @Builder
    public CartMenu(String id, String cartId, Long menuId, String name, Integer price, Integer cartMenuTotalPrice, Integer quantity, String menuImageUrl, List<String> cartMenuOptionIds) {
        this.id = id;
        this.cartId = cartId;
        this.menuId = menuId;
        this.name = name;
        this.price = price;
        this.cartMenuTotalPrice = cartMenuTotalPrice;
        this.quantity = quantity;
        this.menuImageUrl = menuImageUrl != null ? menuImageUrl : NO_IMG;
        this.cartMenuOptionIds = cartMenuOptionIds;
    }

    public void incrementCartMenuQuantity(Integer quantity) {
        this.quantity += quantity;
    }

    public void decrementCartMenuQuantity() {
        this.quantity -= 1;
    }
}
