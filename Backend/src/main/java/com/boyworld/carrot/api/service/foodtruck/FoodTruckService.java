package com.boyworld.carrot.api.service.foodtruck;

import com.boyworld.carrot.api.service.foodtruck.dto.CreateFoodTruckDto;
import com.boyworld.carrot.api.service.foodtruck.dto.UpdateFoodTruckDto;
import com.boyworld.carrot.domain.foodtruck.repository.FoodTruckRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 푸드트럭 서비스
 *
 * @author 최영환
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FoodTruckService {

    private final FoodTruckRepository foodTruckRepository;

    /**
     * 푸드트럭 등록
     *
     * @param dto   푸드트럭 정보
     * @param email 로그인 중인 회원 이메일
     * @param file  등록할 푸드트럭 이미지
     * @return 등록된 푸드트럭 식별키
     */
    public Long createFoodTruck(CreateFoodTruckDto dto, String email, MultipartFile file) {
        return null;
    }

    /**
     * 푸드트럭 수정
     *
     * @param dto   수정할 푸드트럭 정보
     * @param file  수정할 푸드트럭 이미지
     * @param email 로그인 중인 회원 이메일
     * @return 수정된 푸드트럭 식별키
     */
    public Long editFoodTruck(UpdateFoodTruckDto dto, MultipartFile file, String email) {
        return null;
    }
}
