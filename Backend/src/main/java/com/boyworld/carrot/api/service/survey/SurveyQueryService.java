package com.boyworld.carrot.api.service.survey;

import com.boyworld.carrot.api.controller.survey.response.SurveyCountResponse;
import com.boyworld.carrot.api.controller.survey.response.SurveyDetailsResponse;
import com.boyworld.carrot.api.service.survey.dto.SurveyCountDto;
import com.boyworld.carrot.api.service.survey.dto.SurveyDetailDto;
import com.boyworld.carrot.domain.foodtruck.Category;
import com.boyworld.carrot.domain.foodtruck.repository.command.CategoryRepository;
import com.boyworld.carrot.domain.survey.repository.SurveyQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static com.boyworld.carrot.domain.SizeConstants.PAGE_SIZE;

/**
 * 수요조사 쿼리 서비스
 *
 * @author 양진형
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SurveyQueryService {

    private final SurveyQueryRepository surveyQueryRepository;

    private final CategoryRepository categoryRepository;

    /**
     * 수요조사 확인
     *
     * @param sido 시도
     * @param sigungu 시군구
     * @param dong 읍면동
     * @return 각 카테고리별 최근 6개월 수요조사 건수
     */
    public SurveyCountResponse getSurveyCount(String sido, String sigungu, String dong) {
        List<SurveyCountDto> surveyCounts = surveyQueryRepository.countSurvey(sido, sigungu, dong);

        return SurveyCountResponse.of(surveyCounts);
    }

    /**
     * 카테고리별 수요조사 상세내용 리스트
     * (1페이지당 10건)
     *
     * @param categoryId 카테고리 ID
     * @param sido 시도
     * @param sigungu 시군구
     * @param dong 읍면동
     * @param lastSurveyId 마지막으로 조회한 수요조사 아이디
     * @return 각 카테고리별 최근 6개월 수요조사 상세내용 리스트
     */
    public SurveyDetailsResponse getSurveyDetails(Long categoryId, String sido, String sigungu, String dong, Long lastSurveyId) {
        List<SurveyDetailDto> surveyDetails = surveyQueryRepository.getSurvey(categoryId, sido, sigungu, dong, lastSurveyId);

        String categoryName = findCategoryById(categoryId).getName();

        boolean hasNext = checkHasNext(surveyDetails);

        return SurveyDetailsResponse.of(categoryId, categoryName, surveyDetails, hasNext);
    }

    private Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 카테고리입니다."));
    }

    private boolean checkHasNext(List<SurveyDetailDto> surveyDetails) {
        if (surveyDetails.size() > PAGE_SIZE) {
            surveyDetails.remove(PAGE_SIZE);
            return true;
        }
        return false;
    }
}
