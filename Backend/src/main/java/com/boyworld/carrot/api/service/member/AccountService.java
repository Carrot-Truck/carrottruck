package com.boyworld.carrot.api.service.member;

import com.boyworld.carrot.api.controller.member.response.ClientResponse;
import com.boyworld.carrot.api.controller.member.response.MemberAddressDetailResponse;
import com.boyworld.carrot.api.controller.member.response.MemberAddressResponse;
import com.boyworld.carrot.api.controller.member.response.VendorResponse;
import com.boyworld.carrot.domain.member.repository.MemberAddressQueryRepository;
import com.boyworld.carrot.domain.member.repository.MemberQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static com.boyworld.carrot.domain.SizeConstants.PAGE_SIZE;

/**
 * 계정 관련 서비스
 *
 * @author 최영환
 */
@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class AccountService {

    private final MemberQueryRepository memberQueryRepository;

    private final MemberAddressQueryRepository memberAddressQueryRepository;

    /**
     * 로그인 중인 일반 사용자 회원 정보 조회
     *
     * @param email 로그인 중인 회원 이메일
     * @return 로그인 중인 회원 정보
     * @throws NoSuchElementException 해당 이메일 회원 정보가 존재하지 않는 경우
     */
    public ClientResponse getClientInfo(String email) {
        ClientResponse response = memberQueryRepository.getClientInfoByEmail(email);
        log.debug("ClientResponse={}", response);

        if (response == null) {
            throw new NoSuchElementException("존재하지 않는 회원입니다.");
        }

        return response;
    }

    /**
     * 로그인 중인 사업자 회원 정보 조회
     *
     * @param email 로그인 중인 회원 이메일
     * @return 로그인 중인 회원 정보
     * @throws NoSuchElementException 존재하지 않는 사업자인 경우
     */
    public VendorResponse getVendorInfo(String email) {
        VendorResponse response = memberQueryRepository.getVendorInfoByEmail(email);
        log.debug("VendorResponse={}", response);

        if (response == null) {
            throw new NoSuchElementException("존재하지 않는 회원입니다.");
        }

        return response;
    }

    /**
     * 로그인 중인 사용자 주소 전체 조회
     *
     * @param email               로그인 중인 회원 이메일
     * @param lastMemberAddressId 마지막으로 조회된 주소 식별키
     * @return 로그인 중인 사용자의 주소 목록
     */
    public MemberAddressResponse getMemberAddresses(String email, String lastMemberAddressId) {
        List<MemberAddressDetailResponse> memberAddresses = memberAddressQueryRepository
                .getMemberAddressesByEmail(email, getLastMemberAddressId(lastMemberAddressId));

        boolean hasNext = checkHasNext(memberAddresses);

        return MemberAddressResponse.of(memberAddresses, hasNext);
    }

    /**
     * 마지막으로 조회된 회원 주소 식별키 변환
     *
     * @param lastMemberAddressId 마지막으로 조회된 주소 식별키
     * @return Long 타입으로 변환된 식별키 값
     */
    private Long getLastMemberAddressId(String lastMemberAddressId) {
        if (lastMemberAddressId.isBlank()) {
            return 0L;
        }
        return Long.parseLong(lastMemberAddressId);
    }

    /**
     * 다음 페이지 존재여부 확인
     *
     * @param memberAddresses 회원주소 리스트
     * @return true: 다음 페이지가 있는 경우 / false: 다음 페이지가 없는 경우
     */
    private boolean checkHasNext(List<MemberAddressDetailResponse> memberAddresses) {
        if (memberAddresses.size() > PAGE_SIZE) {
            memberAddresses.remove(PAGE_SIZE);
            return true;
        }
        return false;
    }
}
