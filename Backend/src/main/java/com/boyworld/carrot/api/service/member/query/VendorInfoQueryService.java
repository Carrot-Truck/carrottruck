package com.boyworld.carrot.api.service.member.query;

import com.boyworld.carrot.api.controller.member.response.VendorInfoResponse;
import com.boyworld.carrot.domain.member.repository.query.VendorInfoQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

/**
 * 사업자 정보 조회 서비스
 *
 * @author 최영환
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VendorInfoQueryService {

    private final VendorInfoQueryRepository vendorInfoQueryRepository;

    /**
     * 사업자 정보 조회 API
     *
     * @param email 현재 로그인한 사용자의 이메일
     * @return 현재 로그인한 사용자의 사업자 정보
     */
    public VendorInfoResponse getVendorInfo(String email) {
        return vendorInfoQueryRepository.getVendorInfoByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 사업자 정보입니다."));
    }
}
