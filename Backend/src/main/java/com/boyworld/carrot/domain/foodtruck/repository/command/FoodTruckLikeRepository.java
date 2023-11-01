package com.boyworld.carrot.domain.foodtruck.repository.command;

import com.boyworld.carrot.domain.foodtruck.FoodTruckLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 푸드트럭 찜 CUD 레포지토리
 * 
 * @author 최영환
 */
@Repository
public interface FoodTruckLikeRepository extends JpaRepository<FoodTruckLike, Long> {
}
