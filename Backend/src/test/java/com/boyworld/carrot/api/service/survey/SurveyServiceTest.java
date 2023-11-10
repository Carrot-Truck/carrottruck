package com.boyworld.carrot.api.service.survey;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.controller.survey.request.CreateSurveyRequest;
import com.boyworld.carrot.api.controller.survey.response.CreateSurveyResponse;
import com.boyworld.carrot.domain.foodtruck.Category;
import com.boyworld.carrot.domain.foodtruck.repository.command.CategoryRepository;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 수요조사 CUD 테스트
 *
 * @author 양진형
 */
@Slf4j
@Transactional
public class SurveyServiceTest extends IntegrationTestSupport {

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CategoryRepository categoryRepository;

    @DisplayName("수요조사 생성 테스트")
    @Test
    void createSurvey() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Member member = createMember(Role.CLIENT, true);
        Category category = createCategory("고기/구이");

        CreateSurveyRequest request = CreateSurveyRequest.builder()
            .categoryId(category.getId())
            .latitude(new BigDecimal("35.19684"))
            .longitude(new BigDecimal("126.8108"))
            .content("해줘잉")
            .build();

        log.debug("createSurvey#request={}", request);

        CreateSurveyResponse response = surveyService.createSurvey(request.toCreateSurveyDto(), "ssafy@gmail.com");

        log.debug("createSurvey#response={}", response);
    }

    private Member createMember(Role role, boolean active) {
        Member member = Member.builder()
                .email("ssafy@gmail.com")
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
}
