package com.boyworld.carrot.api.service.member.command;

import com.boyworld.carrot.api.controller.member.response.MemberAddressDetailResponse;
import com.boyworld.carrot.api.service.member.dto.SelectedMemberAddressDto;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.MemberAddress;
import com.boyworld.carrot.domain.member.repository.command.MemberAddressRepository;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
import com.boyworld.carrot.domain.member.repository.query.MemberAddressQueryRepository;
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

    private final MemberAddressQueryRepository memberAddressQueryRepository;

    /**
     * 회원 주소 등록
     *
     * @param address 등록할 주소
     * @param email   현재 로그인 중인 회원 이메일
     * @return 등록된 회원 주소 정보
     */
    public MemberAddressDetailResponse createMemberAddress(String address, String email) {
        Member member = findMemberByEmail(email);

        checkActiveMember(member);

        boolean selected = checkSelected(email);

        MemberAddress savedMemberAddress = saveMemberAddress(address, member, selected);

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
     * 회원 탈퇴 여부 확인
     *
     * @param member 회원 엔티티
     * @throws NoSuchElementException 이미 탈퇴한 회원인 경우
     */
    private void checkActiveMember(Member member) {
        if (!member.getActive()) {
            throw new NoSuchElementException("이미 탈퇴한 회원입니다.");
        }
    }

    /**
     * 선택 주소 여부 판별
     *
     * @param email 현재 로그인 중인 회원 이메일
     * @return true: 해당 회원의 회원 주소 중 선택된 주소가 없는 경우 / false: 선택된 주소가 이미 존재하는 경우
     */
    private boolean checkSelected(String email) {
        Long selectedCount = memberAddressQueryRepository.getSelectedCountByEmail(email);
        return selectedCount == null || selectedCount == 0L;
    }

    /**
     * 회원 주소 저장
     *
     * @param address 등록할 주소
     * @param member  회원 엔티티
     * @return 저장된 회원 엔티티
     */
    private MemberAddress saveMemberAddress(String address, Member member, boolean selected) {
        MemberAddress memberAddress = MemberAddress.builder()
                .address(address)
                .member(member)
                .selected(selected)
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
     * @return true: 삭제 완료 / false: 삭제 실패
     */
    public Boolean deleteMemberAddress(Long memberAddressId) {
        MemberAddress memberAddress = getMemberAddressById(memberAddressId);

        memberAddress.deActivate();

        return true;
    }

    /**
     * 선택된 주소 변경 API
     *
     * @param dto 기존에 선택된 주소와 새로 선택할 주소 식별키
     * @return 새로 선택된 주소 식별키
     */
    public Long editSelectedAddress(SelectedMemberAddressDto dto) {
        MemberAddress selectedAddress = getMemberAddressById(dto.getSelectedMemberAddressId());
        selectedAddress.unSelect();

        MemberAddress targetAddress = getMemberAddressById(dto.getTargetMemberAddressId());
        targetAddress.select();

        return targetAddress.getId();
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
