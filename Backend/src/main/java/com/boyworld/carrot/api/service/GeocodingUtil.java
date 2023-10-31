package com.boyworld.carrot.api.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;

@Slf4j
@AllArgsConstructor
public class GeocodingUtil {

    private static RestTemplate restTemplate;

    @Value("${NAVER_CLIENT_ID}")
    private static String clientId;

    @Value("${NAVER_CLIENT_SECRET}")
    private static String clientSecret;

    @Value("${NAVER_GEOCODE_URL}")
    private static String geocodeUrl;

    @Value("${NAVER_REVERSE_GEOCODE_URL}")
    private static String reverseGeocodeUrl;

    /**
     * 좌표 -> 주소 변환
     * 
     * @param latitude 위도
     * @param longitude 경도
     * @return 도로명주소
     */
    public static String geocoding(BigDecimal latitude, BigDecimal longitude) {
        log.debug("GeocodingUtil#geociding called !!!");
        log.debug("latitude={}, longitude={}", latitude, longitude);
        StringBuilder coords = new StringBuilder(latitude.toString()).append(",").append(longitude.toString());

        URI uri = UriComponentsBuilder.fromUriString(reverseGeocodeUrl)
                .queryParam("coords", coords)
                .queryParam("orders", "roadaddr")
                .queryParam("output", "json")
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", clientId);
        headers.set("X-NCP-APIGW-API-KEY", clientSecret);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        Object response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

        log.debug("Geocoding Result={}, ", response.toString());

        return null;
    }
}