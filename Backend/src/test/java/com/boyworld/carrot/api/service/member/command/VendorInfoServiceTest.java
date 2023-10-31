package com.boyworld.carrot.api.service.member.command;


import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.controller.member.response.VendorInfoResponse;
import com.boyworld.carrot.api.service.member.dto.CreateVendorInfoDto;
import com.boyworld.carrot.api.service.member.error.InvalidAccessException;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.VendorInfo;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
import com.boyworld.carrot.domain.member.repository.command.VendorInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 사업자 정보 CUD 서비스 테스트
 *
 * @author 최영환
 */
@Slf4j
class VendorInfoServiceTest extends IntegrationTestSupport {

    @Autowired
    private VendorInfoService vendorInfoService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private VendorInfoRepository vendorInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @DisplayName("사업자 회원은 사업자 정보를 등록할 수 있다.")
    @Test
    void createVendorInfoWithVendor() {
        // given
        Member member = createMember(Role.VENDOR);
        CreateVendorInfoDto dto = CreateVendorInfoDto.builder()
                .tradeName("무적의 소년천지")
                .vendorName("김동현")
                .businessNumber("1515-302-006031")
                .phoneNumber("010-1234-5678")
                .build();

        // when
        VendorInfoResponse response = vendorInfoService.createVendorInfo(dto, "ssafy@ssafy.com");

        // then
        assertThat(response).extracting("tradeName", "vendorName", "businessNumber", "phoneNumber")
                .containsExactlyInAnyOrder("무적의 소년천지", "김동현", "1515-302-006031", "010-1234-5678");
    }

    @DisplayName("일반 사용자 회원은 사업자 정보를 등록할 수 없다.")
    @Test
    void createVendorInfoWithClient() {
        // given
        Member member = createMember(Role.CLIENT);
        CreateVendorInfoDto dto = CreateVendorInfoDto.builder()
                .tradeName("무적의 소년천지")
                .vendorName("김동현")
                .businessNumber("1515-302-006031")
                .phoneNumber("010-1234-5678")
                .build();

        // when // then
        assertThatThrownBy(() -> vendorInfoService.createVendorInfo(dto, "ssafy@ssafy.com"))
                .isInstanceOf(InvalidAccessException.class)
                .hasMessage("잘못된 접근입니다.");
    }

    @DisplayName("존재하지 않는 회원에 대한 요청을 할 수 없다.")
    @Test
    void createVendorInfoWithoutMember() {
        // given
        Member member = createMember(Role.VENDOR);
        CreateVendorInfoDto dto = CreateVendorInfoDto.builder()
                .tradeName("무적의 소년천지")
                .vendorName("김동현")
                .businessNumber("1515-302-006031")
                .phoneNumber("010-1234-5678")
                .build();

        // when // then
        assertThatThrownBy(() -> vendorInfoService.createVendorInfo(dto, "ssafy1234@ssafy.com"))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @DisplayName("사업자는 자신의 사업자 정보를 삭제할 수 있다.")
    @Test
    void deleteVendorInfo() {
        // given
        Member member = createMember(Role.VENDOR);
        VendorInfo vendorInfo = createVendorInfo(member, true);

        // when
        Boolean result = vendorInfoService.deleteVendorInfo(vendorInfo.getId());

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("존재하지 않는 사업자 정보는 삭제할 수 없다.")
    @Test
    void deleteVendorInfoNotExisting() {
        // given
        Member member = createMember(Role.VENDOR);
        VendorInfo vendorInfo = createVendorInfo(member, true);

        // when // then
        assertThatThrownBy(() -> vendorInfoService.deleteVendorInfo(0L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("존재하지 않는 사업자 정보입니다.");
    }

    @DisplayName("이미 삭제된 사업자 정보는 삭제할 수 없다.")
    @Test
    void deleteVendorInfoAlreadyDeleted() {
        // given
        Member member = createMember(Role.VENDOR);
        VendorInfo vendorInfo = createVendorInfo(member, false);

        // when // then
        assertThatThrownBy(() -> vendorInfoService.deleteVendorInfo(vendorInfo.getId()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("이미 삭제된 사업자 정보입니다.");
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

    private VendorInfo createVendorInfo(Member member, boolean active) {
        VendorInfo vendorInfo = VendorInfo.builder()
                .tradeName("상호명")
                .vendorName(member.getName())
                .businessNumber("123456789")
                .phoneNumber(member.getPhoneNumber())
                .member(member)
                .active(active)
                .build();
        return vendorInfoRepository.save(vendorInfo);
    }
}