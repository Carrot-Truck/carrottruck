package com.boyworld.carrot.docs.mermber;

import com.boyworld.carrot.api.controller.member.VendorController;
import com.boyworld.carrot.api.controller.member.request.EditMemberRequest;
import com.boyworld.carrot.api.controller.member.request.JoinRequest;
import com.boyworld.carrot.api.controller.member.request.LoginRequest;
import com.boyworld.carrot.api.controller.member.request.WithdrawalRequest;
import com.boyworld.carrot.api.controller.member.response.JoinMemberResponse;
import com.boyworld.carrot.api.controller.member.response.VendorResponse;
import com.boyworld.carrot.api.service.member.MemberAccountService;
import com.boyworld.carrot.api.service.member.MemberService;
import com.boyworld.carrot.api.service.member.dto.EditMemberDto;
import com.boyworld.carrot.api.service.member.dto.JoinMemberDto;
import com.boyworld.carrot.docs.RestDocsSupport;
import com.boyworld.carrot.security.TokenInfo;
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

@WebMvcTest(VendorControllerDocsTest.class)
public class VendorControllerDocsTest extends RestDocsSupport {

    private final MemberService memberService = mock(MemberService.class);
    private final MemberAccountService memberAccountService = mock(MemberAccountService.class);

    @Override
    protected Object initController() {
        return new VendorController(memberService, memberAccountService);
    }

    @DisplayName("사업자 회원가입 API")
    @Test
    void join() throws Exception {
        JoinRequest request = JoinRequest.builder()
                .email("ssafy@ssafy.com")
                .nickname("닉네임")
                .password("ssafy1234")
                .name("김싸피")
                .phoneNumber("010-1234-5678")
                .role("VENDOR")
                .build();

        JoinMemberResponse response = JoinMemberResponse.builder()
                .email("ssafy@ssafy.com")
                .name("김싸피")
                .nickname("닉네임")
                .phoneNumber("010-1234-5678")
                .role("VENDOR")
                .build();

        given(memberService.join(any(JoinMemberDto.class)))
                .willReturn(response);

        mockMvc.perform(
                        post("/member/vendor/join")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("create-vendor",
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

    @DisplayName("사업자 로그인 API")
    @Test
    void login() throws Exception {
        LoginRequest request = LoginRequest.builder()
                .email("ssafy@ssafy.com")
                .password("ssafy1234")
                .build();

        TokenInfo tokenInfo = TokenInfo.builder()
                .grantType("Bearer")
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();

        given(memberAccountService.login(anyString(), anyString()))
                .willReturn(tokenInfo);

        mockMvc.perform(
                        post("/member/vendor/login")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("login-vendor",
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
                                fieldWithPath("data").type(JsonFieldType.OBJECT)
                                        .description("응답데이터"),
                                fieldWithPath("data.grantType").type(JsonFieldType.STRING)
                                        .description("grantType"),
                                fieldWithPath("data.accessToken").type(JsonFieldType.STRING)
                                        .description("accessToken"),
                                fieldWithPath("data.refreshToken").type(JsonFieldType.STRING)
                                        .description("refreshToken")
                        )
                ));
    }

    @DisplayName("사업자 회원탈퇴 API")
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
                        post("/member/vendor/withdrawal")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isFound())
                .andDo(document("remove-vendor",
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

    @DisplayName("사업자 정보조회 API")
    @Test
    @WithMockUser(roles = "VENDOR")
    void getInfo() throws Exception {
        VendorResponse response = VendorResponse.builder()
                .name("김동현")
                .nickname("매미킴")
                .email("ssafy@ssafy.com")
                .phoneNumber("010-1234-1234")
                .businessNumber("123456789")
                .role("VENDOR")
                .build();

        given(memberAccountService.getVendorInfo(anyString()))
                .willReturn(response);

        mockMvc.perform(
                        get("/member/vendor/info")
                                .header("Authentication", "authentication")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("search-vendor",
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
                                fieldWithPath("data.businessNumber").type(JsonFieldType.STRING)
                                        .description("사업자 번호"),
                                fieldWithPath("data.role").type(JsonFieldType.STRING)
                                        .description("역할")
                        )
                ));
    }

    @DisplayName("사업자 정보 수정 API")
    @WithMockUser(roles = "VENDOR")
    @Test
    void editVendor() throws Exception {
        EditMemberRequest request = EditMemberRequest.builder()
                .name("박동현")
                .nickname("매미킴123")
                .phoneNumber("010-1234-5678")
                .role("VENDOR")
                .build();

        VendorResponse response = VendorResponse.builder()
                .name("박동현")
                .nickname("매미킴123")
                .email("ssafy@ssafy.com")
                .phoneNumber("010-1234-5678")
                .businessNumber("123456789")
                .role("VENDOR")
                .build();

        given(memberAccountService.editVendor(any(EditMemberDto.class)))
                .willReturn(response);

        mockMvc.perform(
                        put("/member/vendor")
                                .header("Authentication", "authentication")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("edit-vendor",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING)
                                        .description("이름"),
                                fieldWithPath("nickname").type(JsonFieldType.STRING)
                                        .description("닉네임"),
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
                                fieldWithPath("data.businessNumber").type(JsonFieldType.STRING)
                                        .description("사업자번호"),
                                fieldWithPath("data.role").type(JsonFieldType.STRING)
                                        .description("역할")
                        )
                ));
    }
}
