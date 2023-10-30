package com.boyworld.carrot.docs.mermber;

import com.boyworld.carrot.api.controller.member.ClientController;
import com.boyworld.carrot.api.controller.member.request.EditMemberRequest;
import com.boyworld.carrot.api.controller.member.request.JoinRequest;
import com.boyworld.carrot.api.controller.member.request.MemberAddressRequest;
import com.boyworld.carrot.api.controller.member.request.WithdrawalRequest;
import com.boyworld.carrot.api.controller.member.response.ClientResponse;
import com.boyworld.carrot.api.controller.member.response.JoinMemberResponse;
import com.boyworld.carrot.api.controller.member.response.MemberAddressResponse;
import com.boyworld.carrot.api.service.member.AccountService;
import com.boyworld.carrot.api.service.member.MemberService;
import com.boyworld.carrot.api.service.member.dto.EditMemberDto;
import com.boyworld.carrot.api.service.member.dto.JoinMemberDto;
import com.boyworld.carrot.docs.RestDocsSupport;
import com.boyworld.carrot.domain.member.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientControllerDocsTest.class)
public class ClientControllerDocsTest extends RestDocsSupport {

    private final MemberService memberService = mock(MemberService.class);
    private final AccountService accountService = mock(AccountService.class);

    @Override
    protected Object initController() {
        return new ClientController(memberService, accountService);
    }

    @DisplayName("일반 사용자 회원가입 API")
    @Test
    void join() throws Exception {
        JoinRequest request = JoinRequest.builder()
                .email("ssafy@ssafy.com")
                .nickname("닉네임")
                .password("ssafy1234")
                .name("김싸피")
                .phoneNumber("010-1234-5678")
                .role("CLIENT")
                .build();

        JoinMemberResponse response = JoinMemberResponse.builder()
                .email("ssafy@ssafy.com")
                .name("김싸피")
                .nickname("닉네임")
                .phoneNumber("010-1234-5678")
                .role("CLIENT")
                .build();

        given(memberService.join(any(JoinMemberDto.class)))
                .willReturn(response);

        mockMvc.perform(
                        post("/member/client/join")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("create-client",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING)
                                        .description("이메일"),
                                fieldWithPath("nickname").type(JsonFieldType.STRING)
                                        .description("닉네임"),
                                fieldWithPath("password").type(JsonFieldType.STRING)
                                        .description("비밀번호"),
                                fieldWithPath("name").type(JsonFieldType.STRING)
                                        .description("이름"),
                                fieldWithPath("phoneNumber").type(JsonFieldType.STRING)
                                        .description("전화번호"),
                                fieldWithPath("role").type(JsonFieldType.STRING)
                                        .description("역할")
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
                                fieldWithPath("data.email").type(JsonFieldType.STRING)
                                        .description("이메일"),
                                fieldWithPath("data.nickname").type(JsonFieldType.STRING)
                                        .description("닉네임"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING)
                                        .description("이름"),
                                fieldWithPath("data.phoneNumber").type(JsonFieldType.STRING)
                                        .description("전화번호"),
                                fieldWithPath("data.role").type(JsonFieldType.STRING)
                                        .description("역할")
                        )
                ));
    }

    @DisplayName("일반 사용자 회원탈퇴 API")
    @Test
    void withdrawal() throws Exception {
        WithdrawalRequest request = WithdrawalRequest.builder()
                .email("ssafy@ssafy.com")
                .password("ssafy1234")
                .build();

        Boolean result = true;

        given(memberService.withdrawal(anyString(), anyString()))
                .willReturn(result);

        mockMvc.perform(
                        post("/member/client/withdrawal")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isFound())
                .andDo(document("remove-client",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING)
                                        .description("이메일"),
                                fieldWithPath("password").type(JsonFieldType.STRING)
                                        .description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.BOOLEAN)
                                        .description("탈퇴 여부")
                        )
                ));
    }

    @DisplayName("일반 사용자 정보조회 API")
    @Test
    @WithMockUser(roles = "CLIENT")
    void getInfo() throws Exception {
        ClientResponse response = ClientResponse.builder()
                .name("김동현")
                .nickname("매미킴")
                .email("ssafy@ssafy.com")
                .phoneNumber("010-1234-1234")
                .role(Role.CLIENT)
                .build();

        given(accountService.getClientInfo(anyString()))
                .willReturn(response);

        mockMvc.perform(
                        get("/member/client/info")
                                .header("Authentication", "authentication")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("search-client",
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING)
                                        .description("이름"),
                                fieldWithPath("data.nickname").type(JsonFieldType.STRING)
                                        .description("닉네임"),
                                fieldWithPath("data.email").type(JsonFieldType.STRING)
                                        .description("이메일"),
                                fieldWithPath("data.phoneNumber").type(JsonFieldType.STRING)
                                        .description("연락처"),
                                fieldWithPath("data.role").type(JsonFieldType.STRING)
                                        .description("역할")
                        )
                ));
    }

    @DisplayName("일반 사용자 정보 수정 API")
    @WithMockUser(roles = "CLIENT")
    @Test
    void editClient() throws Exception {
        EditMemberRequest request = EditMemberRequest.builder()
                .name("박동현")
                .nickname("매미킴123")
                .phoneNumber("010-1234-5678")
                .build();

        ClientResponse response = ClientResponse.builder()
                .name("박동현")
                .nickname("매미킴123")
                .email("ssafy@ssafy.com")
                .phoneNumber("010-1234-5678")
                .role(Role.CLIENT)
                .build();

        given(memberService.editClient(any(EditMemberDto.class)))
                .willReturn(response);

        mockMvc.perform(
                        put("/member/client")
                                .header("Authentication", "authentication")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("edit-client",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING)
                                        .description("이름"),
                                fieldWithPath("nickname").type(JsonFieldType.STRING)
                                        .description("닉네임"),
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
                                fieldWithPath("data.email").type(JsonFieldType.STRING)
                                        .description("이메일"),
                                fieldWithPath("data.nickname").type(JsonFieldType.STRING)
                                        .description("닉네임"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING)
                                        .description("이름"),
                                fieldWithPath("data.phoneNumber").type(JsonFieldType.STRING)
                                        .description("전화번호"),
                                fieldWithPath("data.role").type(JsonFieldType.STRING)
                                        .description("역할")
                        )
                ));
    }

    @DisplayName("회원 주소 등록 API")
    @Test
    @WithMockUser(roles = {"CLIENT", "VENDOR"})
    void createMemberAddress() throws Exception {
        MemberAddressRequest request = MemberAddressRequest.builder()
                .address("광주 광산구 장덕로 5번길 16")
                .build();

        MemberAddressResponse response = MemberAddressResponse.builder()
                .address("광주 광산구 장덕로 5번길 16")
                .build();

        given(memberService.createMemberAddress(anyString(), anyString()))
                .willReturn(response);

        mockMvc.perform(
                        post("/member/client/address")
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
                                fieldWithPath("data.address").type(JsonFieldType.STRING)
                                        .description("주소")
                        )
                ));
    }
}
