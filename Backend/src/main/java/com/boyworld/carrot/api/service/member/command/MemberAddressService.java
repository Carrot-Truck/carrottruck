package com.boyworld.carrot.api.service.member.command;

import com.boyworld.carrot.api.controller.member.response.MemberAddressDetailResponse;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.MemberAddress;
import com.boyworld.carrot.domain.member.repository.command.MemberAddressRepository;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

/**
 * 회원 주소 CUD 서비스
 *
 * @author 최영환
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberAddressService {

    private final MemberAddressRepository memberAddressRepository;

    private final MemberRepository memberRepository;

    /**
     * 회원 주소 등록
     *
     * @param address 등록할 주소
     * @param email   현재 로그인 중인 회원 이메일
     * @return 등록된 회원 주소 정보
     */
    public MemberAddressDetailResponse createMemberAddress(String address, String email) {
        Member findMember = findMemberByEmail(email);

        MemberAddress savedMemberAddress = saveMemberAddress(address, findMember);

        return MemberAddressDetailResponse.of(savedMemberAddress);
    }

    /**
     * 이메일로 회원 조회
     *
     * @param email 찾을 회원 이메일
     * @return 이메일에 해당하는 회원
     * @throws NoSuchElementException 이메일에 해당하는 회원이 없는 경우
     */
    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
    }

    /**
     * 회원 주소 저장
     *
     * @param address 등록할 주소
     * @param member  회원 엔티티
     * @return 저장된 회원 엔티티
     */
    private MemberAddress saveMemberAddress(String address, Member member) {
        MemberAddress memberAddress = MemberAddress.builder()
                .address(address)
                .member(member)
                .active(true)
                .build();
        return memberAddressRepository.save(memberAddress);
    }

    /**
     * 회원 주소 수정
     *
     * @param memberAddressId 수정할 주소 식별키
     * @param address         수정할 주소 정보
     * @param email           로그인 중인 회원 이메일
     * @return 수정된 주소 정보
     */
    public MemberAddressDetailResponse editMemberAddress(Long memberAddressId, String address, String email) {
        MemberAddress memberAddress = getMemberAddressById(memberAddressId);

        memberAddress.editAddress(address);

        return MemberAddressDetailResponse.of(memberAddress);
    }

    /**
     * 회원 주소 삭제
     *
     * @param memberAddressId 삭제할 회원 주소
     * @param email           로그인 중인 회원 이메일
     * @return true: 삭제 완료 / false: 삭제 실패
     */
    public Boolean deleteMemberAddress(Long memberAddressId, String email) {
        MemberAddress memberAddress = getMemberAddressById(memberAddressId);

        memberAddress.deActivate();

        return true;
    }

    /**
     * 회원 주소 식별키로 식별키로 회원 주소 조회
     *
     * @param memberAddressId 회원 주소 식별키
     * @return 회원 주소 엔티티
     * @throws NoSuchElementException 회원 주소 식별키에 해당하는 회원 주소가 존재하지 않을 경우
     */
    private MemberAddress getMemberAddressById(Long memberAddressId) {
        return memberAddressRepository.findById(memberAddressId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원 주소입니다."));
    }
}
