package com.boyworld.carrot.domain.survey.repository;

import com.boyworld.carrot.domain.survey.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 수요조사 CUD 레포지토리
 *
 * @author 양진형
 */
@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
}
