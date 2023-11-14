package com.boyworld.carrot.api.controller.foodtruck;

import com.boyworld.carrot.api.ApiResponse;
import com.boyworld.carrot.api.controller.foodtruck.request.CreateFoodTruckRequest;
import com.boyworld.carrot.api.controller.foodtruck.request.FoodTruckLikeRequest;
import com.boyworld.carrot.api.controller.foodtruck.request.UpdateFoodTruckRequest;
import com.boyworld.carrot.api.controller.foodtruck.response.*;
import com.boyworld.carrot.api.service.foodtruck.FoodTruckQueryService;
import com.boyworld.carrot.api.service.foodtruck.FoodTruckService;
import com.boyworld.carrot.api.service.foodtruck.dto.FoodTruckClientDetailDto;
import com.boyworld.carrot.api.service.foodtruck.dto.FoodTruckVendorDetailDto;
import com.boyworld.carrot.domain.foodtruck.repository.dto.SearchCondition;
import com.boyworld.carrot.security.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

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
    public ApiResponse<Long> createFoodTruck(@Valid @RequestPart CreateFoodTruckRequest request,
                                             @RequestPart(required = false) MultipartFile file) throws IOException {
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
     * 근처 푸드트럭 위치 정보 검색 API
     *
     * @param categoryId 카테고리 식별키
     * @param keyword    검색어(푸드트럭 이름 / 메뉴 이름)
     * @param latitude   위도
     * @param longitude  경도
     * @param showAll    전체보기 / 영업중 보기 여부
     * @return 푸드트럭 지도에 표시될 마커 정보
     */
    @GetMapping("/marker")
    public ApiResponse<FoodTruckMarkerResponse> getFoodTruckMarkers(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam BigDecimal longitude,
            @RequestParam BigDecimal latitude,
            @RequestParam(defaultValue = "true") Boolean showAll) {

        log.debug("FoodTruckController#getFoodTruckMarkers called");
        log.debug("categoryId={}", categoryId);
        log.debug("keyword={}", keyword);
        log.debug("latitude={}", latitude);
        log.debug("longitude={}", longitude);
        log.debug("showAll={}", showAll);

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        FoodTruckMarkerResponse response =
                foodTruckQueryService
                        .getFoodTruckMarkers(SearchCondition.of(categoryId, keyword, latitude, longitude), showAll);
        log.debug("FoodTruckResponse={}", response);

        return ApiResponse.ok(response);
    }

    /**
     * 근처 푸드트럭 목록 조회 API
     *
     * @param categoryId      카테고리 식별키
     * @param keyword         검색어(푸드트럭 이름 / 메뉴 이름)
     * @param orderCondition  정렬 기준 (가까운순, 평점 높은 순, 찜 많은 순, 리뷰 많은 순)
     * @param latitude        위도
     * @param longitude       경도
     * @param lastFoodTruckId 마지막으로 조회된 푸드트럭 식별키
     * @param showAll         전체보기 / 영업중 보기 여부
     * @return 현재 위치 기반 반경 1Km 이내의 푸드트럭 목록
     */
    @GetMapping
    public ApiResponse<FoodTruckResponse<List<FoodTruckItem>>> getSearchedFoodTrucks(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false, defaultValue = "") String orderCondition,
            @RequestParam BigDecimal longitude,
            @RequestParam BigDecimal latitude,
            @RequestParam(required = false) Long lastFoodTruckId,
            @RequestParam(defaultValue = "false") Boolean showAll) {

        log.debug("FoodTruckController#getFoodTrucks called");
        log.debug("categoryId={}", categoryId);
        log.debug("keyword={}", keyword);
        log.debug("latitude={}", latitude);
        log.debug("longitude={}", longitude);
        log.debug("lastFoodTruckId={}", lastFoodTruckId);
        log.debug("orderCondition={}", orderCondition);
        log.debug("showAll={}", showAll);

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        FoodTruckResponse<List<FoodTruckItem>> response = foodTruckQueryService
                .getFoodTrucks(SearchCondition.of(categoryId, keyword, latitude, longitude, orderCondition),
                        email, lastFoodTruckId, showAll);
        log.debug("FoodTruckResponse={}", response);

        return ApiResponse.ok(response);
    }

    /**
     * 보유 푸드트럭 목록 조회 API
     *
     * @return 보유한 푸드트럭 목록
     */
    @GetMapping("/overview")
    public ApiResponse<FoodTruckResponse<List<FoodTruckOverview>>> getFoodTruckOverviews(
            @RequestParam(required = false) Long lastFoodTruckId) {
        log.debug("lastFoodTruckId={}", lastFoodTruckId);

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        FoodTruckResponse<List<FoodTruckOverview>> response = foodTruckQueryService
                .getFoodTruckOverviews(lastFoodTruckId, email);
        log.debug("response={}", response);

        return ApiResponse.ok(response);
    }

    /**
     * 푸드트럭 상세조회 API (일반 사용자용)
     *
     * @param foodTruckId 푸드트럭 식별키
     * @param latitude    위도
     * @param longitude   경도
     * @return 푸드트럭 상세 정보
     */
    @GetMapping("/client/{foodTruckId}")
    public ApiResponse<FoodTruckDetailResponse<FoodTruckClientDetailDto>> getFoodTruck(@PathVariable Long foodTruckId,
                                                                                       @RequestParam BigDecimal latitude,
                                                                                       @RequestParam BigDecimal longitude) {
        log.debug("FoodTruckController#getFoodTruck called");
        log.debug("truckId={}", foodTruckId);

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        FoodTruckDetailResponse<FoodTruckClientDetailDto> response =
                foodTruckQueryService.getClientFoodTruck(foodTruckId, email, latitude, longitude);
        log.debug("FoodTruckDetailResponse={}", response);

        return ApiResponse.ok(response);
    }

    /**
     * 푸드트럭 상세조회 API (사업자용)
     *
     * @param foodTruckId 푸드트럭 식별키
     * @return 푸드트럭 상세 정보
     */
    @GetMapping("/vendor/{foodTruckId}")
    public ApiResponse<FoodTruckDetailResponse<FoodTruckVendorDetailDto>> getVendorFoodTruckById(
            @PathVariable Long foodTruckId) {

        log.debug("FoodTruckController#getFoodTruck called");
        log.debug("truckId={}", foodTruckId);

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        FoodTruckDetailResponse<FoodTruckVendorDetailDto> response =
                foodTruckQueryService.getVendorFoodTruckById(foodTruckId, email);
        log.debug("FoodTruckDetailResponse={}", response);

        return ApiResponse.ok(response);
    }

    /**
     * 푸드트럭 수정 API
     *
     * @param foodTruckId 푸드트럭 식별키
     * @param request     수정할 푸드트럭 정보
     * @return 수정된 푸드트럭 식별키
     */
    @PatchMapping("/{foodTruckId}")
    public ApiResponse<Long> editFoodTruck(@PathVariable Long foodTruckId,
                                           @Valid @RequestPart UpdateFoodTruckRequest request,
                                           @RequestPart(required = false) MultipartFile file) throws IOException {
        log.debug("FoodTruckController#editFoodTruck called");
        log.debug("foodTruckId={}", foodTruckId);
        log.debug("UpdateFoodTruckRequest={}", request);
        log.debug("MultipartFile={}", file);

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        Long updateId = foodTruckService.editFoodTruck(request.toUpdateFoodTruckDto(foodTruckId), file, email);
        log.debug("updateId={}", updateId);

        return ApiResponse.ok(updateId);
    }

    /**
     * 푸드트럭 삭제 API
     *
     * @param foodTruckId 푸드트럭 식별키
     * @return 삭제된 푸드트럭 식별키
     */
    @DeleteMapping("/{foodTruckId}")
    @ResponseStatus(HttpStatus.FOUND)
    public ApiResponse<Long> deleteFoodTruck(@PathVariable Long foodTruckId) {
        log.debug("FoodTruckController#deleteFoodTruck called");
        log.debug("foodTruckId={}", foodTruckId);

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        Long deleteId = foodTruckService.deleteFoodTruck(foodTruckId, email);
        log.debug("deleteId={}", deleteId);

        return ApiResponse.found(deleteId);
    }

    /**
     * 푸드트럭 찜 API
     *
     * @param request 푸드트럭 식별키
     * @return 푸드트럭 찜 정보
     */
    @PostMapping("/like")
    public ApiResponse<FoodTruckLikeResponse> foodTruckLike(@Valid @RequestBody FoodTruckLikeRequest request) {
        log.debug("FoodTruckController#foodTruckLike called");
        log.debug("FoodTruckLikeRequest={}", request);

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        FoodTruckLikeResponse response = foodTruckService.foodTruckLike(request.toFoodTruckLikeDto(), email);
        log.debug("FoodTruckLikeResponse={}", response);

        return ApiResponse.ok(response);
    }

    /**
     * 사업자별 선택된 푸드트럭 변경
     *
     * @param foodTruckId 푸드트럭 식별키
     * @return 선택된 푸드트럭 식별키
     */
    @PatchMapping("/selected/{foodTruckId}")
    public ApiResponse<Long> editSelected(@PathVariable Long foodTruckId) {
        log.debug("FoodTruckController#editSelected called");
        log.debug("foodTruckId={}", foodTruckId);

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        Long selectedId = foodTruckService.editSelected(foodTruckId, email);
        log.debug("selectedId={}", selectedId);

        return ApiResponse.ok(selectedId);
    }

    /**
     * 푸드트럭 영업 여부 조회 API
     *
     * @param foodTruckId 푸드트럭 식별키
     * @return 해당 푸드트럭의 영업 여부
     */
    @GetMapping("/open/{foodTruckId}")
    public ApiResponse<Boolean> isOpenFoodTruck(@PathVariable Long foodTruckId) {
        log.debug("FoodTruckController#isOpenFoodTruck called");
        log.debug("foodTruckId={}", foodTruckId);

        Boolean result = foodTruckQueryService.isOpenFoodTruck(foodTruckId);
        log.debug("result={}", result);

        return ApiResponse.ok(result);
    }
}
