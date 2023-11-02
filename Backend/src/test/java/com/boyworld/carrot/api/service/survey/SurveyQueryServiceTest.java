package com.boyworld.carrot.api.service.survey;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.controller.survey.response.SurveyCountResponse;
import com.boyworld.carrot.api.controller.survey.response.SurveyDetailsResponse;
import com.boyworld.carrot.domain.foodtruck.Category;
import com.boyworld.carrot.domain.foodtruck.repository.command.CategoryRepository;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
import com.boyworld.carrot.domain.survey.Survey;
import com.boyworld.carrot.domain.survey.repository.SurveyRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

/**
 * 수요조사 쿼리 서비스 테스트
 * 
 * @author 양진형
 */
@Slf4j
public class SurveyQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private SurveyQueryService surveyQueryService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @DisplayName("카테고리별 수요조사 갯수 목록 테스트")
    @Test
    void getSurveyCount() throws Exception {
        Member member = createMember(Role.CLIENT, true);

        Category category1 = createCategory("한식");
        Category category2 = createCategory("카페/베이커리");
        Category category3 = createCategory("일식");
        List<Category> categories = List.of(category1, category2, category3);

        String sido = "광주광역시";
        String sigungu = "광산구";
        String[] dong = {"장덕동", "수완동"};
        int cateCount = categories.size();
        int dongCount = dong.length;
        for (int i = 0; i < 100; i++) {
            int ri = (int) Math.floor(Math.random() * cateCount);
            int di = (int) Math.floor(Math.random() * dongCount);
            Survey survey = createSurvey(categories.get(ri), member, sido, sigungu, dong[di]);
        }

        SurveyCountResponse response = surveyQueryService.getSurveyCount(sido, sigungu, dong[0]);

        log.debug("getSurveyCount#response {}={}", dong[0], response);
    }

    @DisplayName("특정 카테고리 수요조사 상세내용 리스트 조회")
    @Test
    void getSurveyDetails() throws Exception {
        Member member = createMember(Role.CLIENT, true);

        Category category = createCategory("한식");

        String sido = "광주광역시";
        String sigungu = "광산구";
        String dong = "장덕동";
        for (int i = 0; i < 100; i++) {
            Survey survey = createSurvey(category, member, sido, sigungu, dong);
        }

        Long lastId = 0L;
        int callCount = 0;
        SurveyDetailsResponse response = surveyQueryService.getSurveyDetails(category.getId(), sido, sigungu, dong, lastId);
        log.debug("#{}:getSurveyDetails#SurveyDetailsResponse={}", ++callCount, response);
        while (response.getHasNext()) {
            lastId = response.getSurveyDetails().get(response.getSurveyDetails().size() - 1).getSurveyId();
            response = surveyQueryService.getSurveyDetails(category.getId(), sido, sigungu, dong, lastId);
            log.debug("#{}:getSurveyDetails#SurveyDetailsResponse={}", ++callCount, response);
        }
    }

    private Member createMember(Role role, boolean active) {
        Member member = Member.builder()
                .email("ssafy@ssafy.com")
                .nickname("매미킴")
                .encryptedPwd(passwordEncoder.encode("ssafy1234"))
                .name("김동현")
                .phoneNumber("010-1234-5678")
                .role(role)
                .active(active)
                .build();
        return memberRepository.save(member);
    }

    private Category createCategory(String name) {
        Category category = Category.builder()
                .name(name)
                .active(true)
                .build();
        return categoryRepository.save(category);
    }

    private Survey createSurvey(Category category, Member member, String sido, String sigungu, String dong) {
        Survey survey = Survey.builder()
                .category(category)
                .member(member)
                .sido(sido)
                .sigungu(sigungu)
                .dong(dong)
                .content("내용!!")
                .active(true)
                .build();
        return surveyRepository.save(survey);
    }
}
