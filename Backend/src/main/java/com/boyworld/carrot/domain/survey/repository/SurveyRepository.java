package com.boyworld.carrot.domain.survey.repository;

import com.boyworld.carrot.domain.survey.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 수요조사 CRUD 레포지토리
 *
 * @author 양진형
 */
public interface SurveyRepository extends JpaRepository<Survey, Long> {

    Optional<Survey> findById(Long id);
}
