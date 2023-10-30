package com.boyworld.carrot.api.service.member;

import com.boyworld.carrot.api.controller.member.response.ClientResponse;
import com.boyworld.carrot.api.controller.member.response.VendorResponse;
import com.boyworld.carrot.api.service.member.dto.EditMemberDto;
import com.boyworld.carrot.security.JwtTokenProvider;
import com.boyworld.carrot.security.TokenInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 계정 관련 서비스
 *
 * @author 최영환
 */
@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class AccountService {

    /**
     * 로그인 중인 일반 사용자 회원 정보 조회
     *
     * @param email 로그인 중인 회원 이메일
     * @return 로그인 중인 회원 정보
     */
    public ClientResponse getClientInfo(String email) {
        return null;
    }

    /**
     * 로그인 중인 사업자 회원 정보 조회
     *
     * @param email 로그인 중인 회원 이메일
     * @return 로그인 중인 회원 정보
     */
    public VendorResponse getVendorInfo(String email) {
        return null;
    }

    /**
     * 일반 사용자 정보 수정
     *
     * @param dto 수정할 회원 정보
     * @return 수정된 회원 정보
     */
    public ClientResponse editClient(EditMemberDto dto) {
        return null;
    }

    /**
     * 사업자 정보 수정
     *
     * @param dto 수정할 회원 정보
     * @return 수정된 회원 정보
     */
    public VendorResponse editVendor(EditMemberDto dto) {
        return null;
    }
}
