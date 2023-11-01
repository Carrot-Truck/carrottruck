package com.boyworld.carrot.docs.mermber;

import com.boyworld.carrot.api.controller.member.MemberAddressController;
import com.boyworld.carrot.api.controller.member.request.MemberAddressRequest;
import com.boyworld.carrot.api.controller.member.response.MemberAddressDetailResponse;
import com.boyworld.carrot.api.controller.member.response.MemberAddressResponse;
import com.boyworld.carrot.api.service.member.command.MemberAddressService;
import com.boyworld.carrot.api.service.member.query.MemberAddressQueryService;
import com.boyworld.carrot.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberAddressControllerDocsTest.class)
public class MemberAddressControllerDocsTest extends RestDocsSupport {

    private final MemberAddressService memberAddressService = mock(MemberAddressService.class);
    private final MemberAddressQueryService memberAddressQueryService = mock(MemberAddressQueryService.class);

    @Override
    protected Object initController() {
        return new MemberAddressController(memberAddressService, memberAddressQueryService);
    }

    @DisplayName("회원 주소 등록 API")
    @Test
    @WithMockUser(roles = {"CLIENT", "VENDOR"})
    void createMemberAddress() throws Exception {
        MemberAddressRequest request = MemberAddressRequest.builder()
                .address("광주 광산구 장덕로 5번길 16")
                .build();

        MemberAddressDetailResponse response = MemberAddressDetailResponse.builder()
                .memberAddressId(1L)
                .address("광주 광산구 장덕로 5번길 16")
                .selected(true)
                .build();

        given(memberAddressService.createMemberAddress(anyString(), anyString()))
                .willReturn(response);

        mockMvc.perform(
                        post("/member/address")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("create-address",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("address").type(JsonFieldType.STRING)
                                        .description("주소")
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
                                fieldWithPath("data.memberAddressId").type(JsonFieldType.NUMBER)
                                        .description("주소 식별키"),
                                fieldWithPath("data.address").type(JsonFieldType.STRING)
                                        .description("주소"),
                                fieldWithPath("data.selected").type(JsonFieldType.BOOLEAN)
                                        .description("현재 선택된 주소 여부")
                        )
                ));
    }

    @DisplayName("회원 주소 목록 조회 API")
    @Test
    @WithMockUser(roles = {"CLIENT", "VENDOR"})
    void getMemberAddresses() throws Exception {
        MemberAddressDetailResponse memberAddress1 = MemberAddressDetailResponse.builder()
                .memberAddressId(1L)
                .address("광주 광산구 장덕로 5번길 16")
                .selected(true)
                .build();

        MemberAddressDetailResponse memberAddress2 = MemberAddressDetailResponse.builder()
                .memberAddressId(2L)
                .address("광주 광산구 풍영로 223번안길")
                .selected(false)
                .build();

        MemberAddressResponse response = MemberAddressResponse.builder()
                .hasNext(false)
                .memberAddresses(List.of(memberAddress1, memberAddress2))
                .build();

        given(memberAddressQueryService.getMemberAddresses(anyString(), anyString()))
                .willReturn(response);

        mockMvc.perform(
                        get("/member/address")
                                .header("Authentication", "authentication")
                                .param("lastMemberAddressId", "")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("search-address",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("lastMemberAddressId")
                                        .description("마지막으로 조회된 주소 식별키")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data.hasNext").type(JsonFieldType.BOOLEAN)
                                        .description("다음 페이지 존재 여부"),
                                fieldWithPath("data.memberAddresses").type(JsonFieldType.ARRAY)
                                        .description("회원 주소 목록"),
                                fieldWithPath("data.memberAddresses[].memberAddressId").type(JsonFieldType.NUMBER)
                                        .description("회원 주소 식별키"),
                                fieldWithPath("data.memberAddresses[].address").type(JsonFieldType.STRING)
                                        .description("주소"),
                                fieldWithPath("data.memberAddresses[].selected").type(JsonFieldType.BOOLEAN)
                                        .description("현재 선택된 주소 여부")
                        )
                ));
    }

    @DisplayName("회원 주소 상세 조회 API")
    @Test
    @WithMockUser(roles = {"CLIENT", "VENDOR"})
    void getMemberAddress() throws Exception {
        MemberAddressDetailResponse response = MemberAddressDetailResponse.builder()
                .memberAddressId(1L)
                .address("광주 광산구 장덕로 5번길 16")
                .selected(true)
                .build();

        given(memberAddressQueryService.getMemberAddress(anyLong()))
                .willReturn(response);

        mockMvc.perform(
                        get("/member/address/{memberAddressId}", 1)
                                .header("Authentication", "authentication")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("search-detail-address",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("memberAddressId")
                                        .description("주소 식별키")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data.memberAddressId").type(JsonFieldType.NUMBER)
                                        .description("회원 주소 식별키"),
                                fieldWithPath("data.address").type(JsonFieldType.STRING)
                                        .description("주소"),
                                fieldWithPath("data.selected").type(JsonFieldType.BOOLEAN)
                                        .description("현재 선택된 주소 여부")
                        )
                ));
    }

    @DisplayName("회원 주소 수정 API")
    @Test
    @WithMockUser(roles = {"CLIENT", "VENDOR"})
    void editMemberAddress() throws Exception {
        MemberAddressRequest request = MemberAddressRequest.builder()
                .address("광주 광산구 풍영로 223번안길")
                .build();

        MemberAddressDetailResponse response = MemberAddressDetailResponse.builder()
                .memberAddressId(1L)
                .address("광주 광산구 풍영로 223번안길")
                .build();

        given(memberAddressService.editMemberAddress(anyLong(), anyString(), anyString()))
                .willReturn(response);

        mockMvc.perform(
                        patch("/member/address/{memberAddressId}", 1)
                                .header("Authentication", "authentication")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("edit-address",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("memberAddressId").description("수정할 주소 식별키")
                        ),
                        requestFields(
                                fieldWithPath("address").type(JsonFieldType.STRING)
                                        .description("주소")
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
                                fieldWithPath("data.memberAddressId").type(JsonFieldType.NUMBER)
                                        .description("주소 식별키"),
                                fieldWithPath("data.address").type(JsonFieldType.STRING)
                                        .description("주소"),
                                fieldWithPath("data.selected").type(JsonFieldType.BOOLEAN)
                                        .description("현재 선택된 주소 여부")
                        )
                ));
    }

    @DisplayName("회원 주소 삭제 API")
    @Test
    @WithMockUser(roles = {"CLIENT", "VENDOR"})
    void deleteMemberAddress() throws Exception {

        given(memberAddressService.deleteMemberAddress(anyLong()))
                .willReturn(true);

        mockMvc.perform(
                        delete("/member/address/{memberAddressId}", 1)
                                .header("Authentication", "authentication")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isFound())
                .andDo(document("delete-address",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("memberAddressId").description("삭제할 주소 식별키")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.BOOLEAN)
                                        .description("삭제여부")
                        )
                ));
    }
}
