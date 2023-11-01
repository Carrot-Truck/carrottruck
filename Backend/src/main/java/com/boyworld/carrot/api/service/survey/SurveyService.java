package com.boyworld.carrot.api.service.survey;

import com.boyworld.carrot.api.controller.survey.request.CreateSurveyRequest;
import com.boyworld.carrot.api.controller.survey.response.CreateSurveyResponse;
import com.boyworld.carrot.api.service.geocoding.GeocodingService;
import com.boyworld.carrot.domain.foodtruck.Category;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
import com.boyworld.carrot.domain.survey.Survey;
import com.boyworld.carrot.domain.survey.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

/**
 * 수요조사 서비스
 *
 * @author 양진형
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class SurveyService {

    private final SurveyRepository surveyRepository;

    private final MemberRepository memberRepository;

    private final GeocodingService geocodingService;

    /**
     * 설문 제출
     *
     * @param request 제출 수요조사 정보
     * @return 제출 수요조사 정보
     */
    public CreateSurveyResponse createSurvey(CreateSurveyRequest request) {
        Member member = findMemberById(request.getMemberId());
        checkActiveMember(member);

        Category category = null;

        String[] address = geocodingService.reverseGeocoding(request.getLatitude(), request.getLongitude(), "legalcode").get("legalcode").split(" ");

        Survey survey = saveSurvey(category, member, address[0], address[1], address[2], request.getContent());

        return CreateSurveyResponse.of(survey);
    }

    /**
     * 수요조사 삭제
     *
     * @param surveyId 삭제할 수요조사 ID
     * @return 삭제한 수요조사 ID
     */
    public Long deleteSurvey(Long surveyId, String email) {
        Survey survey = findSurveyById(surveyId);

        if (!survey.getMember().getEmail().equals(email)) {
            // 로그인한 회원과 수요조사 작성 회원 불일치
            return null;
        }

        if (!survey.getMember().getActive()) {
            // 탈퇴한 회원이 작성한 수요조사
            return null;
        }

        survey.deActivate();

        return surveyId;
    }

    private Member findMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
    }

    private void checkActiveMember(Member member) {
        if (!member.getActive()) {
            throw new NoSuchElementException("이미 탈퇴한 회원입니다.");
        }
    }
    
//    private Category findCategoryById(Long id) {
//        return categoryRepository.findById(id)
//                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 카테고리입니다."));
//    }
    
    private Survey findSurveyById(Long id) {
        return surveyRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 수요조사입니다."));
    }

    private Survey saveSurvey(Category category, Member member, String sido, String sigungu, String dong, String content) {
        Survey survey = Survey.builder()
                .category(category)
                .member(member)
                .sido(sido)
                .sigungu(sigungu)
                .dong(dong)
                .content(content)
                .active(true)
                .build();
        return surveyRepository.save(survey);
    }
}
