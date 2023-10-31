package com.boyworld.carrot.api.service.member.command;

import com.boyworld.carrot.api.controller.member.response.VendorInfoResponse;
import com.boyworld.carrot.api.service.member.dto.CreateVendorInfoDto;
import com.boyworld.carrot.api.service.member.error.InvalidAccessException;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.VendorInfo;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
import com.boyworld.carrot.domain.member.repository.command.VendorInfoRepository;
import com.boyworld.carrot.domain.member.repository.query.VendorInfoQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

/**
 * 사업자 정보 CUD 서비스
 *
 * @author 최영환
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class VendorInfoService {

    private final VendorInfoRepository vendorInfoRepository;
    private final VendorInfoQueryRepository vendorInfoQueryRepository;
    private final MemberRepository memberRepository;

    /**
     * 사업자 정보 등록
     *
     * @param dto   사업자 정보
     * @param email 현재 로그인 한 사용자 이메일
     * @return 등록된 사용자 정보
     */
    public VendorInfoResponse createVendorInfo(CreateVendorInfoDto dto, String email) {
        Member member = getMemberByEmail(email);

        checkValidAccess(member);

        VendorInfo savedVendorInfo = vendorInfoRepository.save(dto.toEntity(member));

        return VendorInfoResponse.of(savedVendorInfo);
    }

    /**
     * 이메일로 회원 엔티티 조회
     *
     * @param email 현재 로그인한 사용자 이메일
     * @return 이메일에 해당하는 회원 엔티티
     */
    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
    }

    /**
     * 접근 유효성 판별
     *
     * @param member 회원 엔티티
     * @throws InvalidAccessException 해당 회원의 권한이 CLIENT 이거나 비활성화 상태인 경우
     */
    private void checkValidAccess(Member member) {
        if (isClient(member.getRole()) || !member.getActive()) {
            throw new InvalidAccessException("잘못된 접근입니다.");
        }
    }

    /**
     * 회원 권한이 CLIENT 인지 여부 판별
     * @param role 회원 권한
     * @return true : CLIENT 인 경우 false: 그 외 경우
     */
    private boolean isClient(Role role) {
        return role.equals(Role.CLIENT);
    }

    /**
     * 사업자 정보 삭제
     *
     * @param vendorInfoId 사업자 정보 식별키
     * @return true : 삭제 성공 / false : 삭제 실패
     */
    public Boolean deleteVendorInfo(Long vendorInfoId) {
        VendorInfo vendorInfo = getVendorInfoById(vendorInfoId);

        checkActive(vendorInfo);

        vendorInfo.deActivate();

        return true;
    }

    /**
     * 사업자 정보 식별키로 사업자 정보 조회
     * 
     * @param vendorInfoId 사업자 정보 식별키
     * @return 사업자 정보 엔티티
     * @throws NoSuchElementException 존재하지 않는 사업자 정보인 경우
     */
    private VendorInfo getVendorInfoById(Long vendorInfoId) {
        return vendorInfoRepository.findById(vendorInfoId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 사업자 정보입니다."));
    }

    /**
     * 삭제여부 확인
     * 
     * @param vendorInfo 사업자 정보 엔티티
     * @throws NoSuchElementException 삭제된 사업자 정보인 경우
     */
    private void checkActive(VendorInfo vendorInfo) {
        if (!vendorInfo.getActive()) {
            throw new NoSuchElementException("이미 삭제된 사업자 정보입니다.");
        }
    }
}
