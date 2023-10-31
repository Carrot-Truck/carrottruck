package com.boyworld.carrot.api.service.member.query;

import com.boyworld.carrot.domain.member.repository.command.VendorInfoRepository;
import com.boyworld.carrot.domain.member.repository.query.VendorInfoQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final VendorInfoRepository vendorInfoRepository;
    private final VendorInfoQueryRepository vendorInfoQueryRepository;

}
