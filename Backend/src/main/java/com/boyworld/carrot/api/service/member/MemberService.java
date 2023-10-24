package com.boyworld.carrot.api.service.member;

import com.boyworld.carrot.api.controller.member.response.JoinMemberResponse;
import com.boyworld.carrot.api.service.member.dto.JoinMemberDto;
import com.boyworld.carrot.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 회원 서비스
 *
 * @author 최영환
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     *
     * @param dto 가입할 회원 정보
     * @return 가입한 회원 정보
     */
    public JoinMemberResponse join(JoinMemberDto dto) {
        return null;
    }

    /**
     * 회원탈퇴
     *
     * @param email    탈퇴할 회원 이메일
     * @param password 탈퇴할 회원 비밀번호
     * @return 탈퇴여부
     */
    public Boolean withdrawal(String email, String password) {
        return true;
    }
}
