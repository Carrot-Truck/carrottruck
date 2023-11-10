package com.boyworld.carrot.api.service.member.query;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.controller.member.response.MemberAddressDetailResponse;
import com.boyworld.carrot.api.controller.member.response.MemberAddressResponse;
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

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 회원 주소 조회 서비스 테스트
 *
 * @author 최영환
 */
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
        MemberAddress memberAddress1 = createMemberAddress(member, "주소1", true, true);
        MemberAddress memberAddress2 = createMemberAddress(member, "주소2", false, true);

        // when
        MemberAddressResponse response = memberAddressQueryService.getMemberAddresses(member.getEmail(), null);
        log.debug("response={}", response);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getHasNext()).isFalse();
        assertThat(response.getMemberAddresses()).isNotEmpty();
    }

    @DisplayName("등록된 주소가 없으면 빈 리스트가 반환된다.")
    @Test
    void getEmptyMemberAddresses() {
        // given
        Member member = createMember(Role.CLIENT);

        // when
        MemberAddressResponse response = memberAddressQueryService.getMemberAddresses(member.getEmail(), null);
        log.debug("response={}", response);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getHasNext()).isFalse();
        assertThat(response.getMemberAddresses()).isEmpty();
    }

    @DisplayName("사용자는 회원 주소 하나를 조회할 수 있다.")
    @Test
    void getExistingMemberAddress() {
        // given
        Member member = createMember(Role.CLIENT);
        MemberAddress memberAddress = createMemberAddress(member, "주소1", true, true);

        // when
        MemberAddressDetailResponse response =
                memberAddressQueryService.getMemberAddress(memberAddress.getId());

        // then
        assertThat(response).extracting("address")
                .isEqualTo("주소1");
    }

    @DisplayName("존재하지 않는 회원 주소를 조회하면 예외가 발생한다.")
    @Test
    void getNotExistingMemberAddress() {
        // given
        Member member = createMember(Role.CLIENT);

        // when // then
        assertThatThrownBy(() -> memberAddressQueryService.getMemberAddress(2L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("존재하지 않는 회원 주소입니다.");
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

    private MemberAddress createMemberAddress(Member member, String address, boolean selected, boolean active) {
        MemberAddress memberAddress = MemberAddress.builder()
                .member(member)
                .address(address)
                .selected(selected)
                .active(active)
                .build();
        return memberAddressRepository.save(memberAddress);
    }
}
