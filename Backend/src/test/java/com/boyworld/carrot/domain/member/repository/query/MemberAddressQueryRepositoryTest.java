package com.boyworld.carrot.domain.member.repository.query;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.controller.member.response.MemberAddressDetailResponse;
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

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 회원 주소 조회용 레포지토리 테스트
 *
 * @author 최영환
 */
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
        MemberAddress memberAddress1 = createMemberAddress(member, "주소1", true, true);
        MemberAddress memberAddress2 = createMemberAddress(member, "주소2", false, true);

        // when
        List<MemberAddressDetailResponse> responses =
                memberAddressQueryRepository.getMemberAddressesByEmail(member.getEmail(), 0L);

        // then
        assertThat(responses).isNotEmpty();
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

    @DisplayName("사용자는 회원 주소 하나를 조회할 수 있다.")
    @Test
    void getExistingMemberAddress() {
        // given
        Member member = createMember(Role.CLIENT);
        MemberAddress memberAddress = createMemberAddress(member, "주소1", true, true);

        // when
        MemberAddressDetailResponse response =
                memberAddressQueryRepository.getMemberAddressById(memberAddress.getId())
                        .orElseThrow(NoSuchElementException::new);

        // then
        assertThat(response).extracting("address")
                .isEqualTo("주소1");
    }

    @DisplayName("존재하지 않는 회원 주소를 조회하면 빈 객체가 반환된다.")
    @Test
    void getNotExistingMemberAddress() {
        // given
        Member member = createMember(Role.CLIENT);
        MemberAddress memberAddress = createMemberAddress(member, "주소1", true, true);

        // when
        Optional<MemberAddressDetailResponse> response =
                memberAddressQueryRepository.getMemberAddressById(2L);

        // then
        assertThat(response.isEmpty()).isTrue();
    }

    @DisplayName("이메일로 선택된 회원 주소의 개수를 반환한다.")
    @Test
    void getSelectedCountByEmail() {
        // given
        Member member = createMember(Role.CLIENT);
        MemberAddress memberAddress = createMemberAddress(member, "주소1", true, true);

        // when
        Long result = memberAddressQueryRepository.getSelectedCountByEmail("ssafy@gmail.com");

        // then
        assertThat(result).isEqualTo(1);
    }

    @DisplayName("이메일로 선택된 회원 주소가 없으면 0 을 반환한다.")
    @Test
    void getSelectedCountByEmailWithoutSelected() {
        // given
        Member member = createMember(Role.CLIENT);

        // when
        Long result = memberAddressQueryRepository.getSelectedCountByEmail("ssafy@gmail.com");

        // then
        assertThat(result).isZero();
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