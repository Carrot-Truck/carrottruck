package com.boyworld.carrot.domain.adong.repository;

import com.boyworld.carrot.domain.adong.AdongCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 행정동 코드 CUD 레포지토리
 * (테스트용)
 * 
 * @author 양진형
 */
@Repository
public interface AdongCodeRepository extends JpaRepository<AdongCode, Long> {
}
