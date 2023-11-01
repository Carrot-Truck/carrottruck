package com.boyworld.carrot.api.controller.survey;

import com.boyworld.carrot.api.ApiResponse;
import com.boyworld.carrot.api.controller.survey.request.CreateSurveyRequest;
import com.boyworld.carrot.api.controller.survey.response.CreateSurveyResponse;
import com.boyworld.carrot.api.controller.survey.response.SurveyDetailsResponse;
import com.boyworld.carrot.api.controller.survey.response.SurveyCountResponse;
import com.boyworld.carrot.api.service.survey.SurveyQueryService;
import com.boyworld.carrot.api.service.survey.SurveyService;
import com.boyworld.carrot.security.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 수요조사 API 컨트롤러
 *
 * @author 양진형
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/survey")
public class SurveyController {

    private final SurveyService surveyService;
    private final SurveyQueryService surveyQueryService;

    /**
     * 수요조사 제출 API
     *
     * @param request 수요조사 카테고리, 작성자 ID, 위도, 경도, 내용
     * @return 수요조사 행정동, 내용
     */
    @PostMapping("/submit")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateSurveyResponse> submitSurvey(@Valid @RequestBody CreateSurveyRequest request) {
        log.debug("SurveyController#submitSurvey called !!!");
        log.debug("CreateSurveyRequest={}", request);

        CreateSurveyResponse response = surveyService.createSurvey(request);
        log.debug("CreateSurveyResponse={}", response);

        return ApiResponse.created(response);
    }

    /**
     * 수요조사 리스트 API
     *
     * @param sido 시도
     * @param sigungu 시군구
     * @param dong 읍면동
     * @return 각 카테고리별 수요조사 개수(최근 6개월)
     */
    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<SurveyCountResponse> getSurveyCount(@RequestParam String sido,
                                                           @RequestParam String sigungu,
                                                           @RequestParam String dong) {
        log.debug("SurveyController#getSurveyCount called !!!");
        log.debug("Address={} {} {}", sido, sigungu, dong);

        SurveyCountResponse response = surveyQueryService.getSurveyCount(sido, sigungu, dong);
        log.debug("SurveyCountResponse={}", response);

        return ApiResponse.ok(response);
    }

    /**
     * 각 카테고리별 수요조사 상세내용 리스트 API
     *
     * @param categoryId 카테고리 ID
     * @param sido 시도
     * @param sigungu 시군구
     * @param dong 읍면동
     * @param page 페이지
     * @return 수요조사 상세내용 리스트(최근 6개월)
     */
    @GetMapping("/list/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<SurveyDetailsResponse> getSurveyDetails(@PathVariable Long categoryId,
                                                               @RequestParam String sido,
                                                               @RequestParam String sigungu,
                                                               @RequestParam String dong,
                                                               @RequestParam(defaultValue = "1") Integer page) {
        log.debug("SurveyController#getSurveyDetails called !!!");
        log.debug("CategoryID={}, Address={} {} {}, Page={}", categoryId, sido, sigungu, dong, page);

        SurveyDetailsResponse response = surveyQueryService.getSurveyDetails(categoryId, sido, sigungu, dong, page);
        log.debug("SurveyDetailsResponse={}", response);

        return ApiResponse.ok(response);
    }

    /**
     * 수요조사 삭제 API
     *
     * @param surveyId 삭제할 수요조사 ID
     * @return 삭제한 수요조사 ID
     */
    @DeleteMapping("/remove/{surveyId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Long> deleteSurvey(@PathVariable Long surveyId) {
        log.debug("SurveyController#deleteSurvey called !!!");
        log.debug("SurveyID={}", surveyId);

        String email = SecurityUtil.getCurrentLoginId();

        Long response = surveyService.deleteSurvey(surveyId, email);
        log.debug("DeletedID={}", response);

        return ApiResponse.ok(response);
    }

}
