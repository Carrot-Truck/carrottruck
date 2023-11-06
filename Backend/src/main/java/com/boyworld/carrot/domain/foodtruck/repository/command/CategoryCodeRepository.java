package com.boyworld.carrot.domain.foodtruck.repository.command;

import com.boyworld.carrot.domain.foodtruck.CategoryCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 카테고리 코드 레포지토리 (테스트용)
 *
 * @author 양진형
 */
@Repository
public interface CategoryCodeRepository extends JpaRepository<CategoryCode, Long> {
}
