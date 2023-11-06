package com.boyworld.carrot.docs.analysis;

import com.boyworld.carrot.api.controller.analysis.AnalysisController;
import com.boyworld.carrot.api.controller.analysis.response.StoreAnalysisResponse;
import com.boyworld.carrot.api.service.analysis.AnalysisQueryService;
import com.boyworld.carrot.api.service.analysis.dto.StoreAnalysisDto;
import com.boyworld.carrot.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.restdocs.payload.JsonFieldType;

import java.math.BigDecimal;
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

@WebMvcTest(AnalysisControllerDocsTest.class)
public class AnalysisControllerDocsTest extends RestDocsSupport {

    private final AnalysisQueryService analysisQueryService = mock(AnalysisQueryService.class);

    @Override
    protected Object initController() { return new AnalysisController(analysisQueryService); }

    @DisplayName("상권분석 API")
    @Test
    void getStoreAnalysis() throws Exception {

        StoreAnalysisDto item1 = StoreAnalysisDto.builder()
                .storeName("빨간곱창")
                .latitude(new BigDecimal("126.830916831796"))
                .longitude(new BigDecimal("35.1918574116446"))
                .sido("광주광역시")
                .sigungu("광산구")
                .dong("수완동")
                .indsMclsCd("I201")
                .indsMclsNm("한식")
                .indsSclsCd("I20109")
                .indsSclsNm("곱창 전골/구이")
                .build();

        StoreAnalysisDto item2 = StoreAnalysisDto.builder()
                .storeName("국수나무장덕점")
                .latitude(new BigDecimal("126.814597161796"))
                .longitude(new BigDecimal("35.1950879287079"))
                .sido("광주광역시")
                .sigungu("광산구")
                .dong("장덕동")
                .indsMclsCd("I201")
                .indsMclsNm("한식")
                .indsSclsCd("I20105")
                .indsSclsNm("국수/칼국수")
                .build();

        StoreAnalysisDto item3 = StoreAnalysisDto.builder()
                .storeName("호야명품")
                .latitude(new BigDecimal("126.815120825025"))
                .longitude(new BigDecimal("35.1915416402982"))
                .sido("광주광역시")
                .sigungu("광산구")
                .dong("장덕동")
                .indsMclsCd("I201")
                .indsMclsNm("한식")
                .indsSclsCd("I20101")
                .indsSclsNm("백반/한정식")
                .build();

        StoreAnalysisDto item4 = StoreAnalysisDto.builder()
                .storeName("예향정")
                .latitude(new BigDecimal("126.81277113399"))
                .longitude(new BigDecimal("35.191536654724"))
                .sido("광주광역시")
                .sigungu("광산구")
                .dong("장덕동")
                .indsMclsCd("I201")
                .indsMclsNm("한식")
                .indsSclsCd("I20101")
                .indsSclsNm("백반/한정식")
                .build();

        StoreAnalysisDto item5 = StoreAnalysisDto.builder()
                .storeName("백두산")
                .latitude(new BigDecimal("126.817929899939"))
                .longitude(new BigDecimal("35.1833305240406"))
                .sido("광주광역시")
                .sigungu("광산구")
                .dong("신가동")
                .indsMclsCd("I201")
                .indsMclsNm("한식")
                .indsSclsCd("I20101")
                .indsSclsNm("백반/한정식")
                .build();

        List<StoreAnalysisDto> items = List.of(item1, item2, item3, item4, item5);

        StoreAnalysisResponse response = StoreAnalysisResponse.builder()
                .categoryId(1L)
                .categoryName("한식")
                .sido("광주광역시")
                .sigungu("광산구")
                .dong("수완동")
                .radiusCount(115)
                .addressCount(390)
                .stores(items)
                .build();

        given(analysisQueryService.getStoreAnalysis(anyLong(), any(BigDecimal.class), any(BigDecimal.class)))
                .willReturn(response);

        Long cid = 1L;

        mockMvc.perform(
                get("/analysis/store/{categoryId}", cid)
                        .param("latitude", "35.19684")
                        .param("longitude", "126.8108")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-analysis-store-category",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("categoryId")
                                        .description("카테고리 ID")
                        ),
                        queryParameters(
                                parameterWithName("latitude")
                                        .description("현재 위도 위치"),
                                parameterWithName("longitude")
                                        .description("현재 경도 위치")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data.categoryId").type(JsonFieldType.NUMBER)
                                        .description("카테고리 ID"),
                                fieldWithPath("data.categoryName").type(JsonFieldType.STRING)
                                        .description("카테고리 이름"),
                                fieldWithPath("data.sido").type(JsonFieldType.STRING)
                                        .description("분석한 시도"),
                                fieldWithPath("data.sigungu").type(JsonFieldType.STRING)
                                        .description("분석한 시군구"),
                                fieldWithPath("data.dong").type(JsonFieldType.STRING)
                                        .description("분석한 읍면동(법정동)"),
                                fieldWithPath("data.radiusCount").type(JsonFieldType.NUMBER)
                                        .description("반경 범위 내 상가업소 수"),
                                fieldWithPath("data.addressCount").type(JsonFieldType.NUMBER)
                                        .description("동일 행정동 내 상가업소 수"),
                                fieldWithPath("data.stores[].storeName").type(JsonFieldType.STRING)
                                        .description("상가업소의 상호명"),
                                fieldWithPath("data.stores[].latitude").type(JsonFieldType.NUMBER)
                                        .description("상가업소의 위도 좌표값"),
                                fieldWithPath("data.stores[].longitude").type(JsonFieldType.NUMBER)
                                        .description("상가업소의 경도 좌표값"),
                                fieldWithPath("data.stores[].sido").type(JsonFieldType.STRING)
                                        .description("상가업소의 시도 명칭"),
                                fieldWithPath("data.stores[].sigungu").type(JsonFieldType.STRING)
                                        .description("상가업소의 시군구 명칭"),
                                fieldWithPath("data.stores[].dong").type(JsonFieldType.STRING)
                                        .description("상가업소의 읍면동 명칭(법정동)"),
                                fieldWithPath("data.stores[].indsMclsCd").type(JsonFieldType.STRING)
                                        .description("상권업종중분류코드(4자리)"),
                                fieldWithPath("data.stores[].indsMclsNm").type(JsonFieldType.STRING)
                                        .description("상권업종중분류명"),
                                fieldWithPath("data.stores[].indsSclsCd").type(JsonFieldType.STRING)
                                        .description("상권업종소분류코드(6자리)"),
                                fieldWithPath("data.stores[].indsSclsNm").type(JsonFieldType.STRING)
                                        .description("상권업종소분류명")
                        )
                ));

    }

}
