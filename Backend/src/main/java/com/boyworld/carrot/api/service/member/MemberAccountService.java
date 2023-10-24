package com.boyworld.carrot.api.service.member;

import com.boyworld.carrot.security.TokenInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class MemberAccountService {

    /**
     * 로그인
     *
     * @param email    로그인 할 이메일
     * @param password 로그인 할 비밀번호
     * @return 로그인한 회원 정보
     */
    public TokenInfo login(String email, String password) {
        return null;
    }
}

