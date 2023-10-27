package com.boyworld.carrot.api.service.foodtruck;

import com.boyworld.carrot.api.controller.foodtruck.response.FoodTruckDetailResponse;
import com.boyworld.carrot.api.controller.foodtruck.response.FoodTruckMarkerResponse;
import com.boyworld.carrot.api.controller.foodtruck.response.FoodTruckResponse;
import com.boyworld.carrot.domain.foodtruck.repository.FoodTruckQueryRepository;
import com.boyworld.carrot.domain.foodtruck.repository.dto.SearchCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 푸드트럭 조회 서비스
 *
 * @author 최영환
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodTruckQueryService {

    private final FoodTruckQueryRepository queryRepository;

    /**
     * 푸드트럭 지도 검색 API
     *
     * @param condition 검색 조건
     * @param email 현재 로그인 중인 사용자 이메일
     * @return 푸드트럭 지도에 표시될 마커 정보
     */
    public FoodTruckMarkerResponse getFoodTruckMarkers(SearchCondition condition, String email) {
        return null;
    }

    /**
     * 푸드트럭 목록 조회 API
     *
     * @param condition 검색 조건
     * @param email 현재 로그인 중인 사용자 이메일
     * @return 식별키 리스트에 해당하는 푸드트럭 리스트 (거리순 정렬)
     */
    public FoodTruckResponse getFoodTrucks(SearchCondition condition, String lastFoodTruckId, String email) {
        return null;
    }

    /**
     * 푸드트럭 상세 조회 API
     * 
     * @param truckId 푸드트럭 식별키
     * @param email 현재 로그인 중인 사용자 이메일
     * @return 푸드트럭 식별키에 해당하는 푸드트럭 상세 정보 (메뉴, 리뷰 포함)
     */
    public FoodTruckDetailResponse getFoodTruck(Long truckId, String email) {
        return null;
    }
}
