package com.boyworld.carrot.domain.member.repository;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.controller.member.response.ClientResponse;
import com.boyworld.carrot.api.controller.member.response.VendorResponse;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.VendorInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 회원 조회용 레포지토리 테스트
 *
 * @author 최영환
 */
@Slf4j
class MemberQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberQueryRepository memberQueryRepository;

    @Autowired
    private VendorInfoRepository vendorInfoRepository;

    @DisplayName("이미 등록된 사용자 이메일이라면 true 를 반환한다.")
    @Test
    void isExistingClientEmailWithTrue() {
        // given
        Member member = createMember(Role.CLIENT, "ssafy@ssafy.com");

        // when
        Boolean result = memberQueryRepository.isExistEmail("ssafy@ssafy.com");

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("이미 등록된 사업자 이메일이라면 true 를 반환한다.")
    @Test
    void isExistingVendorEmailWithTrue() {
        // given
        Member member = createMember(Role.VENDOR, "ssafy@ssafy.com");

        // when
        Boolean result = memberQueryRepository.isExistEmail("ssafy@ssafy.com");

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("이미 등록되지 않은 이메일이라면 false 를 반환한다.")
    @Test
    void isExistingClientEmailWithFalse() {
        // given
        Member client = createMember(Role.CLIENT, "ssafy@ssafy.com");
        String email = "ssafy@naver.com";

        // when
        Boolean result = memberQueryRepository.isExistEmail(email);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("이미 등록되지 않은 이메일이라면 false 를 반환한다.")
    @Test
    void isExistingVendorEmailWithFalse() {
        // given
        Member vendor = createMember(Role.VENDOR, "ssafy@ssafy.com");
        String email = "ssafy@naver.com";

        // when
        Boolean result = memberQueryRepository.isExistEmail(email);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("일반 사용자 정보를 이메일로 조회한다.")
    @Test
    void getExistClientInfoByClient() {
        // given
        Member member = createMember(Role.CLIENT, "ssafy@ssafy.com");
        String email = "ssafy@ssafy.com";

        // when
        ClientResponse response = memberQueryRepository.getClientInfoByEmail(email);

        // then
        assertThat(response).extracting("name", "nickname", "email", "phoneNumber", "role")
                .containsExactlyInAnyOrder("김동현", "매미킴", "ssafy@ssafy.com", "010-1234-5678", "CLIENT");
    }

    @DisplayName("사업자도 일반 사용자로서의 정보를 조회할 수 있다.")
    @Test
    void getExistClientInfoByVendor() {
        // given
        Member member = createMember(Role.VENDOR, "ssafy@ssafy.com");
        String email = "ssafy@ssafy.com";

        // when
        ClientResponse response = memberQueryRepository.getClientInfoByEmail(email);

        // then
        assertThat(response).extracting("name", "nickname", "email", "phoneNumber", "role")
                .containsExactlyInAnyOrder("김동현", "매미킴", "ssafy@ssafy.com", "010-1234-5678", "VENDOR");
    }

    @DisplayName("해당 이메일을 가진 일반 사용자가 존재하지 않으면 null 이 반환된다.")
    @Test
    void getNotExistClientInfo() {
        // given
        Member member = createMember(Role.CLIENT, "ssafy@ssafy.com");
        String email = "ssafy@naver.com";

        // when
        ClientResponse response = memberQueryRepository.getClientInfoByEmail(email);

        // then
        assertThat(response).isNull();
    }

    @DisplayName("사업자 정보를 이메일로 조회한다.")
    @Test
    void getExistVendorInfo() {
        // given
        Member member = createMember(Role.VENDOR, "ssafy@ssafy.com");
        VendorInfo vendorInfo = createVendorInfo(member);
        String email = "ssafy@ssafy.com";

        // when
        VendorResponse response = memberQueryRepository.getVendorInfoByEmail(email);

        // then
        assertThat(response).isNotNull();
        assertThat(response).extracting("name", "nickname", "email", "phoneNumber", "role", "businessNumber")
                .containsExactlyInAnyOrder("김동현", "매미킴", "ssafy@ssafy.com", "010-1234-5678", "VENDOR", "123456789");
    }

    @DisplayName("해당 이메일을 가진 사업자가 존재하지 않으면 null 이 반환된다.")
    @Test
    void getVendorInfoByClientEmail() {
        // given
        Member member = createMember(Role.CLIENT, "ssafy@ssafy.com");
        VendorInfo vendorInfo = createVendorInfo(member);
        String email = "ssafy@ssafy.com";

        // when
        VendorResponse response = memberQueryRepository.getVendorInfoByEmail(email);

        // then
        assertThat(response).isNull();
    }

    private Member createMember(Role role, String email) {
        Member member = Member.builder()
                .email(email)
                .nickname("매미킴")
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