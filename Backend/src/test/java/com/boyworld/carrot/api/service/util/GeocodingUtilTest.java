package com.boyworld.carrot.api.service.util;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.api.service.geocoding.GeocodingService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Map;

@Slf4j
public class GeocodingUtilTest extends IntegrationTestSupport {

    @Autowired
    private GeocodingService geocodingService;

    @DisplayName("좌표 -> 주소 변환 유틸")
    @Test
    void geocoding() {
        BigDecimal latitude = new BigDecimal("35.19508792");
        BigDecimal longitude = new BigDecimal("126.8145971");

        Map<String, String> res = geocodingService.reverseGeocoding(latitude, longitude, "roadaddr,admcode,addr,legalcode");

        for (String key : res.keySet()) {
            log.debug("key={}, value={}", key, res.get(key));
        }
    }

}
