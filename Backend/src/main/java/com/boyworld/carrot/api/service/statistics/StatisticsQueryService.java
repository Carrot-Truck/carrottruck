package com.boyworld.carrot.api.service.statistics;

import com.boyworld.carrot.api.controller.statistics.response.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * 매출통계 서비스
 *
 * @author 양진형
 *
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatisticsQueryService {

    /**
     * 영업별 매출통계 리스트
     * 연단위 구분
     * 최신순 정렬
     *
     * @param foodTruckId 푸드트럭 ID
     * @param year 조회 연도
     * @param page 페이지
     * @return 매출통계 리스트
     */
    public StatisticsBySalesResponse getStatisticsBySales(Long foodTruckId, Integer year, Integer page) {
        return null;
    }

    /**
     * 영업 매출통계 상세
     *
     * @param salesId 영업 ID
     * @return 매출통계 상세
     */
    public StatisticsBySalesDetailsResponse getStatisticsBySalesDetails(Long foodTruckId, Long salesId) {
        return null;
    }

    /**
     * 주별 매출통계 리스트
     * 연단위 구분
     * 최신순 정렬
     *
     * @param foodTruckId 푸드트럭 ID
     * @param year 조회 연도
     * @param page 페이지
     * @return 매출통계 리스트
     */
    public StatisticsByWeekResponse getStatisticsByWeek(Long foodTruckId, Integer year, Integer page) {
        return null;
    }

    /**
     * 주 매출통계 상세
     *
     * @param foodTruckId 푸드트럭 ID
     * @param startDate 조회 시작 시간
     * @param endDate 조회 끝 시간
     * @return 매출통계 상세
     */
    public StatisticsByWeekDetailsResponse getStatisticsByWeekDetails(Long foodTruckId, LocalDate startDate, LocalDate endDate) {
        return null;
    }

    /**
     * 월별 매출통계 리스트
     * 연단위 구분
     * 최신순 정렬
     *
     * @param foodTruckId 푸드트럭 ID
     * @param year 조회 연도
     * @return 매출통계 리스트
     */
    public StatisticsByMonthResponse getStatisticsByMonth(Long foodTruckId, Integer year) {
        return null;
    }

    /**
     * 월 매출통계 상세
     *
     * @param foodTruckId 푸드트럭 ID
     * @param year 년
     * @param month 월
     * @return 매출통계 상세
     */
    public StatisticsByMonthDetailsResponse getStatisticsByMonthDetails(Long foodTruckId, Integer year, Integer month) {
        return null;
    }
}
