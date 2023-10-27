package com.boyworld.carrot.domain.review.repository;

import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.review.Review;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Review CRUD Repository
 *
 * @author Gunhoo Park
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<List<Review>> findByMember(Member memberId);

    Optional<List<Review>> findByFoodTruck(FoodTruck foodTruck);
}
