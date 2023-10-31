package com.boyworld.carrot.domain.member.repository;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.controller.member.response.MemberAddressDetailResponse;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.MemberAddress;
import com.boyworld.carrot.domain.member.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class MemberAddressQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private MemberAddressQueryRepository memberAddressQueryRepository;

    @Autowired
    private MemberAddressRepository memberAddressRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("사용자는 회원 주소 목록을 조회할 수 있다.")
    @Test
    void getMemberAddresses() {
        // given
        Member member = createMember(Role.CLIENT);
        MemberAddress memberAddress1 = createMemberAddress(member, "주소1");
        MemberAddress memberAddress2 = createMemberAddress(member, "주소2");

        // when
        List<MemberAddressDetailResponse> responses =
                memberAddressQueryRepository.getMemberAddressesByEmail(member.getEmail(), 0L);

        // then
        assertThat(responses).hasSize(2);
    }

    @DisplayName("회원 주소가 없으면 빈 리스트가 반환된다.")
    @Test
    void getMemberAddressesWithEmpty() {
        // given
        Member member = createMember(Role.CLIENT);

        // when
        List<MemberAddressDetailResponse> responses =
                memberAddressQueryRepository.getMemberAddressesByEmail(member.getEmail(), 0L);

        // then
        assertThat(responses).isEmpty();
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

    private MemberAddress createMemberAddress(Member member, String address) {
        MemberAddress memberAddress = MemberAddress.builder()
                .member(member)
                .address(address)
                .active(true)
                .build();
        return memberAddressRepository.save(memberAddress);
    }
}