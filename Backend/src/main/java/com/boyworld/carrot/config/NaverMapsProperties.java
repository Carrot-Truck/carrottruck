package com.boyworld.carrot.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class NaverMapsProperties {

    @Value("${NAVER_CLIENT_ID}")
    private String clientId;

    @Value("${NAVER_CLIENT_SECRET}")
    private String clientSecret;

    @Value("${NAVER_GEOCODE_URL}")
    private String geocodeUrl;

    @Value("${NAVER_REVERSE_GEOCODE_URL}")
    private String reverseGeocodeUrl;

}
