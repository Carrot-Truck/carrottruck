package com.boyworld.carrot.api.controller.statistics;

import com.boyworld.carrot.api.ApiResponse;
import com.boyworld.carrot.api.controller.statistics.response.StatisticsByPeriodDetailsResponse;
import com.boyworld.carrot.api.controller.statistics.response.StatisticsByPeriodResponse;
import com.boyworld.carrot.api.controller.statistics.response.StatisticsBySalesDetailsResponse;
import com.boyworld.carrot.api.controller.statistics.response.StatisticsBySalesResponse;
import com.boyworld.carrot.api.service.statistics.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 매출통계 API
 * 
 * @author 양진형
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public ApiResponse<StatisticsBySalesResponse> getStatisticsBySales() {

        return ApiResponse.ok(null);
    }

    public ApiResponse<StatisticsBySalesDetailsResponse> getStatisticsBySalesDetails() {

        return ApiResponse.ok(null);
    }

    public ApiResponse<StatisticsByPeriodResponse> getStatisticsByPeriod() {

        return ApiResponse.ok(null);
    }

    public ApiResponse<StatisticsByPeriodDetailsResponse> getStatisticsByPeriodDetails() {

        return ApiResponse.ok(null);
    }

}
