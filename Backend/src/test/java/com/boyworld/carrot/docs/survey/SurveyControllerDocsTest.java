package com.boyworld.carrot.docs.survey;

import com.boyworld.carrot.api.controller.survey.SurveyController;
import com.boyworld.carrot.api.controller.survey.response.CreateSurveyResponse;
import com.boyworld.carrot.api.controller.survey.response.SurveyCountResponse;
import com.boyworld.carrot.api.controller.survey.response.SurveyDetailsResponse;
import com.boyworld.carrot.api.service.survey.SurveyQueryService;
import com.boyworld.carrot.api.service.survey.SurveyService;
import com.boyworld.carrot.api.service.survey.dto.CreateSurveyDto;
import com.boyworld.carrot.api.service.survey.dto.SurveyCountDto;
import com.boyworld.carrot.api.service.survey.dto.SurveyDetailDto;
import com.boyworld.carrot.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SurveyControllerDocsTest.class)
public class SurveyControllerDocsTest extends RestDocsSupport {

    private final SurveyService surveyService = mock(SurveyService.class);
    private final SurveyQueryService surveyQueryService = mock(SurveyQueryService.class);

    @Override
    protected Object initController() { return new SurveyController(surveyService, surveyQueryService); }

    @DisplayName("수요조사 제출 API")
    @Test
    @WithMockUser(roles = "CLIENT")
    void submitSurvey() throws Exception {
        CreateSurveyResponse response = CreateSurveyResponse.builder()
                .categoryName("한식")
                .nickname("별명")
                .sido("광주광역시")
                .sigungu("광산구")
                .dong("장덕동")
                .build();

        given(surveyService.createSurvey(any(CreateSurveyDto.class), anyString()))
                .willReturn(response);

        CreateSurveyDto dto = CreateSurveyDto.builder()
                .categoryId(1L)
                .latitude(new BigDecimal("35.19684"))
                .longitude(new BigDecimal("126.8108"))
                .content("해줘잉")
                .build();

        String jsonRequest = objectMapper.writeValueAsString(dto);

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

    @DisplayName("수요조사 리스트 API")
    @Test
    void getSurveyCount() throws Exception {

        SurveyCountDto item1 = SurveyCountDto.builder()
                .categoryId(1L)
                .categoryName("한식")
                .surveyCount(1)
                .build();

        SurveyCountDto item2 = SurveyCountDto.builder()
                .categoryId(7L)
                .categoryName("치킨/닭강정")
                .surveyCount(8)
                .build();

        SurveyCountDto item3 = SurveyCountDto.builder()
                .categoryId(9L)
                .categoryName("카페/베이커리")
                .surveyCount(3)
                .build();

        List<SurveyCountDto> items = List.of(item1, item2, item3);

        SurveyCountResponse response = SurveyCountResponse.builder()
                .surveyCounts(items)
                .build();

        given(surveyQueryService.getSurveyCount(anyString(), anyString(), anyString()))
                .willReturn(response);

        mockMvc.perform(
                get("/survey/list")
                        .param("sido", "광주광역시")
                        .param("sigungu", "광산구")
                        .param("dong", "장덕동")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("search-survey-list",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("sido")
                                        .description("시도"),
                                parameterWithName("sigungu")
                                        .description("시군구"),
                                parameterWithName("dong")
                                        .description("읍면동")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data.surveyCounts[].categoryId").type(JsonFieldType.NUMBER)
                                        .description("카테고리 아이디"),
                                fieldWithPath("data.surveyCounts[].categoryName").type(JsonFieldType.STRING)
                                        .description("카테고리 이름"),
                                fieldWithPath("data.surveyCounts[].surveyCount").type(JsonFieldType.NUMBER)
                                        .description("수요조사 갯수")
                        )
                ));
    }

    @DisplayName("수요조사 상세내용 리스트 API")
    @Test
    void getSurveyDetails() throws Exception {
        SurveyDetailDto item1 = SurveyDetailDto.builder()
                .surveyId(1L)
                .nickname("당근당근")
                .content("출근길에 아아가 있었으면 좋겠어요")
                .createdTime(LocalDateTime.now().minusMonths(1).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();

        SurveyDetailDto item2 = SurveyDetailDto.builder()
                .surveyId(6L)
                .nickname("트럭")
                .content("커피팔아주세요")
                .createdTime(LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();

        SurveyDetailDto item3 = SurveyDetailDto.builder()
                .surveyId(11L)
                .nickname("바니")
                .content("붕어빵 먹고싶어요")
                .createdTime(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();

        List<SurveyDetailDto> items = List.of(item1, item2, item3);
        Long cid = 9L;
        String cname = "카페/베이커리";

        SurveyDetailsResponse response = SurveyDetailsResponse.builder()
                .categoryId(cid)
                .categoryName(cname)
                .surveyDetails(items)
                .hasNext(false)
                .build();

        given(surveyQueryService.getSurveyDetails(anyLong(), anyString(), anyString(), anyString(), anyLong()))
                .willReturn(response);

        mockMvc.perform(
                        get("/survey/list/{categoryId}", cid)
                                .param("sido", "광주광역시")
                                .param("sigungu", "광산구")
                                .param("dong", "장덕동")
                                .param("lastSurveyId", "0")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("search-survey-detail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("categoryId")
                                        .description("카테고리 아이디")
                        ),
                        queryParameters(
                                parameterWithName("sido")
                                        .description("시도"),
                                parameterWithName("sigungu")
                                        .description("시군구"),
                                parameterWithName("dong")
                                        .description("읍면동"),
                                parameterWithName("lastSurveyId")
                                        .description("마지막으로 조회한 수요조사 아이디")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data.categoryId").type(JsonFieldType.NUMBER)
                                        .description("카테고리 아이디"),
                                fieldWithPath("data.categoryName").type(JsonFieldType.STRING)
                                        .description("카테고리 이름"),
                                fieldWithPath("data.surveyDetails[].surveyId").type(JsonFieldType.NUMBER)
                                        .description("수요조사 ID"),
                                fieldWithPath("data.surveyDetails[].nickname").type(JsonFieldType.STRING)
                                        .description("작성자 닉네임"),
                                fieldWithPath("data.surveyDetails[].content").type(JsonFieldType.STRING)
                                        .description("수요조사 상세내용"),
                                fieldWithPath("data.surveyDetails[].createdTime").type(JsonFieldType.STRING)
                                        .description("수요조사 작성시간"),
                                fieldWithPath("data.hasNext").type(JsonFieldType.BOOLEAN)
                                        .description("다음 페이지 존재 여부")
                        )
                ));
    }

    @DisplayName("수요조사 삭제 API")
    @Test
    @WithMockUser(roles = "CLIENT")
    void deleteSurvey() throws Exception {
        Long sid = 11L;

        given(surveyService.deleteSurvey(anyLong(), anyString()))
                .willReturn(sid);

        mockMvc.perform(
                        put("/survey/remove/{surveyId}", sid)
                                .header("Authentication", "authentication")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("delete-survey",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("surveyId")
                                        .description("수요조사 아이디")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER)
                                        .description("수요조사 아이디")
                        )
                ));
    }
}
