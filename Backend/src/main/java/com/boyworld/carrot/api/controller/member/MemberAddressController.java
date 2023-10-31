package com.boyworld.carrot.api.controller.member;

import com.boyworld.carrot.api.ApiResponse;
import com.boyworld.carrot.api.controller.member.request.MemberAddressRequest;
import com.boyworld.carrot.api.controller.member.response.MemberAddressDetailResponse;
import com.boyworld.carrot.api.controller.member.response.MemberAddressResponse;
import com.boyworld.carrot.api.service.member.command.MemberAddressService;
import com.boyworld.carrot.api.service.member.query.MemberAddressQueryService;
import com.boyworld.carrot.security.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 회원 주소 관련 API 컨트롤러
 *
 * @author 최영환
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member/address")
public class MemberAddressController {

    private final MemberAddressService memberAddressService;
    private final MemberAddressQueryService memberAddressQueryService;

    /**
     * 회원 주소 등록 API
     *
     * @param request 등록할 주소
     * @return 등록된 회원 주소 정보
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MemberAddressDetailResponse> createMemberAddress(@Valid @RequestBody MemberAddressRequest request) {
        log.debug("MemberAddressController#createMemberAddress called");

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        MemberAddressDetailResponse response = memberAddressService.createMemberAddress(request.getAddress(), email);
        log.debug("MemberDetailAddressResponse={}", response);

        return ApiResponse.ok(response);
    }

    /**
     * 회원 주소 목록 조회 API
     *
     * @param lastMemberAddressId 마지막으로 조회된 주소 식별키
     * @return 회원 주소 목록
     */
    @GetMapping
    public ApiResponse<MemberAddressResponse> getMemberAddresses(@RequestParam(required = false, defaultValue = "") String lastMemberAddressId) {
        log.debug("MemberAddressController#getMemberAddresses called");

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        MemberAddressResponse response = memberAddressQueryService.getMemberAddresses(email, lastMemberAddressId);
        log.debug("MemberAddressResponse={}", response);

        return ApiResponse.ok(response);
    }

    /**
     * 회원 주소 상세 조회 API
     *
     * @param memberAddressId 회원 주소 식별키
     * @return 회원 주소 상세 정보
     */
    @GetMapping("/{memberAddressId}")
    public ApiResponse<MemberAddressDetailResponse> getMemberAddress(@PathVariable Long memberAddressId) {
        log.debug("MemberAddressController#getMemberAddress called");
        log.debug("memberAddressId={}", memberAddressId);

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        MemberAddressDetailResponse response = memberAddressQueryService.getMemberAddress(memberAddressId, email);
        log.debug("MemberAddressDetailResponse={}", response);

        return ApiResponse.ok(response);
    }

    /**
     * 회원 주소 수정 API
     *
     * @param memberAddressId 수정할 주소 식별키
     * @param request         수정할 주소 정보
     * @return 수정된 주소 정보
     */
    @PatchMapping("/{memberAddressId}")
    public ApiResponse<MemberAddressDetailResponse> editMemberAddress(@PathVariable Long memberAddressId,
                                                                      @Valid @RequestBody MemberAddressRequest request) {
        log.debug("MemberAddressController#editMemberAddress called");

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        MemberAddressDetailResponse response = memberAddressService.editMemberAddress(memberAddressId, request.getAddress(), email);
        log.debug("MemberAddressResponse={}", response);

        return ApiResponse.ok(response);
    }

    /**
     * 회원 주소 삭제 API
     *
     * @param memberAddressId 삭제할 회원 주소
     * @return true: 삭제 완료 / false: 삭제 실패
     */
    @DeleteMapping("/{memberAddressId}")
    @ResponseStatus(HttpStatus.FOUND)
    public ApiResponse<Boolean> deleteMemberAddress(@PathVariable Long memberAddressId) {
        log.debug("MemberAddressController#deleteMemberAddress called");

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        Boolean result = memberAddressService.deleteMemberAddress(memberAddressId, email);
        log.debug("result={}", result);

        return ApiResponse.found(result);
    }
}
