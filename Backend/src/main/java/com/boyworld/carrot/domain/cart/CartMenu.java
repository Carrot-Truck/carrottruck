package com.boyworld.carrot.domain.cart;

import com.boyworld.carrot.domain.TimeBaseEntity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisHash;

@Slf4j
@Getter
@NoArgsConstructor
@RedisHash("cartmenu")
public class CartMenu extends TimeBaseEntity {

}
