package com.boyworld.carrot.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AnalysisProperties {

    @Value("${DATA_GO_SERVICE_KEY}")
    private String serviceKey;

    @Value("${STORE_DONG_URL}")
    private String storesInDongUrl;

    @Value("${STORE_RADIUS_URL}")
    private String storesInRadiusUrl;

    @Value("${DONG_CODE_URL}")
    private String dongCodeUrl;

}
