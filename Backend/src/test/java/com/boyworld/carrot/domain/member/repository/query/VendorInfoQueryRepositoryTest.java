package com.boyworld.carrot.domain.member.repository.query;

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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 사업자 정보 조회용 레포지토리 테스트
 *
 * @author 최영환
 */
@Slf4j
class VendorInfoQueryRepositoryTest extends IntegrationTestSupport {
    @Autowired
    private VendorInfoQueryRepository vendorInfoQueryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private VendorInfoRepository vendorInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @DisplayName("사업자 정보를 이메일로 조회한다.")
    @Test
    void getVendorInfoByEmail() {
        // given
        Member member = createMember(Role.VENDOR);
        VendorInfo vendorInfo = createVendorInfo(member);

        // when
        Optional<VendorInfoResponse> response = vendorInfoQueryRepository.getVendorInfoByEmail("ssafy@ssafy.com");

        // then
        assertThat(response.isPresent()).isTrue();
        assertThat(response.get()).extracting("tradeName", "vendorName", "businessNumber", "phoneNumber")
                .containsExactlyInAnyOrder("무적의 소년천지", "김동현", "1515-302-006031", "010-1234-5678");
    }

    @DisplayName("존재하지 않는 사업자 정보를 조회하면 빈 객체가 반환된다.")
    @Test
    void getVendorInfoByEmailNotExisting() {
        // given
        Member member = createMember(Role.VENDOR);

        // when
        Optional<VendorInfoResponse> response = vendorInfoQueryRepository.getVendorInfoByEmail("ssafy@ssafy.com");

        // then
        assertThat(response.isEmpty()).isTrue();
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