package com.boyworld.carrot.api.controller.member;

import com.boyworld.carrot.api.ApiResponse;
import com.boyworld.carrot.api.controller.member.request.JoinRequest;
import com.boyworld.carrot.api.controller.member.response.JoinMemberResponse;
import com.boyworld.carrot.api.service.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 사업자 관련 API 컨트롤러
 *
 * @author 최영환
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class VendorController {

    private final MemberService memberService;

    /**
     * 일반 사용자 가입 API
     *
     * @param request 가입할 회원 정보
     * @return 가입된 회원 정보
     */
    @PostMapping("/vendor/join")
    public ApiResponse<JoinMemberResponse> join(@Valid @RequestBody JoinRequest request) {
        log.debug("ClientController#join called !!!");
        log.debug("JoinRequest={}", request);

        JoinMemberResponse response = memberService.join(request.toJoinMemberDto());
        log.debug("JoinMemberResponse={}", response);

        return ApiResponse.created(response);
    }
}
