package com.boyworld.carrot.api.service.member.query;

import com.boyworld.carrot.api.service.member.dto.LoginDto;
import com.boyworld.carrot.api.service.member.error.InvalidAccessException;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.repository.query.MemberQueryRepository;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
import com.boyworld.carrot.security.JwtTokenProvider;
import com.boyworld.carrot.security.TokenInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

/**
 * 인증 서비스
 *
 * @author 최영환
 */
@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 로그인
     *
     * @param dto  로그인 정보
     * @param role 로그인 할 사용자 권한
     * @return 로그인한 회원 정보
     */
    public TokenInfo login(LoginDto dto, String role) {
        log.debug("AuthService#login called");
        log.debug("LoginDto={}", dto);
        log.debug("role={}", role);

        checkValidAccess(dto, role);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtTokenProvider.generateToken(authenticate);
    }

    /**
     * 회원 가입시 이메일 중복 체크 API
     *
     * @param email 중복 체크할 이메일
     * @return 존재하면 true, 존재하지 않으면 false
     */
    public Boolean checkEmail(String email) {
        return memberQueryRepository.isExistEmail(email);
    }

    /**
     * 사업자 로그인 요청 시 일반 사용자의 요청인지 확인
     *
     * @param dto  로그인 할 회원 정보
     * @param role 로그인 할 권한
     * @throws NoSuchElementException 이메일에 해당하는 사용자가 없을 경우
     * @throws InvalidAccessException 사용자가 사업자 로그인을 시도한 경우
     */
    private void checkValidAccess(LoginDto dto, String role) {
        if (role.equals(Role.VENDOR.toString())) {
            Member member = memberRepository.findByEmail(dto.getEmail())
                    .orElseThrow(NoSuchElementException::new);
            if (member.getRole().equals(Role.CLIENT)) {
                throw new InvalidAccessException("잘못된 접근입니다.");
            }
        }
    }
}
