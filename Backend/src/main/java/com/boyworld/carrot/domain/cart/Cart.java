package com.boyworld.carrot.domain.cart;


import com.boyworld.carrot.domain.TimeBaseEntity;
import com.boyworld.carrot.domain.member.Member;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor
@RedisHash("cart")
public class Cart extends TimeBaseEntity {
    @Id
    private Long id;
    private Member member;
    private Integer totalPrice;

    @Builder
    private Cart(Long id, Member member, Integer totalPrice) {
        this.id = id;
        this.member = member;
        this.totalPrice = totalPrice;
    }
}
