package com.boyworld.carrot.docs.mermber;

import com.boyworld.carrot.api.controller.member.VendorInfoController;
import com.boyworld.carrot.api.controller.member.request.CreateVendorInfoRequest;
import com.boyworld.carrot.api.controller.member.request.MemberAddressRequest;
import com.boyworld.carrot.api.controller.member.response.MemberAddressDetailResponse;
import com.boyworld.carrot.api.controller.member.response.MemberAddressResponse;
import com.boyworld.carrot.api.controller.member.response.VendorInfoResponse;
import com.boyworld.carrot.api.service.member.command.VendorInfoService;
import com.boyworld.carrot.api.service.member.dto.CreateVendorInfoDto;
import com.boyworld.carrot.api.service.member.query.VendorInfoQueryService;
import com.boyworld.carrot.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VendorInfoControllerTest.class)
class VendorInfoControllerTest extends RestDocsSupport {

    private final VendorInfoService vendorInfoService = Mockito.mock(VendorInfoService.class);
    private final VendorInfoQueryService vendorInfoQueryService = Mockito.mock(VendorInfoQueryService.class);

    @Override
    protected Object initController() {
        return new VendorInfoController(vendorInfoService, vendorInfoQueryService);
    }

    @DisplayName("회원 주소 등록 API")
    @Test
    @WithMockUser(roles = {"VENDOR"})
    void createMemberAddress() throws Exception {
        CreateVendorInfoRequest request = CreateVendorInfoRequest.builder()
                .tradeName("무적의 소년천지")
                .businessNumber("1515-302-006031")
                .vendorName("김동현")
                .phoneNumber("010-1234-5678")
                .build();

        VendorInfoResponse response = VendorInfoResponse.builder()
                .tradeName("무적의 소년천지")
                .businessNumber("1515-302-006031")
                .vendorName("김동현")
                .phoneNumber("010-1234-5678")
                .build();

        given(vendorInfoService.createVendorInfo(any(CreateVendorInfoDto.class), anyString()))
                .willReturn(response);

        mockMvc.perform(
                        post("/member/vendor-info")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("create-vendor-info",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("tradeName").type(JsonFieldType.STRING)
                                        .description("상호명"),
                                fieldWithPath("businessNumber").type(JsonFieldType.STRING)
                                        .description("사업자등록번호"),
                                fieldWithPath("vendorName").type(JsonFieldType.STRING)
                                        .description("대표자명"),
                                fieldWithPath("phoneNumber").type(JsonFieldType.STRING)
                                        .description("전화번호")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT)
                                        .description("응답데이터"),
                                fieldWithPath("data.tradeName").type(JsonFieldType.STRING)
                                        .description("상호명"),
                                fieldWithPath("data.businessNumber").type(JsonFieldType.STRING)
                                        .description("사업자등록번호"),
                                fieldWithPath("data.vendorName").type(JsonFieldType.STRING)
                                        .description("대표자명"),
                                fieldWithPath("data.phoneNumber").type(JsonFieldType.STRING)
                                        .description("전화번호")
                        )
                ));
    }

    @DisplayName("회원 주소 목록 조회 API")
    @Test
    @WithMockUser(roles = {"CLIENT", "VENDOR"})
    void getMemberAddresses() throws Exception {

        VendorInfoResponse response = VendorInfoResponse.builder()
                .tradeName("무적의 소년천지")
                .businessNumber("1515-302-006031")
                .vendorName("김동현")
                .phoneNumber("010-1234-5678")
                .build();

        given(vendorInfoQueryService.getVendorInfo(anyString()))
                .willReturn(response);

        mockMvc.perform(
                        get("/member/vendor-info")
                                .header("Authentication", "authentication")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("search-vendor-info",
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT)
                                        .description("응답데이터"),
                                fieldWithPath("data.tradeName").type(JsonFieldType.STRING)
                                        .description("상호명"),
                                fieldWithPath("data.businessNumber").type(JsonFieldType.STRING)
                                        .description("사업자등록번호"),
                                fieldWithPath("data.vendorName").type(JsonFieldType.STRING)
                                        .description("대표자명"),
                                fieldWithPath("data.phoneNumber").type(JsonFieldType.STRING)
                                        .description("전화번호")
                        )
                ));
    }
}