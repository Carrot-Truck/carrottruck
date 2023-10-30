package com.boyworld.carrot.domain.cart;

import com.boyworld.carrot.domain.TimeBaseEntity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisHash;

/**
 * 장바구니-메뉴옵션 엔티티
 *
 * @author 김동현
 */
@Slf4j
@Getter
@NoArgsConstructor
@RedisHash("cartMenuOption")
public class CartMenuOption extends TimeBaseEntity {

    @Id
    private Long id;
    private Long cartMenuId;
    private Long menuOptionId;
    private Integer quantity;

    @Builder
    public CartMenuOption(Long id, Long cartMenuId, Long menuOptionId, Integer quantity) {
        this.id = id;
        this.cartMenuId = cartMenuId;
        this.menuOptionId = menuOptionId;
        this.quantity = quantity;
    }
}
