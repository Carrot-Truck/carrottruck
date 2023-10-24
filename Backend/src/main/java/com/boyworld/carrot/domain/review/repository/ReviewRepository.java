package com.boyworld.carrot.domain.review.repository;

import com.boyworld.carrot.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Review CRUD Repository
 *
 * @author Gunhoo Park
 */
public interface ReviewRepository extends JpaRepository<Review, Long> {

}
