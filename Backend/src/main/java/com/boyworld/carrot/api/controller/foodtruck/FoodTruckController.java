package com.boyworld.carrot.api.controller.foodtruck;

import com.boyworld.carrot.api.ApiResponse;
import com.boyworld.carrot.api.controller.foodtruck.request.CreateFoodTruckRequest;
import com.boyworld.carrot.api.controller.foodtruck.response.FoodTruckResponse;
import com.boyworld.carrot.api.service.foodtruck.FoodTruckQueryService;
import com.boyworld.carrot.api.service.foodtruck.FoodTruckService;
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
     * 푸드트럭 검색 결과 목록 조회 API (사용자)
     *
     * @param categoryId      선택한 카테고리 식별키
     * @param keyword         검색한 푸드트럭/메뉴 이름
     * @param lastFoodTruckId 마지막으로 조회된 푸드트럭 식별키
     * @return 푸드트럭 검색 결과 목록
     */
    @GetMapping("/client")
    public ApiResponse<FoodTruckResponse> getFoodTrucks(@RequestParam(required = false, defaultValue = "") String categoryId,
                                                        @RequestParam(required = false, defaultValue = "") String keyword,
                                                        @RequestParam(required = false, defaultValue = "") String lastFoodTruckId) {
        log.debug("FoodTruckController#getFoodTrucks called");
        log.debug("categoryId={}", categoryId);
        log.debug("keyword={}", keyword);
        log.debug("lastFoodTruckId={}", lastFoodTruckId);

        FoodTruckResponse response = foodTruckQueryService.getFoodTrucks(categoryId, keyword, lastFoodTruckId);
        log.debug("FoodTruckResponse={}", response);

        return ApiResponse.ok(response);
    }
}
