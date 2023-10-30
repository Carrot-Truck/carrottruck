package com.boyworld.carrot.api.service.member;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.service.member.error.InvalidAccessException;
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

@Slf4j
class AuthServiceTest extends IntegrationTestSupport {
    @Autowired
    private AuthService authService;

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
        Member member = createMember(Role.CLIENT);

        // when
        String password = "ssafy1234";
        TokenInfo tokenInfo = authService.login(member.getEmail(), password, member.getRole().toString());
        log.debug("tokenInfo={}", tokenInfo);

        // then
        assertThat(tokenInfo).isNotNull();
    }

    @DisplayName("이메일과 비밀번호가 일치하지 않으면 예외가 발생한다.")
    @Test
    void loginWithWrongPassword() {
        // given
        Member member = createMember(Role.CLIENT);

        // when // then
        String password = "ssafy123411";
        assertThatThrownBy(() -> authService.login(member.getEmail(), password, member.getRole().toString()))
                .isInstanceOf(BadCredentialsException.class);
    }

    @DisplayName("사용자와 사업자 간의 교차 로그인은 불가능하다.")
    @Test
    void loginWithWrongRole() {
        // given
        Member member = createMember(Role.VENDOR);

        // when // then
        String password = "ssafy1234";
        String role = "CLIENT";
        assertThatThrownBy(() -> authService.login(member.getEmail(), password, role))
                .isInstanceOf(InvalidAccessException.class)
                .hasMessage("잘못된 접근입니다.");
    }

    private Member createMember(Role role) {
        Member member = Member.builder()
                .email("ssafy@ssafy.com")
                .nickname("매미킴")
                .encryptedPwd(passwordEncoder.encode("ssafy1234"))
                .name("김동현")
                .phoneNumber("010-1234-5678")
                .role(role)
                .active(true)
                .build();
        return memberRepository.save(member);
    }
}