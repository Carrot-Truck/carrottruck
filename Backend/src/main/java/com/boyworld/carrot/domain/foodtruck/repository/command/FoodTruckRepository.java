package com.boyworld.carrot.domain.foodtruck.repository.command;

import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 푸드트럭 레포지토리
 *
 * @author 최영환
 */
@Repository
public interface FoodTruckRepository extends JpaRepository<FoodTruck, Long> {
}
