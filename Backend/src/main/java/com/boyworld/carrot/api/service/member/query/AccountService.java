package com.boyworld.carrot.api.service.member.query;

import com.boyworld.carrot.api.controller.member.response.ClientResponse;
import com.boyworld.carrot.api.controller.member.response.VendorResponse;
import com.boyworld.carrot.domain.member.repository.query.MemberQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

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

    private final MemberQueryRepository memberQueryRepository;

    /**
     * 로그인 중인 일반 사용자 회원 정보 조회
     *
     * @param email 로그인 중인 회원 이메일
     * @return 로그인 중인 회원 정보
     * @throws NoSuchElementException 해당 이메일 회원 정보가 존재하지 않는 경우
     */
    public ClientResponse getMemberInfo(String email) {
        ClientResponse response = memberQueryRepository.getClientInfoByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
        log.debug("ClientResponse={}", response);

        return response;
    }

    /**
     * 로그인 중인 사업자 회원 정보 조회
     *
     * @param email 로그인 중인 회원 이메일
     * @return 로그인 중인 회원 정보
     * @throws NoSuchElementException 존재하지 않는 사업자인 경우
     */
    public VendorResponse getVendorInfo(String email) {
        VendorResponse response = memberQueryRepository.getVendorInfoByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
        log.debug("VendorResponse={}", response);

        return response;
    }
}
