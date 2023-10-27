package com.boyworld.carrot.domain.foodtruck.repository;

import com.boyworld.carrot.domain.foodtruck.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 푸드트럭 스케쥴 레포지토리
 *
 * @author 최영환
 */
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
