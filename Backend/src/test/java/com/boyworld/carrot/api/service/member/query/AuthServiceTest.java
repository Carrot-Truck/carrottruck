package com.boyworld.carrot.api.service.member.query;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.service.member.dto.LoginDto;
import com.boyworld.carrot.api.service.member.error.InValidAccessException;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
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
 * 인증 서비스 테스트
 *
 * @author 최영환
 */
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
        LoginDto dto = createLoginDto("ssafy1234");
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
        LoginDto dto = createLoginDto("ssafy123411");
        Member member = createMember(Role.CLIENT);

        // when // then
        assertThatThrownBy(() -> authService.login(dto, Role.CLIENT.toString()))
                .isInstanceOf(BadCredentialsException.class);
    }

    @DisplayName("사용자는 사업자로 로그인 할 수 없다.")
    @Test
    void loginWithWrongRole() {
        // given
        LoginDto dto = createLoginDto("ssafy1234");
        Member member = createMember(Role.CLIENT);

        // when // then
        assertThatThrownBy(() -> authService.login(dto, "VENDOR"))
                .isInstanceOf(InValidAccessException.class)
                .hasMessage("잘못된 접근입니다.");
    }

    @DisplayName("이메일 중복 체크 시 존재하는 이메일일 경우 True 가 반환된다.")
    @Test
    void checkEmailWithTrue() {
        // given
        String email = "ssafy@gmail.com";
        String role = "CLIENT";

        Member member = createMember(Role.CLIENT);

        // when
        Boolean result = authService.checkEmail(email);
        assertThat(result).isTrue();
    }

    @DisplayName("이메일 중복 체크 시 존재하는 이메일이 없는 경우 False 가 반환된다.")
    @Test
    void checkEmailWithFalse() {
        // given
        String email = "ssafy@naver.com";
        String role = "CLIENT";

        Member member = createMember(Role.CLIENT);

        // when
        Boolean result = authService.checkEmail(email);
        assertThat(result).isFalse();
    }

    private Member createMember(Role role) {
        Member member = Member.builder()
                .email("ssafy@gmail.com")
                .nickname("매미킴")
                .encryptedPwd(passwordEncoder.encode("ssafy1234"))
                .name("김동현")
                .phoneNumber("010-1234-5678")
                .role(role)
                .active(true)
                .build();
        return memberRepository.save(member);
    }

    private LoginDto createLoginDto(String password) {
        return LoginDto.builder()
                .email("ssafy@gmail.com")
                .password(password)
                .build();
    }
}