package com.boyworld.carrot.api.service.member;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.controller.member.response.MemberAddressResponse;
import com.boyworld.carrot.api.service.member.query.MemberAddressQueryService;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.MemberAddress;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.repository.command.MemberAddressRepository;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class MemberAddressQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private MemberAddressQueryService memberAddressQueryService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberAddressRepository memberAddressRepository;

    @DisplayName("모든 사용자는 등록된 회원 주소 목록을 조회할 수 있다.")
    @Test
    void getMemberAddresses() {
        // given
        Member member = createMember(Role.CLIENT);
        MemberAddress memberAddress1 = createMemberAddress(member, "주소1");
        MemberAddress memberAddress2 = createMemberAddress(member, "주소2");

        // when
        MemberAddressResponse response = memberAddressQueryService.getMemberAddresses(member.getEmail(), "");
        log.debug("response={}", response);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getHasNext()).isFalse();
        assertThat(response.getMemberAddresses()).hasSize(2);
    }

    @DisplayName("등록된 주소가 없으면 빈 리스트가 반환된다.")
    @Test
    void getEmptyMemberAddresses() {
        // given
        Member member = createMember(Role.CLIENT);

        // when
        MemberAddressResponse response = memberAddressQueryService.getMemberAddresses(member.getEmail(), "");
        log.debug("response={}", response);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getHasNext()).isFalse();
        assertThat(response.getMemberAddresses()).isEmpty();
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
