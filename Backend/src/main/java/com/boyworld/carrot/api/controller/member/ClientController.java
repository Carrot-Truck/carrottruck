package com.boyworld.carrot.api.controller.member;

import com.boyworld.carrot.api.ApiResponse;
import com.boyworld.carrot.api.controller.member.request.EditMemberRequest;
import com.boyworld.carrot.api.controller.member.request.JoinRequest;
import com.boyworld.carrot.api.controller.member.request.LoginRequest;
import com.boyworld.carrot.api.controller.member.request.WithdrawalRequest;
import com.boyworld.carrot.api.controller.member.response.ClientResponse;
import com.boyworld.carrot.api.controller.member.response.JoinMemberResponse;
import com.boyworld.carrot.api.service.member.MemberAccountService;
import com.boyworld.carrot.api.service.member.MemberService;
import com.boyworld.carrot.security.SecurityUtil;
import com.boyworld.carrot.security.TokenInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 일반 사용자 관련 API 컨트롤러
 *
 * @author 최영환
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member/client")
public class ClientController {

    private final MemberService memberService;
    private final MemberAccountService memberAccountService;

    /**
     * 일반 사용자 가입 API
     *
     * @param request 가입할 회원 정보
     * @return 가입된 회원 정보
     */
    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<JoinMemberResponse> join(@Valid @RequestBody JoinRequest request) {
        log.debug("ClientController#join called !!!");
        log.debug("JoinRequest={}", request);

        JoinMemberResponse response = memberService.join(request.toJoinMemberDto());
        log.debug("JoinMemberResponse={}", response);

        return ApiResponse.created(response);
    }

    /**
     * 로그인 API
     *
     * @param request 로그인할 아이디, 비밀번호
     * @return 로그인한 회원정보
     */
    @PostMapping("/login")
    public ApiResponse<TokenInfo> login(@Valid @RequestBody LoginRequest request) {
        log.debug("ClientController#login called !!!");
        log.debug("LoginRequest={}", request);

        TokenInfo tokenInfo = memberAccountService.login(request.getEmail(), request.getPassword());
        log.debug("TokenInfo={}", tokenInfo);

        return ApiResponse.ok(tokenInfo);
    }

    /**
     * 회원탈퇴 API
     *
     * @param request 탈퇴할 아이디, 비밀번호
     * @return 탈퇴 성공 여부
     */
    @PostMapping("/withdrawal")
    @ResponseStatus(HttpStatus.FOUND)
    public ApiResponse<Boolean> withdrawal(@Valid @RequestBody WithdrawalRequest request) {
        log.debug("ClientController#withdrawal called !!!");
        log.debug("WithdrawalRequest={}", request);

        Boolean result = memberService.withdrawal(request.getEmail(), request.getPassword());
        log.debug("result={}", result);

        return ApiResponse.found(true);
    }

    /**
     * 로그인 중인 회원 정보 조회
     *
     * @return 로그인 중인 회원 정보
     */
    @GetMapping("/info")
    public ApiResponse<ClientResponse> getInfo() {
        log.debug("ClientController#getInfo called");

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        ClientResponse response = memberAccountService.getClientInfo(email);
        log.debug("ClientResponse={}", response);

        return ApiResponse.ok(response);
    }

    /**
     * 회원 정보 수정
     *
     * @param request 수정할 회원 정보
     * @return 수정된 회원 정보
     */
    @PutMapping
    public ApiResponse<ClientResponse> edit(@Valid @RequestBody EditMemberRequest request) {
        log.debug("ClientController#edit called");

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        ClientResponse response = memberAccountService.editClient(request.toEditMemberDto(email));
        log.debug("ClientResponse={}", response);

        return ApiResponse.ok(response);
    }
}
