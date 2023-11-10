package com.boyworld.carrot.api.service.member.query;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.controller.member.response.VendorInfoResponse;
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
 * 사업자 정보 조회 서비스 테스트
 *
 * @author 최영환
 */
@Slf4j
class VendorInfoQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private VendorInfoQueryService vendorInfoQueryService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private VendorInfoRepository vendorInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @DisplayName("사업자는 자신의 사업자 정보를 조회할 수 있다.")
    @Test
    void getVendorInfo() {
        // given
        Member member = createMember(Role.VENDOR);
        VendorInfo vendorInfo = createVendorInfo(member);

        // when
        VendorInfoResponse response = vendorInfoQueryService.getVendorInfo("ssafy@gmail.com");

        // then
        assertThat(response).extracting("tradeName", "vendorName", "businessNumber", "phoneNumber")
                .containsExactlyInAnyOrder("무적의 소년천지", "김동현", "1515-302-006031", "010-1234-5678");
    }

    @DisplayName("존재하지 않는 사업자 정보를 조회하면 예외가 발생한다.")
    @Test
    void getVendorInfoNotExisting() {
        // given
        Member member = createMember(Role.VENDOR);

        // when // then
        assertThatThrownBy(() -> vendorInfoQueryService.getVendorInfo("ssafy1234@ssafy.com"))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("존재하지 않는 사업자 정보입니다.");

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

    private VendorInfo createVendorInfo(Member member) {
        VendorInfo vendorInfo = VendorInfo.builder()
                .tradeName("무적의 소년천지")
                .vendorName(member.getName())
                .businessNumber("1515-302-006031")
                .phoneNumber(member.getPhoneNumber())
                .member(member)
                .active(true)
                .build();
        return vendorInfoRepository.save(vendorInfo);
    }
}