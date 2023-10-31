package com.boyworld.carrot.domain.member.repository;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 회원 CUD 레포지토리 테스트
 *
 * @author 최영환
 */
@Slf4j
class MemberRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원등록 테스트")
    void saveMember() {

        // given
        Member member = Member.builder()
                .email("ssafy@ssafy.com")
                .nickname("매미킴")
                .name("김동현")
                .phoneNumber("010-1234-5678")
                .role(Role.CLIENT)
                .active(true)
                .build();
        // when
        Member savedMember = memberRepository.save(member);

        // then
        assertThat(member).isSameAs(savedMember);
        assertThat(member.getEmail()).isEqualTo(savedMember.getEmail());
        assertThat(savedMember.getId()).isNotNull();
        assertThat(memberRepository.count()).isEqualTo(1);
    }
}