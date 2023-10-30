package com.boyworld.carrot.docs.survey;

import com.boyworld.carrot.api.controller.survey.SurveyController;
import com.boyworld.carrot.api.controller.survey.request.CreateSurveyRequest;
import com.boyworld.carrot.api.controller.survey.response.CreateSurveyResponse;
import com.boyworld.carrot.api.service.survey.SurveyQueryService;
import com.boyworld.carrot.api.service.survey.SurveyService;
import com.boyworld.carrot.api.service.survey.dto.CreateSurveyDto;
import com.boyworld.carrot.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SurveyControllerDocsTest.class)
public class SurveyControllerDocsTest extends RestDocsSupport {

    private final SurveyService surveyService = mock(SurveyService.class);
    private final SurveyQueryService surveyQueryService = mock(SurveyQueryService.class);

    @Override
    protected Object initController() { return new SurveyController(surveyService, surveyQueryService); }

    @DisplayName("수요조사 제출 API")
    @Test
    void submitSurvey() throws Exception {
        CreateSurveyResponse response = CreateSurveyResponse.builder()
                .categoryName("한식")
                .nickname("별명")
                .sido("광주광역시")
                .sigungu("광산구")
                .dong("장덕동")
                .build();

        given(surveyService.createSurvey(any(CreateSurveyRequest.class)))
                .willReturn(response);

        CreateSurveyRequest request = CreateSurveyRequest.builder()
                .categoryId(1L)
                .memberId(1L)
                .latitude(new BigDecimal("35.19684"))
                .longitude(new BigDecimal("126.8108"))
                .content("해줘잉")
                .build();

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                post("/survey/submit")
                        .content(jsonRequest)
                        .header("Authentication", "authentication")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated())
                .andDo(document("create-survey",
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("categoryId").type(JsonFieldType.NUMBER)
                                        .description("카테고리 아이디"),
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER)
                                        .description("멤버 아이디"),
                                fieldWithPath("latitude").type(JsonFieldType.NUMBER)
                                        .description("위도"),
                                fieldWithPath("longitude").type(JsonFieldType.NUMBER)
                                        .description("경도"),
                                fieldWithPath("content").type(JsonFieldType.STRING)
                                        .description("상세 내용")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data.categoryName").type(JsonFieldType.STRING)
                                        .description("카테고리 이름"),
                                fieldWithPath("data.nickname").type(JsonFieldType.STRING)
                                        .description("회원 별명"),
                                fieldWithPath("data.sido").type(JsonFieldType.STRING)
                                        .description("시도"),
                                fieldWithPath("data.sigungu").type(JsonFieldType.STRING)
                                        .description("시군구"),
                                fieldWithPath("data.dong").type(JsonFieldType.STRING)
                                        .description("동")
                        )
                ));
    }
}
