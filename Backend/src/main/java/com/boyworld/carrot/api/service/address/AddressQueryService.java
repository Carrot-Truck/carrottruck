package com.boyworld.carrot.api.service.address;

import com.boyworld.carrot.api.controller.address.response.AddressResponse;
import com.boyworld.carrot.api.service.address.dto.AddressInfoDto;
import com.boyworld.carrot.domain.address.repository.AddressQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 주소 조회 서비스
 *
 * @author 양진형
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AddressQueryService {

    private final AddressQueryRepository addressQueryRepository;

    /**
     * 시도 조회 서비스
     * 
     * @return 시도 ID, 시도 이름
     */
    public AddressResponse getSido() {
        List<AddressInfoDto> data = addressQueryRepository.getSido();
        return AddressResponse.of(data);
    }

    /**
     * 시군구 조회 서비스
     * 
     * @param sidoId 조회할 시도 ID
     * @return 시군구 ID, 시군구 이름
     */
    public AddressResponse getSigungu(Long sidoId) {
        List<AddressInfoDto> data = addressQueryRepository.getSigungu(sidoId);
        return AddressResponse.of(data);
    }

    /**
     * 읍면동 조회 서비스
     * 
     * @param dongId 조회할 읍면동 ID
     * @return 읍면동 ID, 읍면동 이름
     */
    public AddressResponse getDong(Long dongId) {
        List<AddressInfoDto> data = addressQueryRepository.getDong(dongId);
        return AddressResponse.of(data);
    }
}
