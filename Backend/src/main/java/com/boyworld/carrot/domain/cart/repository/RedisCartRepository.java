package com.boyworld.carrot.domain.cart.repository;

import com.boyworld.carrot.domain.cart.Cart;
import org.springframework.data.repository.CrudRepository;

public interface RedisCartRepository extends CrudRepository<Cart, String> {

}
