package com.boyworld.carrot.domain.cart;

import com.boyworld.carrot.domain.TimeBaseEntity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private Long cartId;
    private Long menuId;
    private Integer quantity;

    @Builder
    public CartMenu(Long id, Long cartId, Long menuId, Integer quantity) {
        this.id = id;
        this.cartId = cartId;
        this.menuId = menuId;
        this.quantity = quantity;
    }
}
