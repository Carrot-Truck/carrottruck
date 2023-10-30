package com.boyworld.carrot.api.service.member;


import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.repository.MemberRepository;
import com.boyworld.carrot.security.JwtTokenProvider;
import com.boyworld.carrot.security.TokenInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 계정 서비스 테스트
 *
 * @author 최영환
 */
@Slf4j
class MemberAccountServiceTest extends IntegrationTestSupport {

    @Autowired
    private MemberAccountService memberAccountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @DisplayName("모든 사용자는 로그인을 할 수 있다.")
    @Test
    void loginSuccess() {
        // given
        Member member = createMember();

        // when
        TokenInfo tokenInfo = memberAccountService.login(member.getEmail(), "ssafy1234");
        log.debug("tokenInfo={}", tokenInfo);

        // then
        assertThat(tokenInfo).isNotNull();
    }

    @DisplayName("이메일이나 비밀번호가 일치하지 않으면 예외가 발생한다.")
    @Test
    void loginFail() {
        // given
        Member member = createMember();

        // when // then
        assertThatThrownBy(() -> memberAccountService.login(member.getEmail(), "ssafy123411"))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("자격 증명에 실패하였습니다.");
    }

    private Member createMember() {
        Member member = Member.builder()
                .email("ssafy@ssafy.com")
                .nickname("매미킴")
                .encryptedPwd(passwordEncoder.encode("ssafy1234"))
                .name("김동현")
                .phoneNumber("010-1234-5678")
                .role(Role.CLIENT)
                .active(true)
                .build();
        return memberRepository.save(member);
    }
}