package com.boyworld.carrot.docs.mermber;

import com.boyworld.carrot.api.controller.member.AuthController;
import com.boyworld.carrot.api.controller.member.request.CheckEmailRequest;
import com.boyworld.carrot.api.controller.member.request.LoginRequest;
import com.boyworld.carrot.api.service.member.AuthService;
import com.boyworld.carrot.api.service.member.dto.LoginDto;
import com.boyworld.carrot.docs.RestDocsSupport;
import com.boyworld.carrot.security.TokenInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthControllerDocsTest.class)
public class AuthControllerDocsTest extends RestDocsSupport {

    private final AuthService authService = mock(AuthService.class);

    @Override
    protected Object initController() {
        return new AuthController(authService);
    }

    @DisplayName("사용자 로그인 API")
    @Test
    void login() throws Exception {
        LoginRequest request = LoginRequest.builder()
                .email("ssafy@ssafy.com")
                .password("ssafy1234")
                .role("CLIENT")
                .build();

        TokenInfo tokenInfo = TokenInfo.builder()
                .grantType("Bearer")
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();

        given(authService.login(any(LoginDto.class), anyString()))
                .willReturn(tokenInfo);

        mockMvc.perform(
                        post("/auth/login/client")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("login-client",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING)
                                        .description("이메일"),
                                fieldWithPath("password").type(JsonFieldType.STRING)
                                        .description("비밀번호"),
                                fieldWithPath("role").type(JsonFieldType.STRING)
                                        .description("권한")
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

    @DisplayName("사업자 로그인 API")
    @Test
    void loginVendor() throws Exception {
        LoginRequest request = LoginRequest.builder()
                .email("ssafy@ssafy.com")
                .password("ssafy1234")
                .role("VENDOR")
                .build();

        TokenInfo tokenInfo = TokenInfo.builder()
                .grantType("Bearer")
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();

        given(authService.login(any(LoginDto.class), anyString()))
                .willReturn(tokenInfo);

        mockMvc.perform(
                        post("/auth/login/vendor")
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
                                        .description("비밀번호"),
                                fieldWithPath("role").type(JsonFieldType.STRING)
                                        .description("권한")
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

    @DisplayName("이메일 중복체크 API")
    @Test
    void checkEmail() throws Exception {
        CheckEmailRequest request = CheckEmailRequest.builder()
                .email("ssafy@ssafy.com")
                .role("CLIENT")
                .build();

        Boolean result = false;

        given(authService.checkEmail(anyString(), anyString()))
                .willReturn(result);

        mockMvc.perform(
                        post("/auth/duplication/email")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("check-email",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING)
                                        .description("이메일"),
                                fieldWithPath("role").type(JsonFieldType.STRING)
                                        .description("권한")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.BOOLEAN)
                                        .description("이메일 중복 여부")
                        )
                ));
    }
}
