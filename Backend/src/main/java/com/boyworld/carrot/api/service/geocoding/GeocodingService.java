package com.boyworld.carrot.api.service.geocoding;

import com.boyworld.carrot.api.service.geocoding.dto.gc.Address;
import com.boyworld.carrot.config.NaverMapsProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class GeocodingService {

    private final NaverMapsProperties naverMapsProperties;

    /**
     * 좌표 -> 주소 변환
     *
     * @param latitude 위도
     * @param longitude 경도
     * @param orders 변환 작업 이름
     *               legalcode: 좌표 to 법정동
     *               admcode: 좌표 to 행정동
     *               addr: 좌표 to 지번주소
     *               roadaddr: 좌표 to 도로명 주소
     *               여러개의 값 입력시 콤마로 구분
     * @return 도로명주소
     */
    public Map<String, String> reverseGeocoding(BigDecimal latitude, BigDecimal longitude, String orders) {
        log.debug("GeocodingUtil#geociding called !!!");
        log.debug("latitude={}, longitude={}, orders={}", latitude, longitude, orders);

        Map<String, String> addressRes = new HashMap<>();

        try {

            HttpHeaders headers = new HttpHeaders();

            headers.set("X-NCP-APIGW-API-KEY-ID", naverMapsProperties.getClientId());
            headers.set("X-NCP-APIGW-API-KEY", naverMapsProperties.getClientSecret());

            HttpEntity<?> entity = new HttpEntity<>(headers);

            String coords = longitude.toString() + "," + latitude.toString();

            URI uri = UriComponentsBuilder.fromUriString(naverMapsProperties.getReverseGeocodeUrl())
                    .queryParam("coords", coords)
                    .queryParam("orders", orders)
                    .queryParam("output", "json")
                    .build()
                    .toUri();

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> response = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity,Map.class);
            ArrayList<Object> results = (ArrayList<Object>) response.getBody().get("results");

            log.debug("ReverseGeocoding Results={}, ", results);
            ObjectMapper mapper = new ObjectMapper();
            for (Object res : results) {
                String jsonString = mapper.writeValueAsString(res);
                Address address = mapper.readValue(jsonString, Address.class);

                String order = address.getName();

                StringBuilder addr = new StringBuilder();
                addr.append(address.getRegion().getArea1().getName()).append(" ")
                        .append(address.getRegion().getArea2().getName()).append(" ");
                if (order.equals("legalcode") || order.equals("admcode")) {
                    addr.append(address.getRegion().getArea3().getName()).append(" ")
                            .append(address.getRegion().getArea4().getName());
                } else if (order.equals("addr")) {
                    addr.append(address.getRegion().getArea3().getName()).append(" ")
                            .append(address.getLand().getNumber1()).append(" ")
                            .append(address.getLand().getNumber2());
                } else if (order.equals("roadaddr")) {
                    addr.append(address.getLand().getName()).append(" ")
                            .append(address.getLand().getNumber1());
                }

                addressRes.put(order, addr.toString().trim());
            }
        } catch (NullPointerException e) {
            log.error("NullPointerException={}", e.toString());
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("HttpErrorException={}", e.toString());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return addressRes;
    }
}