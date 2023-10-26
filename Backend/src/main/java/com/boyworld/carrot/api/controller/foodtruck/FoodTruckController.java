package com.boyworld.carrot.api.controller.foodtruck;

import com.boyworld.carrot.api.ApiResponse;
import com.boyworld.carrot.api.controller.foodtruck.request.CreateFoodTruckRequest;
import com.boyworld.carrot.api.controller.foodtruck.response.FoodTruckDetailResponse;
import com.boyworld.carrot.api.controller.foodtruck.response.FoodTruckMarkerResponse;
import com.boyworld.carrot.api.controller.foodtruck.response.FoodTruckResponse;
import com.boyworld.carrot.api.service.foodtruck.FoodTruckQueryService;
import com.boyworld.carrot.api.service.foodtruck.FoodTruckService;
import com.boyworld.carrot.domain.foodtruck.repository.dto.SearchCondition;
import com.boyworld.carrot.security.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 푸드트럭 관련 API 컨트롤러
 *
 * @author 최영환
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/food-truck")
public class FoodTruckController {

    private final FoodTruckService foodTruckService;
    private final FoodTruckQueryService foodTruckQueryService;

    /**
     * 푸드트럭 등록 API
     *
     * @param request 푸드트럭 정보
     * @param file    푸드트럭 이미지
     * @return 등록된 푸드트럭 식별키
     */
    @PostMapping("/vendor")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Long> createFoodTruck(@Valid @RequestPart(name = "request") CreateFoodTruckRequest request,
                                             @RequestPart(required = false, name = "file") MultipartFile file) {
        log.debug("FoodTruckController#createFoodTruck called");
        log.debug("CreateFoodTruckRequest={}", request);
        log.debug("MultipartFile={}", file);

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        Long saveId = foodTruckService.createFoodTruck(request.toCreateFoodTruckDto(), email, file);
        log.debug("saveId={}", saveId);

        return ApiResponse.created(saveId);
    }

    /**
     * 푸드트럭 지도 검색 API
     *
     * @param categoryId 카테고리 식별키
     * @param keyword    검색어(푸드트럭 이름 / 메뉴 이름)
     * @param latitude   위도
     * @param longitude  경도
     * @return 푸드트럭 지도에 표시될 마커 정보
     */
    @GetMapping("/marker")
    public ApiResponse<FoodTruckMarkerResponse> getFoodTruckMarkers(@RequestParam(required = false, defaultValue = "") String categoryId,
                                                                    @RequestParam(required = false, defaultValue = "") String keyword,
                                                                    @RequestParam(required = false, defaultValue = "") String longitude,
                                                                    @RequestParam(required = false, defaultValue = "") String latitude) {
        log.debug("FoodTruckController#getFoodTruckMarkers called");
        log.debug("categoryId={}", categoryId);
        log.debug("keyword={}", keyword);
        log.debug("latitude={}", latitude);
        log.debug("longitude={}", longitude);

        FoodTruckMarkerResponse response = foodTruckQueryService.getFoodTruckMarkers(SearchCondition.of(categoryId, keyword, longitude, latitude));
        log.debug("FoodTruckResponse={}", response);

        return ApiResponse.ok(response);
    }

    /**
     * 푸드트럭 목록 조회 API
     *
     * @param categoryId      카테고리 식별키
     * @param keyword         검색어(푸드트럭 이름 / 메뉴 이름)
     * @param latitude        위도
     * @param longitude       경도
     * @param lastFoodTruckId 마지막으로 조회된 푸드트럭 식별키
     * @return 식별키 리스트에 해당하는 푸드트럭 리스트 (거리순 정렬)
     */
    @GetMapping
    public ApiResponse<FoodTruckResponse> getFoodTrucks(@RequestParam(required = false, defaultValue = "") String categoryId,
                                                        @RequestParam(required = false, defaultValue = "") String keyword,
                                                        @RequestParam(required = false, defaultValue = "") String longitude,
                                                        @RequestParam(required = false, defaultValue = "") String latitude,
                                                        @RequestParam(required = false, defaultValue = "") String lastFoodTruckId) {
        log.debug("FoodTruckController#getFoodTrucks called");
        log.debug("categoryId={}", categoryId);
        log.debug("keyword={}", keyword);
        log.debug("latitude={}", latitude);
        log.debug("longitude={}", longitude);
        log.debug("lastFoodTruckId={}", lastFoodTruckId);

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        FoodTruckResponse response = foodTruckQueryService.getFoodTrucks(SearchCondition.of(categoryId, keyword, longitude, latitude), lastFoodTruckId, email);
        log.debug("FoodTruckResponse={}", response);

        return ApiResponse.ok(response);
    }


    /**
     * 푸드트럭 상세조회 API
     *
     * @param truckId 푸드트럭 식별키
     * @return 푸드트럭 상세 정보
     */
    @GetMapping("/{truckId}")
    public ApiResponse<FoodTruckDetailResponse> getFoodTruck(@PathVariable Long truckId) {
        log.debug("FoodTruckController#getFoodTruck called");
        log.debug("truckId={}", truckId);

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        FoodTruckDetailResponse response = foodTruckQueryService.getFoodTruck(truckId, email);
        log.debug("FoodTruckDetailResponse={}", response);

        return ApiResponse.ok(response);
    }
}
