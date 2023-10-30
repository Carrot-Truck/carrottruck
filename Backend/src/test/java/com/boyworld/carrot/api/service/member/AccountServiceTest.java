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
class AccountServiceTest extends IntegrationTestSupport {

    @Autowired
    private AccountService accountService;


}