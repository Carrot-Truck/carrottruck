package com.boyworld.carrot.api.service.member;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.controller.member.response.JoinMemberResponse;
import com.boyworld.carrot.api.service.member.dto.JoinMemberDto;
import com.boyworld.carrot.api.service.member.error.DuplicateException;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 회원 서비스 테스트
 *
 * @author 최영환
 */
class MemberServiceTest extends IntegrationTestSupport {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @DisplayName("회원 가입 시 이미 사용 중인 이메일이라면 예외가 발생한다.")
    @Test
    void joinWithDuplicateEmail() {
        // given
        JoinMemberDto dto = JoinMemberDto.builder()
                .email("ssafy@ssafy.com")
                .nickname("매미킴")
                .password("password")
                .name("김동현")
                .phoneNumber("010-1234-5678")
                .role(Role.CLIENT)
                .build();
        Member member = createMember();

        // when // then
        assertThatThrownBy(() -> memberService.join(dto))
                .isInstanceOf(DuplicateException.class)
                .hasMessage("이미 사용중인 이메일 입니다.");
    }

    @DisplayName("모든 사용자는 회원 가입을 할 수 있다.")
    @Test
    void join() {
        // given
        JoinMemberDto dto = JoinMemberDto.builder()
                .email("ssafy@ssafy.com")
                .nickname("매미킴")
                .password("password")
                .name("김동현")
                .phoneNumber("010-1234-5678")
                .role(Role.CLIENT)
                .build();

        // when
        JoinMemberResponse response = memberService.join(dto);

        // then
        assertThat(response)
                .extracting("email", "nickname", "name", "phoneNumber", "role")
                .containsExactlyInAnyOrder("ssafy@ssafy.com", "매미킴", "김동현", "010-1234-5678", "CLIENT");
    }

    private Member createMember() {
        Member member = Member.builder()
                .email("ssafy@ssafy.com")
                .nickname("매미킴")
                .name("김동현")
                .phoneNumber("010-1234-5678")
                .role(Role.CLIENT)
                .active(true)
                .build();
        return memberRepository.save(member);
    }
}