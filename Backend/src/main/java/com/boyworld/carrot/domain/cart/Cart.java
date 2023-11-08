package com.boyworld.carrot.domain.cart;


import com.boyworld.carrot.domain.TimeBaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

/**
 * 장바구니 엔티티
 *
 * @author 김동현
 */
@Slf4j
@Getter
@NoArgsConstructor
@RedisHash("cart")
public class Cart {
    @Id
    private String id;
    private Long foodTruckId;
    private String foodTruckName;
    private Integer totalPrice;
    private List<String> cartMenuIds;

    @Builder
    public Cart(String id, Long foodTruckId, String foodTruckName, Integer totalPrice, List<String> cartMenuIds) {
        this.id = id;
        this.foodTruckId = foodTruckId;
        this.foodTruckName = foodTruckName;
        this.totalPrice = totalPrice;
        this.cartMenuIds = cartMenuIds;
    }



    public void incrementCartTotalPrice(Integer menuPrice) {
        this.totalPrice += menuPrice;
    }

    public void decrementCartTotalPrice(Integer menuPrice) {
        this.totalPrice -= menuPrice;
    }
    public void addCartMenuIds(String cartMenuId) {
        this.cartMenuIds.add(cartMenuId);
    }
    public void removeCartMenuIds(String cartMenuId) {
        this.cartMenuIds.remove(cartMenuId);
    }

}


