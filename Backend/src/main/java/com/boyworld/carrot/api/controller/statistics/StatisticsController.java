package com.boyworld.carrot.api.controller.statistics;

import com.boyworld.carrot.api.ApiResponse;
import com.boyworld.carrot.api.controller.statistics.response.*;
import com.boyworld.carrot.api.service.statistics.StatisticsQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * 매출통계 API
 *
 * @author 양진형
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/statistics/{foodTruckId}")
public class StatisticsController {

    private final StatisticsQueryService statisticsService;

    /**
     * 영업 매출 통계 리스트 API
     *
     * @param foodTruckId 푸드트럭 ID
     * @param year 연도
     * @param page 페이지
     * @return 영업별 매출 통계 요약 리스트
     */
    @GetMapping("/sales")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<StatisticsBySalesResponse> getStatisticsBySales(@PathVariable Long foodTruckId,
                                                                       @RequestParam Integer year,
                                                                       @RequestParam Integer month,
                                                                       @RequestParam(defaultValue = "1") Integer page) {
        log.debug("StatisticsController#getStatisticsBySales called !!!");
        log.debug("FoodTruckID={}, year={}, month={}, Page={}", foodTruckId, year, month, page);

        StatisticsBySalesResponse response = statisticsService.getStatisticsBySales(foodTruckId, year, month, page);
        log.debug("StatisticsBySalesResponse={}", response);

        return ApiResponse.ok(response);
    }

    /**
     * 영업 매출 통계 상세 API
     *
     * @param foodTruckId 푸드트럭 Id
     * @param salesId 영업 ID
     * @return 영업 매출 통계 상세 정보
     */
    @GetMapping("/sales-detail/{salesId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<StatisticsBySalesDetailsResponse> getStatisticsBySalesDetails(@PathVariable Long foodTruckId,
                                                                                     @PathVariable Long salesId) {
        log.debug("StatisticsController#getStatisticsBySalesDetails called !!!");
        log.debug("FoodTruckId={}, SalesID={}", foodTruckId, salesId);

        StatisticsBySalesDetailsResponse response = statisticsService.getStatisticsBySalesDetails(foodTruckId, salesId);
        log.debug("StatisticsBySalesDetailsResponse={}", response);

        return ApiResponse.ok(response);
    }

    /**
     * 주별 매출 통계 리스트 API
     *
     * @param foodTruckId 푸드트럭 ID
     * @param year 연도
     * @param page 페이지
     * @return 주별 매출 통계 요약 리스트
     */
    @GetMapping("/weekly")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<StatisticsByWeekResponse> getStatisticsByWeek(@PathVariable Long foodTruckId,
                                                                     @RequestParam Integer year,
                                                                     @RequestParam(defaultValue = "1") Integer page) {
        log.debug("StatisticsController#getStatisticsByWeek called !!!");
        log.debug("FoodTruckID={}, year={}, Page={}", foodTruckId, year, page);

        StatisticsByWeekResponse response = statisticsService.getStatisticsByWeek(foodTruckId, year, page);
        log.debug("StatisticsByWeekResponse={}", response);

        return ApiResponse.ok(response);
    }

    /**
     * 주 매출 통계 상세 API
     *
     * @param foodTruckId 푸드트럭 ID
     * @param startDate 시작일
     * @param endDate 종료일
     * @return 주 매출 통계 상세 정보
     */
    @GetMapping("/weekly-detail")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<StatisticsByWeekDetailsResponse> getStatisticsByWeekDetails(@PathVariable Long foodTruckId,
                                                                                   @RequestParam String startDate,
                                                                                   @RequestParam String endDate) {
        log.debug("StatisticsController#getStatisticsByWeekDetails called !!!");
        log.debug("FoodTruckID={}, startDate={}, endDate={}", foodTruckId, startDate, endDate);

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        StatisticsByWeekDetailsResponse response = statisticsService.getStatisticsByWeekDetails(foodTruckId, start, end);
        log.debug("StatisticsByWeekDetailsResponse={}", response);

        return ApiResponse.ok(response);
    }

    /**
     * 월별 매출 통계 리스트 API
     *
     * @param foodTruckId 푸드트럭 ID
     * @param year 연도
     * @return 월별 매출 통계 요약 리스트
     */
    @GetMapping("/monthly")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<StatisticsByMonthResponse> getStatisticsByMonth(@PathVariable Long foodTruckId,
                                                                       @RequestParam Integer year) {
        log.debug("StatisticsController#getStatisticsByMonth called !!!");
        log.debug("FoodTruckID={}, year={}", foodTruckId, year);

        StatisticsByMonthResponse response = statisticsService.getStatisticsByMonth(foodTruckId, year);
        log.debug("StatisticsByMonthResponse={}", response);

        return ApiResponse.ok(response);
    }

    /**
     * 월 매출 통계 상세 API
     *
     * @param foodTruckId 푸드트럭 ID
     * @param year 연도
     * @param month 월
     * @return 월 매출 통계 상세 정보
     */
    @GetMapping("/monthly-detail")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<StatisticsByMonthDetailsResponse> getStatisticsByMonthDetails(@PathVariable Long foodTruckId,
                                                                                     @RequestParam Integer year,
                                                                                     @RequestParam Integer month) {
        log.debug("StatisticsController#getStatisticsByMonthDetails called !!!");
        log.debug("FoodTruckID={}, year={}, month={}", foodTruckId, year, month);

        StatisticsByMonthDetailsResponse response = statisticsService.getStatisticsByMonthDetails(foodTruckId, year, month);
        log.debug("StatisticsByMonthDetailsResponse={}", response);

        return ApiResponse.ok(response);
    }
}
