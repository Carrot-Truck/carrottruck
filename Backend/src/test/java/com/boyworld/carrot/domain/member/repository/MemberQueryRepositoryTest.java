package com.boyworld.carrot.domain.member.repository;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 회원 조회용 레포지토리 테스트
 *
 * @author 최영환
 */
@Slf4j
class MemberQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberQueryRepository memberQueryRepository;

    @DisplayName("이미 등록된 이메일이라면 true 를 반환한다.")
    @Test
    void isExistEmailWithTrue() {
        Member member = createMember();

        String email = "ssafy@ssafy.com";

        String role = member.getRole().toString();
        Boolean result = memberQueryRepository.isExistEmail(email, role);

        assertThat(result).isTrue();
    }

    @DisplayName("이미 등록되지 않은 이메일이라면 false 를 반환한다.")
    @Test
    void isExistEmailWithFalse() {
        Member member = createMember();

        String email = "ssafy@naver.com";

        String role = member.getRole().toString();
        Boolean result = memberQueryRepository.isExistEmail(email, role);

        assertThat(result).isFalse();
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