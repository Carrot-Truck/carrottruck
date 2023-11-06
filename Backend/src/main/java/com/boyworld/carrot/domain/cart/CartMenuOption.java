package com.boyworld.carrot.domain.cart;

import com.boyworld.carrot.domain.TimeBaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
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
public class CartMenuOption {

    @Id
    private String id;
    private String cartMenuId;
    private Long menuOptionId;
    private String name;
    private Integer price;
//    private Boolean soldOut; 조회시 확인

    @Builder
    public CartMenuOption(String id, String cartMenuId, Long menuOptionId, String name, Integer price) {
        this.id = id;
        this.cartMenuId = cartMenuId;
        this.menuOptionId = menuOptionId;
        this.name = name;
        this.price = price;
    }
}
