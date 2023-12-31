package com.boyworld.carrot.api.controller.address;

import com.boyworld.carrot.api.ApiResponse;
import com.boyworld.carrot.api.controller.address.response.AddressResponse;
import com.boyworld.carrot.api.controller.address.response.ReverseGeocodingResponse;
import com.boyworld.carrot.api.service.address.AddressQueryService;
import com.boyworld.carrot.api.service.geocoding.GeocodingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * 주소 조회 컨트롤러
 * 
 * @author 양진형
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/address")
public class AddressController {
    
    private final AddressQueryService addressQueryService;

    private final GeocodingService geocodingService;

    /**
     * 시도 조회 API
     * 
     * @return 시도 ID, 이름 리스트
     */
    @GetMapping("/sido")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<AddressResponse> getSido() {
        log.debug("AddressController#getSido called !!!");

        AddressResponse response = addressQueryService.getSido();
        log.debug("AddressResponse={}", response);

        return ApiResponse.ok(response);
    }

    /**
     * 시군구 조회 API
     * 
     * @param sidoId 조회할 시도 ID
     * @return 시군구 ID, 이름 리스트
     */
    @GetMapping("/sigungu/{sidoId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<AddressResponse> getSigungu(@PathVariable Long sidoId) {
        log.debug("AddressController#getSigungu called !!!");

        AddressResponse response = addressQueryService.getSigungu(sidoId);
        log.debug("AddressResponse={}", response);

        return ApiResponse.ok(response);
    }

    /**
     * 읍면동 조회 API
     * 
     * @param sigunguId 조회할 시군구 ID
     * @return 읍면동 ID, 이름 리스트
     */
    @GetMapping("/dong/{sigunguId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<AddressResponse> getDong(@PathVariable Long sigunguId) {
        log.debug("AddressController#getDong called !!!");

        AddressResponse response = addressQueryService.getDong(sigunguId);
        log.debug("AddressResponse={}", response);

        return ApiResponse.ok(response);
    }

    /**
     * reverse geocoding api
     * 
     * @param latitude 위도
     * @param longitude 경도
     * @return 도로명 주소
     */
    @GetMapping("/rgc")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<ReverseGeocodingResponse> reverseGeocoding(@RequestParam BigDecimal latitude,
                                                                  @RequestParam BigDecimal longitude) {
        log.debug("AddressController#reverseGeocoding called !!!");

        String addr = geocodingService.reverseGeocoding(latitude, longitude, "roadaddr").get("roadaddr");
        ReverseGeocodingResponse response = ReverseGeocodingResponse.builder().address(addr).build();
        log.debug("ReverseGeocodingResponse={}", response);

        return ApiResponse.ok(response);
    }
}
