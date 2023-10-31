package com.boyworld.carrot.api.service.member;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.controller.member.response.ClientResponse;
import com.boyworld.carrot.api.controller.member.response.JoinMemberResponse;
import com.boyworld.carrot.api.controller.member.response.MemberAddressDetailResponse;
import com.boyworld.carrot.api.controller.member.response.VendorResponse;
import com.boyworld.carrot.api.service.member.dto.EditMemberDto;
import com.boyworld.carrot.api.service.member.dto.JoinMemberDto;
import com.boyworld.carrot.api.service.member.error.DuplicateException;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.MemberAddress;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.VendorInfo;
import com.boyworld.carrot.domain.member.repository.MemberAddressRepository;
import com.boyworld.carrot.domain.member.repository.MemberRepository;
import com.boyworld.carrot.domain.member.repository.VendorInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 회원 서비스 테스트
 *
 * @author 최영환
 */
@Slf4j
class MemberServiceTest extends IntegrationTestSupport {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberAddressRepository memberAddressRepository;

    @Autowired
    private VendorInfoRepository vendorInfoRepository;

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
                .role("CLIENT")
                .build();
        Member member = createMember(Role.CLIENT);

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
                .role("CLIENT")
                .build();

        // when
        JoinMemberResponse response = memberService.join(dto);

        // then
        assertThat(response)
                .extracting("email", "nickname", "name", "phoneNumber", "role")
                .containsExactlyInAnyOrder("ssafy@ssafy.com", "매미킴", "김동현", "010-1234-5678", "CLIENT");
    }

    @DisplayName("일반 사용자는 회원 정보 수정을 할 수 있다.")
    @Test
    void editClientSuccess() {
        // given
        Member member = createMember(Role.CLIENT);
        EditMemberDto dto = EditMemberDto.builder()
                .name("박동현")
                .email("ssafy@ssafy.com")
                .nickname("매미킴123")
                .phoneNumber("010-5678-1234")
                .build();

        // when
        ClientResponse response = memberService.editClient(dto);

        // then
        assertThat(response).extracting("name", "nickname", "email", "phoneNumber", "role")
                .containsExactlyInAnyOrder("박동현", "매미킴123", "ssafy@ssafy.com", "010-5678-1234", "CLIENT");
    }

    @DisplayName("사업자는 회원 정보 수정을 할 수 있다.")
    @Test
    void editVendorSuccess() {
        // given
        Member member = createMember(Role.VENDOR);
        VendorInfo vendorInfo = createVendorInfo(member);
        EditMemberDto dto = EditMemberDto.builder()
                .name("박동현")
                .email("ssafy@ssafy.com")
                .nickname("매미킴123")
                .phoneNumber("010-5678-1234")
                .build();

        // when
        VendorResponse response = memberService.editVendor(dto);
        log.debug("response={}", response);
        // then
        assertThat(response).extracting("name", "nickname", "email", "phoneNumber", "role", "businessNumber")
                .containsExactlyInAnyOrder("박동현", "매미킴123", "ssafy@ssafy.com", "010-5678-1234", "VENDOR", "123456789");
    }

    @DisplayName("모든 사용자는 회원 탈퇴를 할 수 있다.")
    @Test
    void withdrawal() {
        // given
        Member member = createMember(Role.CLIENT);

        // when
        Boolean result = memberService.withdrawal(member.getEmail(), "ssafy1234");

        // then
        assertThat(result).isTrue();

    }

    @DisplayName("잘못된 비밀번호를 입력하면 탈퇴를 할 수 없다ㅏ.")
    @Test
    void withdrawalWithWrongPassword() {
        // given
        Member member = createMember(Role.CLIENT);

        // when
        Boolean result = memberService.withdrawal(member.getEmail(), "ssafy!");

        // then
        assertThat(result).isFalse();

    }

    @DisplayName("사용자는 회원 주소를 등록할 수 있다.")
    @Test
    void createMemberAddressSuccess() {
        // given
        Member member = createMember(Role.CLIENT);

        // when
        MemberAddressDetailResponse response = memberService.createMemberAddress("주소", "ssafy@ssafy.com");
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
        MemberAddressDetailResponse response = memberService.editMemberAddress(memberAddress.getId(),
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
                memberService.editMemberAddress(2L, "변경할 주소", member.getEmail()))
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
        Boolean result = memberService.deleteMemberAddress(memberAddress.getId(), member.getEmail());
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
                memberService.deleteMemberAddress(2L, member.getEmail()))
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

    private VendorInfo createVendorInfo(Member member) {
        VendorInfo vendorInfo = VendorInfo.builder()
                .tradeName("상호명")
                .vendorName(member.getName())
                .businessNumber("123456789")
                .phoneNumber(member.getPhoneNumber())
                .member(member)
                .active(true)
                .build();
        return vendorInfoRepository.save(vendorInfo);
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