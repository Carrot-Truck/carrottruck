package com.boyworld.carrot.api.service.member;


import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.controller.member.response.ClientResponse;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.repository.MemberQueryRepository;
import com.boyworld.carrot.domain.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.NoSuchElementException;

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

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private MemberQueryRepository memberQueryRepository;

    @DisplayName("일반 사용자 정보를 이메일로 조회한다.")
    @Test
    void getExistClientInfo() {
        // given
        Member member = createMember(Role.CLIENT);
        String email = "ssafy@ssafy.com";

        // when
        ClientResponse response = accountService.getClientInfo(email);

        // then
        assertThat(response).isNotNull();
        assertThat(response).extracting("name", "nickname", "email", "phoneNumber", "role")
                .containsExactlyInAnyOrder("김동현", "매미킴", "ssafy@ssafy.com", "010-1234-5678", "CLIENT");
    }

    @DisplayName("이메일에 해당하는 일반 사용자가 존재하지 않을 경우 예외가 발생한다.")
    @Test
    void getNotExistClientInfo() {
        // given
        Member member = createMember(Role.VENDOR);
        String email = "ssafy@ssafy.com";

        // when // then
        assertThatThrownBy(() -> accountService.getClientInfo(email))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("존재하지 않는 회원입니다.");
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