package com.boyworld.carrot.api.service.member.query;

import com.boyworld.carrot.api.controller.member.response.MemberAddressDetailResponse;
import com.boyworld.carrot.api.controller.member.response.MemberAddressResponse;
import com.boyworld.carrot.domain.member.repository.query.MemberAddressQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.boyworld.carrot.domain.SizeConstants.PAGE_SIZE;

/**
 * 회원 주소 조회 서비스
 *
 * @author 최영환
 */
@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class MemberAddressQueryService {

    private final MemberAddressQueryRepository memberAddressQueryRepository;

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
            return null;
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

    /**
     * 회원 주소 상세 조회
     *
     * @param memberAddressId 회원 주소 식별키
     * @return 회원 주소 상세 정보
     */
    public MemberAddressDetailResponse getMemberAddress(Long memberAddressId) {
        return memberAddressQueryRepository.getMemberAddressById(memberAddressId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원 주소입니다."));
    }
}
