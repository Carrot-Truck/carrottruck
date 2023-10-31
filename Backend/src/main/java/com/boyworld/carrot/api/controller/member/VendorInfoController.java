package com.boyworld.carrot.api.controller.member;

import com.boyworld.carrot.api.ApiResponse;
import com.boyworld.carrot.api.controller.member.request.CreateVendorInfoRequest;
import com.boyworld.carrot.api.controller.member.response.VendorInfoResponse;
import com.boyworld.carrot.api.service.member.query.VendorInfoQueryService;
import com.boyworld.carrot.api.service.member.command.VendorInfoService;
import com.boyworld.carrot.security.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 사업자 정보 관련 API 컨트롤러
 *
 * @author 최영환
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member/vendor-info")
public class VendorInfoController {

    private final VendorInfoService vendorInfoService;

    private final VendorInfoQueryService vendorInfoQueryService;

    /**
     * 사업자 정보 등록 API
     *
     * @param request 사업자 정보
     * @return 등록된 사업자 정보
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<VendorInfoResponse> createVendorInfo(@Valid @RequestBody CreateVendorInfoRequest request) {
        log.debug("VendorInfoController#createVendorInfo called");
        log.debug("CreateVendorInfoRequest={}", request);

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        VendorInfoResponse response = vendorInfoService.createVendorInfo(request.toCreateVendorInfoDto(), email);
        log.debug("VendorInfoResponse={}", response);

        return ApiResponse.created(response);
    }

    /**
     * 사업자 정보 조회 API
     * 
     * @return 현재 로그인한 사용자의 사업자 정보
     */
    @GetMapping
    public ApiResponse<VendorInfoResponse> getVendorInfo() {
        log.debug("VendorInfoController#getVendorInfo called");

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        VendorInfoResponse response = vendorInfoQueryService.getVendorInfo(email);
        log.debug("VendorInfoResponse={}", response);

        return ApiResponse.ok(response);
    }
}
