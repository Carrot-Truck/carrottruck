package com.boyworld.carrot.domain.cart;

import com.boyworld.carrot.domain.TimeBaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * 장바구니-메뉴 엔티티
 *
 * @author 김동현
 */
@Slf4j
@Getter
@NoArgsConstructor
@RedisHash("cartMenu")
public class CartMenu extends TimeBaseEntity {

    @Id
    private Long id;
    private String cartId;
    private Long menuId;
    private String name;
    private Integer price;
    private Boolean soldOut;
    private Integer quantity;

    @Builder
    public CartMenu(Long id, String cartId, Long menuId, String name, Integer price, Boolean soldOut, Integer quantity) {
        this.id = id;
        this.cartId = cartId;
        this.menuId = menuId;
        this.name = name;
        this.price = price;
        this.soldOut = soldOut;
        this.quantity = quantity;
    }
}
