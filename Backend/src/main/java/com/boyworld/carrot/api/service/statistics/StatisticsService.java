package com.boyworld.carrot.api.service.statistics;

import com.boyworld.carrot.api.controller.statistics.response.StatisticsByPeriodDetailsResponse;
import com.boyworld.carrot.api.controller.statistics.response.StatisticsByPeriodResponse;
import com.boyworld.carrot.api.controller.statistics.response.StatisticsBySalesDetailsResponse;
import com.boyworld.carrot.api.controller.statistics.response.StatisticsBySalesResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 매출통계 서비스
 *
 * @author 양진형
 *
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class StatisticsService {

    public StatisticsBySalesResponse getStatisticsBySalesList() {
        return null;
    }

    public StatisticsBySalesDetailsResponse getStatisticsBySalesDetail() {
        return null;
    }

    public StatisticsByPeriodResponse getStatisticsByWeekList() {
        return null;
    }

    public StatisticsByPeriodDetailsResponse getStatisticsByPeriodDetail() {
        return null;
    }
}
