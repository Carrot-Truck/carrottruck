package com.boyworld.carrot.api.controller.member;

import com.boyworld.carrot.api.ApiResponse;
import com.boyworld.carrot.api.controller.member.request.CheckEmailRequest;
import com.boyworld.carrot.api.controller.member.request.LoginRequest;
import com.boyworld.carrot.api.service.member.AuthService;
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
    @PostMapping("/login")
    public ApiResponse<TokenInfo> login(@Valid @RequestBody LoginRequest request) {
        log.debug("AuthController#login called");
        log.debug("LoginRequest={}", request);

        TokenInfo tokenInfo = authService.login(request.getEmail(), request.getPassword(), request.getRole());
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

        Boolean result = authService.checkEmail(request.getEmail(), request.getRole());
        log.debug("result={}", result);

        return ApiResponse.ok(result);
    }

}
