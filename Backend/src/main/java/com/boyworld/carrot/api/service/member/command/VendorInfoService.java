package com.boyworld.carrot.api.service.member.command;

import com.boyworld.carrot.api.controller.member.response.VendorInfoResponse;
import com.boyworld.carrot.api.service.member.dto.CreateVendorInfoDto;
import com.boyworld.carrot.domain.member.repository.command.VendorInfoRepository;
import com.boyworld.carrot.domain.member.repository.query.VendorInfoQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 사업자 정보 등록
     *
     * @param dto   사업자 정보
     * @param email 현재 로그인 한 사용자 이메일
     * @return 등록된 사용자 정보
     */
    public VendorInfoResponse createVendorInfo(CreateVendorInfoDto dto, String email) {
        // TODO: 2023-10-31 로직 작성 필요. 사업자 검증 로직 필요
        return null;
    }
}
