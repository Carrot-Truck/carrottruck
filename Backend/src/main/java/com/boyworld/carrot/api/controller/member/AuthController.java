package com.boyworld.carrot.api.controller.member;

import com.boyworld.carrot.api.ApiResponse;
import com.boyworld.carrot.api.controller.member.request.AuthCheckEmailRequest;
import com.boyworld.carrot.api.controller.member.request.AuthEmailRequest;
import com.boyworld.carrot.api.controller.member.request.CheckEmailRequest;
import com.boyworld.carrot.api.controller.member.request.LoginRequest;
import com.boyworld.carrot.api.service.member.query.AuthService;
import com.boyworld.carrot.client.mail.EmailMessage;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.security.TokenInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 인증 API 컨트롤러
 *
 * @author 최영환
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * 로그인 API
     *
     * @param request 로그인할 아이디, 비밀번호
     * @return 로그인한 회원정보
     */
    @PostMapping("/login/client")
    public ApiResponse<TokenInfo> loginClient(@Valid @RequestBody LoginRequest request) {
        log.debug("AuthController#loginClient called");
        log.debug("LoginRequest={}", request);

        TokenInfo tokenInfo = authService.login(request.toLoginDto(), Role.CLIENT.toString());
        log.debug("TokenInfo={}", tokenInfo);

        return ApiResponse.ok(tokenInfo);
    }

    /**
     * 사업자 로그인 API
     *
     * @param request 로그인할 아이디, 비밀번호
     * @return 로그인한 회원정보
     */
    @PostMapping("/login/vendor")
    public ApiResponse<TokenInfo> loginVendor(@Valid @RequestBody LoginRequest request) {
        log.debug("AuthController#loginVendor called");
        log.debug("LoginRequest={}", request);

        TokenInfo tokenInfo = authService.login(request.toLoginDto(), Role.VENDOR.toString());
        log.debug("TokenInfo={}", tokenInfo);

        return ApiResponse.ok(tokenInfo);
    }

    /**
     * 회원 가입시 이메일 중복 체크 API
     *
     * @param request 중복 체크할 이메일
     * @return 존재하면 true, 존재하지 않으면 false
     */
    @PostMapping("/duplication/email")
    public ApiResponse<Boolean> checkEmail(@Valid @RequestBody CheckEmailRequest request) {
        log.debug("AuthController#checkEmail called");
        log.debug("CheckEmailRequest={}", request);

        Boolean result = authService.checkEmail(request.getEmail());
        log.debug("result={}", result);

        return ApiResponse.ok(result);
    }

    /**
     * 이메일 인증 번호 발송 API
     *
     * @param request 인증번호를 받을 이메일 (사용자 이메일)
     * @return 200: 성공
     */
    @PostMapping("/email")
    public ApiResponse<?> authEmail(@Valid @RequestBody AuthEmailRequest request) {
        log.debug("AuthController#authEmail called");
        log.debug("AuthEmailRequest={}", request);

        EmailMessage emailMessage = EmailMessage.builder()
                .to(request.getEmail())
                .subject("[당근트럭] 이메일 인증을 위한 인증 코드 발송")
                .build();

        authService.authEmail(emailMessage);

        return ApiResponse.ok(null);
    }

    /**
     * 이메일 인증번호 확인 API
     *
     * @param request 인증할 이메일, 인증번호
     * @return 200: 성공
     */
    @PostMapping("/email/check")
    public ApiResponse<?> checkAuthEmail(@Valid @RequestBody AuthCheckEmailRequest request) {
        log.debug("AuthController#checkAuthEmail called");
        log.debug("AuthCheckEmailRequest={}", request);

        authService.authCheckEmail(request.getEmail(), request.getAuthNumber());

        return ApiResponse.ok(null);
    }
}
