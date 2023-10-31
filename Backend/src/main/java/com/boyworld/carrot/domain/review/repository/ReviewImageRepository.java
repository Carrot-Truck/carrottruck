package com.boyworld.carrot.domain.review.repository;

import com.boyworld.carrot.domain.review.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {

}
