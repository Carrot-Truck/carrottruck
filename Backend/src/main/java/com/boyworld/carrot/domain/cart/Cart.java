package com.boyworld.carrot.domain.cart;


import com.boyworld.carrot.domain.TimeBaseEntity;
import com.boyworld.carrot.domain.member.Member;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisHash;

/**
 * 장바구니 엔티티
 *
 * @author 김동현
 */
@Slf4j
@Getter
@NoArgsConstructor
@RedisHash("cart")
public class Cart extends TimeBaseEntity {
    @Id
    private Long memberId;
    private Long foodTruckId;
    private Integer totalPrice;

    @Builder
    public Cart(Long memberId, Long foodTruckId, Integer totalPrice) {
        this.memberId = memberId;
        this.foodTruckId = foodTruckId;
        this.totalPrice = totalPrice;
    }

}
