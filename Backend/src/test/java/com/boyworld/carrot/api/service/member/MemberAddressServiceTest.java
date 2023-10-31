package com.boyworld.carrot.api.service.member;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.controller.member.response.MemberAddressDetailResponse;
import com.boyworld.carrot.api.service.member.command.MemberAddressService;
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

@Slf4j
public class MemberAddressServiceTest extends IntegrationTestSupport {

    @Autowired
    private MemberAddressService memberAddressService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberAddressRepository memberAddressRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @DisplayName("사용자는 회원 주소를 등록할 수 있다.")
    @Test
    void createMemberAddressSuccess() {
        // given
        Member member = createMember(Role.CLIENT);

        // when
        MemberAddressDetailResponse response = memberAddressService.createMemberAddress("주소", "ssafy@ssafy.com");
        log.debug("response={}", response);

        // then
        assertThat(response).extracting("address")
                .isEqualTo("주소");
    }

    @DisplayName("사용자는 회원 주소를 수정할 수 있다.")
    @Test
    void editMemberAddress() {
        // given
        Member member = createMember(Role.CLIENT);
        MemberAddress memberAddress = createMemberaddress(member);

        // when
        MemberAddressDetailResponse response = memberAddressService.editMemberAddress(memberAddress.getId(),
                "변경할 주소", member.getEmail());
        log.debug("response={}", response);

        // then
        assertThat(response).extracting("address")
                .isEqualTo("변경할 주소");
    }

    @DisplayName("회원 주소 수정 시 잘못된 식별키로 요청하면 예외가 발생한다.")
    @Test
    void editMemberAddressWithWrongId() {
        // given
        Member member = createMember(Role.CLIENT);
        MemberAddress memberAddress = createMemberaddress(member);

        // when // then
        assertThatThrownBy(() ->
                memberAddressService.editMemberAddress(2L, "변경할 주소", member.getEmail()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("존재하지 않는 회원 주소입니다.");
    }

    @DisplayName("사용자는 회원 주소를 삭제할 수 있다.")
    @Test
    void deleteMemberAddress() {
        // given
        Member member = createMember(Role.CLIENT);
        MemberAddress memberAddress = createMemberaddress(member);

        // when
        Boolean result = memberAddressService.deleteMemberAddress(memberAddress.getId(), member.getEmail());
        log.debug("result={}", result);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("회원 주소 삭제 시 잘못된 식별키로 요청하면 예외가 발생한다.")
    @Test
    void deleteMemberAddressWithWrongId() {
        // given
        Member member = createMember(Role.CLIENT);
        MemberAddress memberAddress = createMemberaddress(member);

        // when // then
        assertThatThrownBy(() ->
                memberAddressService.deleteMemberAddress(2L, member.getEmail()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("존재하지 않는 회원 주소입니다.");
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

    private MemberAddress createMemberaddress(Member member) {
        MemberAddress memberAddress = MemberAddress.builder()
                .member(member)
                .address("주소")
                .active(true)
                .build();

        return memberAddressRepository.save(memberAddress);
    }
}
