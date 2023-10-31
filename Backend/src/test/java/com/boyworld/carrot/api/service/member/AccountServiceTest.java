package com.boyworld.carrot.api.service.member;


import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.controller.member.response.ClientResponse;
import com.boyworld.carrot.api.controller.member.response.VendorResponse;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.VendorInfo;
import com.boyworld.carrot.domain.member.repository.MemberQueryRepository;
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
 * 계정 서비스 테스트
 *
 * @author 최영환
 */
@Slf4j
class AccountServiceTest extends IntegrationTestSupport {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private VendorInfoRepository vendorInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private MemberQueryRepository memberQueryRepository;

    @DisplayName("일반 사용자 정보를 이메일로 조회한다.")
    @Test
    void getExistClientInfo() {
        // given
        Member member = createMember(Role.CLIENT);
        String email = "ssafy@ssafy.com";

        // when
        ClientResponse response = accountService.getClientInfo(email);

        // then
        assertThat(response).isNotNull();
        assertThat(response).extracting("name", "nickname", "email", "phoneNumber", "role")
                .containsExactlyInAnyOrder("김동현", "매미킴", "ssafy@ssafy.com", "010-1234-5678", "CLIENT");
    }

    @DisplayName("사업자도 일반 사용자로서 조회를 이용할 수 있다.")
    @Test
    void getExistClientInfoByVendor() {
        // given
        Member member = createMember(Role.VENDOR);
        String email = "ssafy@ssafy.com";

        // when
        ClientResponse response = accountService.getClientInfo(email);

        // then
        assertThat(response).isNotNull();
        assertThat(response).extracting("name", "nickname", "email", "phoneNumber", "role")
                .containsExactlyInAnyOrder("김동현", "매미킴", "ssafy@ssafy.com", "010-1234-5678", "VENDOR");
    }

    @DisplayName("이메일에 해당하는 회원이 존재하지 않을 경우 예외가 발생한다.")
    @Test
    void getNotExistClientInfo() {
        // given
        Member member = createMember(Role.CLIENT);
        String email = "ssafy@naver.com";

        // when // then
        assertThatThrownBy(() -> accountService.getClientInfo(email))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @DisplayName("사업자 정보를 이메일로 조회한다.")
    @Test
    void getExistVendorInfo() {
        // given
        Member member = createMember(Role.VENDOR);
        VendorInfo vendorInfo = createVendorInfo(member);
        String email = "ssafy@ssafy.com";

        // when
        VendorResponse response = accountService.getVendorInfo(email);

        // then
        assertThat(response).isNotNull();
        assertThat(response).extracting("name", "nickname", "email", "phoneNumber", "role", "businessNumber")
                .containsExactlyInAnyOrder("김동현", "매미킴", "ssafy@ssafy.com", "010-1234-5678", "VENDOR", "123456789");
    }

    @DisplayName("해당 이메일의 사업자가 존재하지 않으면 예외가 발생한다.")
    @Test
    void getNotExistingVendorInfo() {
        // given
        Member member = createMember(Role.VENDOR);
        VendorInfo vendorInfo = createVendorInfo(member);
        String email = "ssafy@naver.com";

        // when // then
        assertThatThrownBy(() -> accountService.getVendorInfo(email))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @DisplayName("사용자로 사업자 정보를 조회하려하면 예외가 발생한다.")
    @Test
    void getVendorInfoByClient() {
        // given
        Member member = createMember(Role.CLIENT);
        String email = "ssafy@ssafy.com";

        // when // then
        assertThatThrownBy(() -> accountService.getVendorInfo(email))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("존재하지 않는 회원입니다.");
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
}