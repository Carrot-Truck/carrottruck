package com.boyworld.carrot.api.service.member.command;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.controller.member.response.MemberAddressDetailResponse;
import com.boyworld.carrot.api.service.member.dto.SelectedMemberAddressDto;
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
 * 회원 주소 CUD 서비스 테스트
 *
 * @author 최영환
 */
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
    void createMemberAddressFirst() {
        // given
        Member member = createMember(Role.CLIENT, true);

        // when
        MemberAddressDetailResponse response = memberAddressService.createMemberAddress("주소", "ssafy@ssafy.com");
        log.debug("response={}", response);

        // then
        assertThat(response.getAddress()).isEqualTo("주소");
        assertThat(response.getSelected()).isTrue();
    }

    @DisplayName("이미 등록된 주소가 있는 경우 선택 여부는 false 로 들어간다.")
    @Test
    void createMemberAddressNotFirst() {
        // given
        Member member = createMember(Role.CLIENT, true);
        MemberAddress memberAddress = createMemberaddress(member, true);

        // when
        MemberAddressDetailResponse response = memberAddressService.createMemberAddress("주소", "ssafy@ssafy.com");
        log.debug("response={}", response);

        // then
        assertThat(response.getAddress()).isEqualTo("주소");
        assertThat(response.getSelected()).isFalse();
    }

    @DisplayName("이미 탈퇴한 사용자는 주소 등록 요청을 보낼 수 없다.")
    @Test
    void createMemberAddressWithNonActiveMember() {
        // given
        Member member = createMember(Role.CLIENT, false);

        // when // then
        assertThatThrownBy(() -> memberAddressService.createMemberAddress("주소", "ssafy@ssafy.com"))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("이미 탈퇴한 회원입니다.");
    }

    @DisplayName("사용자는 회원 주소를 수정할 수 있다.")
    @Test
    void editMemberAddress() {
        // given
        Member member = createMember(Role.CLIENT, true);
        MemberAddress memberAddress = createMemberaddress(member, true);

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
        Member member = createMember(Role.CLIENT, true);
        MemberAddress memberAddress = createMemberaddress(member, true);

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
        Member member = createMember(Role.CLIENT, true);
        MemberAddress memberAddress = createMemberaddress(member, true);

        // when
        Boolean result = memberAddressService.deleteMemberAddress(memberAddress.getId());
        log.debug("result={}", result);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("회원 주소 삭제 시 잘못된 식별키로 요청하면 예외가 발생한다.")
    @Test
    void deleteMemberAddressWithWrongId() {
        // given
        Member member = createMember(Role.CLIENT, true);
        MemberAddress memberAddress = createMemberaddress(member, true);

        // when // then
        assertThatThrownBy(() ->
                memberAddressService.deleteMemberAddress(2L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("존재하지 않는 회원 주소입니다.");
    }

    @DisplayName("사용자는 현재 선택된 주소를 변경할 수 있다.")
    @Test
    void editSelected() {
        // given
        Member member = createMember(Role.CLIENT, true);
        MemberAddress selectedMemberAddress = createMemberaddress(member, true);
        MemberAddress tragetMemberAddress = createMemberaddress(member, false);

        SelectedMemberAddressDto dto = SelectedMemberAddressDto.builder()
                .selectedMemberAddressId(selectedMemberAddress.getId())
                .targetMemberAddressId(tragetMemberAddress.getId())
                .build();

        // when
        Long result = memberAddressService.editSelectedAddress(dto);

        // then
        assertThat(result).isEqualTo(tragetMemberAddress.getId());
        assertThat(selectedMemberAddress.getSelected()).isFalse();
        assertThat(tragetMemberAddress.getSelected()).isTrue();
    }

    private Member createMember(Role role, boolean active) {
        Member member = Member.builder()
                .email("ssafy@ssafy.com")
                .nickname("매미킴")
                .encryptedPwd(passwordEncoder.encode("ssafy1234"))
                .name("김동현")
                .phoneNumber("010-1234-5678")
                .role(role)
                .active(active)
                .build();
        return memberRepository.save(member);
    }

    private MemberAddress createMemberaddress(Member member, Boolean selected) {
        MemberAddress memberAddress = MemberAddress.builder()
                .member(member)
                .address("주소")
                .selected(selected)
                .active(true)
                .build();

        return memberAddressRepository.save(memberAddress);
    }
}
