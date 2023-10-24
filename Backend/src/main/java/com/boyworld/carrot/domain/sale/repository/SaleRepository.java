package com.boyworld.carrot.domain.sale.repository;

import com.boyworld.carrot.domain.sale.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * 영업 CRUD 레포지토리
 *
 * @author 박은규
 */
public interface SaleRepository extends JpaRepository<Sale, Long> {

//    Optional<List<Sales>> findAllByFoodTruck(FoodTruck foodTruck);
}
