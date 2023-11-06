package com.boyworld.carrot.api.service.analysis;

import com.boyworld.carrot.api.controller.analysis.response.StoreAnalysisResponse;
import com.boyworld.carrot.api.service.analysis.dto.Store;
import com.boyworld.carrot.api.service.analysis.dto.StoreAnalysisDto;
import com.boyworld.carrot.api.service.geocoding.GeocodingService;
import com.boyworld.carrot.config.AnalysisProperties;
import com.boyworld.carrot.domain.adong.repository.AdongCodeQueryRepository;
import com.boyworld.carrot.domain.foodtruck.repository.command.CategoryRepository;
import com.boyworld.carrot.domain.foodtruck.repository.query.CategoryCodeQueryRepository;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 상권분석 서비스
 *
 * @author 양진형
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnalysisQueryService {

    private final GeocodingService geocodingService;

    private final AdongCodeQueryRepository adongCodeQueryRepository;

    private final AnalysisProperties analysisProperties;

    private final CategoryRepository categoryRepository;

    private final CategoryCodeQueryRepository categoryCodeQueryRepository;

    /**
     * 상권분석 서비스
     * 
     * @param categoryId 카테고리 ID
     * @param latitude 위도
     * @param longitude 경도
     *
     * @return 상권분석 정보
     */
    public StoreAnalysisResponse getStoreAnalysis(Long categoryId, BigDecimal latitude, BigDecimal longitude) {
        log.debug("AnalysisQueryService#getStoreAnalysis called !!!");
        log.debug("latitude={}, longitude={}, categoryId={}", latitude, longitude, categoryId);

        String[] address = geocodingService.reverseGeocoding(latitude, longitude, "admcode")
                .get("admcode").split(" ");

        String adongCode = adongCodeQueryRepository.getAdongCode(address[0], address[1], address[2]);

        String categoryName = categoryRepository.findById(categoryId).get().getName();
        List<String> indsCodes = categoryCodeQueryRepository.getCodeByCategoryId(categoryId);

        List<StoreAnalysisDto> stores = new ArrayList<>();
        int radiusCount = 0;
        int addressCount = 0;

        try {
            for (String indsCode : indsCodes) {
                radiusCount += getCountAndListInRadius(indsCode, latitude.toString(), longitude.toString(), stores);
                addressCount += getCountInAddress(adongCode, indsCode);
            }
        } catch (JacksonException e) {
            log.error(e.toString());
        }

        return StoreAnalysisResponse.builder()
                .categoryId(categoryId)
                .categoryName(categoryName)
                .sido(address[0])
                .sigungu(address[1])
                .dong(address[2])
                .radiusCount(radiusCount)
                .addressCount(addressCount)
                .stores(stores)
                .build();
    }

    private int getCountAndListInRadius(String indsCode, String latitude, String longitude,
                                        List<StoreAnalysisDto> stores) throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(analysisProperties.getStoresInRadiusUrl())
                .queryParam("ServiceKey", analysisProperties.getServiceKey())
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 1000)
                .queryParam("radius", 1000)
                .queryParam("cx", longitude)
                .queryParam("cy", latitude)
                .queryParam("type", "json");

        if (indsCode.length() == 4) {
            uriBuilder.queryParam("indsMclsCd", indsCode);
        } else if (indsCode.length() == 6) {
            uriBuilder.queryParam("indsSclsCd", indsCode);
        }

        String uriString = uriBuilder.build().toUriString();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.exchange(
                URI.create(uriString),
                HttpMethod.GET,
                entity,
                Map.class
        );

        Map responseBody = (Map) response.getBody().get("body");
        ArrayList<Object> results = (ArrayList<Object>) responseBody.get("items");

        ObjectMapper mapper = new ObjectMapper();
        for (Object res : results) {
            String jsonString = mapper.writeValueAsString(res);
            Store store = mapper.readValue(jsonString, Store.class);
            StoreAnalysisDto storeDto = StoreAnalysisDto.of(store);
            stores.add(storeDto);
        }

        return (int) responseBody.get("totalCount");
    }

    private int getCountInAddress(String adongCode, String indsCode) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(analysisProperties.getStoresInDongUrl())
                .queryParam("ServiceKey", analysisProperties.getServiceKey())
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 1)
                .queryParam("divId", "adongCd")
                .queryParam("key", adongCode)
                .queryParam("type", "json");

        if (indsCode.length() == 4) {
            uriBuilder.queryParam("indsMclsCd", indsCode);
        } else if (indsCode.length() == 6) {
            uriBuilder.queryParam("indsSclsCd", indsCode);
        }

        String uriString = uriBuilder.build().toUriString();

        log.debug("uri={}", uriString);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.exchange(
                URI.create(uriString),
                HttpMethod.GET,
                entity,
                Map.class
        );

        Map responseBody = (Map) response.getBody().get("body");

        log.debug(responseBody.toString());

        return (int) responseBody.get("totalCount");
    }
}
