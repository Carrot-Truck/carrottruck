package com.boyworld.carrot.api.service.member;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.service.member.dto.LoginDto;
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
        LoginDto dto = createLoginDto("CLIENT", "ssafy1234");
        Member member = createMember(Role.CLIENT);

        // when
        TokenInfo tokenInfo = authService.login(dto, Role.CLIENT.toString());
        log.debug("tokenInfo={}", tokenInfo);

        // then
        assertThat(tokenInfo).isNotNull();
    }

    @DisplayName("이메일과 비밀번호가 일치하지 않으면 예외가 발생한다.")
    @Test
    void loginWithWrongPassword() {
        // given
        LoginDto dto = createLoginDto("CLIENT", "ssafy123411");
        Member member = createMember(Role.CLIENT);

        // when // then
        String password = "ssafy123411";
        assertThatThrownBy(() -> authService.login(dto, Role.CLIENT.toString()))
                .isInstanceOf(BadCredentialsException.class);
    }

    @DisplayName("사용자와 사업자 간의 교차 로그인은 불가능하다.")
    @Test
    void loginWithWrongRole() {
        // given
        LoginDto dto = createLoginDto("CLIENT", "ssafy1234");
        Member member = createMember(Role.VENDOR);

        // when // then
        assertThatThrownBy(() -> authService.login(dto, "VENDOR"))
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

    private LoginDto createLoginDto(String role, String password) {
        return LoginDto.builder()
                .email("ssafy@ssafy.com")
                .password(password)
                .role(role)
                .build();
    }
}