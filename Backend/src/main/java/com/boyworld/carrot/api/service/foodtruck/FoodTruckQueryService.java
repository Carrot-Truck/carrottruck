package com.boyworld.carrot.api.service.foodtruck;

import com.boyworld.carrot.api.controller.foodtruck.response.FoodTruckResponse;
import com.boyworld.carrot.domain.foodtruck.repository.FoodTruckQueryRepository;
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
     * 푸드트럭 검색 결과 목록 조회 (사용자)
     *
     * @param categoryId      선택한 카테고리 식별키
     * @param keyword         검색한 푸드트럭/메뉴 이름
     * @param lastFoodTruckId 마지막으로 조회된 푸드트럭 식별키
     * @return 푸드트럭 검색 결과 목록
     */
    public FoodTruckResponse getFoodTrucks(String categoryId, String keyword, String lastFoodTruckId) {
        return null;
    }
}
