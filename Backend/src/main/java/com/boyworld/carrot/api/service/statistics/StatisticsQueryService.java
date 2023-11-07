package com.boyworld.carrot.api.service.statistics;

import com.boyworld.carrot.api.controller.statistics.response.*;
import com.boyworld.carrot.api.service.statistics.dto.list.SummaryByMonthDto;
import com.boyworld.carrot.api.service.statistics.dto.list.SummaryBySalesDto;
import com.boyworld.carrot.api.service.statistics.dto.list.SummaryByWeekDto;
import com.boyworld.carrot.api.service.statistics.dto.list.StatisticsByMonthDto;
import com.boyworld.carrot.api.service.statistics.dto.list.StatisticsBySalesDto;
import com.boyworld.carrot.api.service.statistics.dto.list.StatisticsByWeekDto;
import com.boyworld.carrot.domain.sale.repository.query.StatisticsQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.boyworld.carrot.domain.SizeConstants.PAGE_SIZE;

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

    private final StatisticsQueryRepository statisticsQueryRepository;

    /**
     * 영업별 매출통계 리스트
     * 연단위 구분
     * 최신순 정렬
     *
     * @param foodTruckId 푸드트럭 ID
     * @param year 조회 연도
     * @param month 조회 월
     * @param lastSalesId 마지막으로 조회한 영업 ID
     * @return 매출통계 리스트
     */
    public StatisticsBySalesResponse getStatisticsBySales(
            Long foodTruckId, Integer year, Integer month, Long lastSalesId) {
        List<SummaryBySalesDto> salesDtos = statisticsQueryRepository
                .getSaleList(foodTruckId, year, month, lastSalesId);

        List<StatisticsBySalesDto> statisticsBySalesDtos = new ArrayList<>();
        for (SummaryBySalesDto salesDto : salesDtos) {
            int totalMinutes = (int)ChronoUnit.MINUTES.between(salesDto.getStartTime(), salesDto.getEndTime());

            StatisticsBySalesDto statisticsBySalesDto = StatisticsBySalesDto.builder()
                    .salesId(salesDto.getSalesId())
                    .date(salesDto.getStartTime().toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE))
                    .startTime(salesDto.getStartTime().toLocalTime().format(DateTimeFormatter.ISO_LOCAL_TIME))
                    .endTime(salesDto.getEndTime().toLocalTime().format(DateTimeFormatter.ISO_LOCAL_TIME))
                    .address(salesDto.getAddress())
                    .totalHours(totalMinutes / 60)
                    .totalMinutes(totalMinutes % 60)
                    .totalSales(salesDto.getTotalAmount())
                    .build();

            statisticsBySalesDtos.add(statisticsBySalesDto);
        }

        Boolean hasNext = checkSalesHasNext(statisticsBySalesDtos);

        return StatisticsBySalesResponse.of(year, statisticsBySalesDtos, hasNext);
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
     * @param lastWeek 마지막으로 조회한 영업주
     * @return 매출통계 리스트
     */
    public StatisticsByWeekResponse getStatisticsByWeek(Long foodTruckId, Integer year, Integer lastWeek) {
        List<SummaryByWeekDto> weeklyDtos = statisticsQueryRepository.getWeeklyList(foodTruckId, year, lastWeek);

        List<StatisticsByWeekDto> statisticsByWeekDtos = new ArrayList<>();
        for (SummaryByWeekDto weeklyDto : weeklyDtos) {
            int week = weeklyDto.getDays().intValue();
            LocalDate wednesday = calculateNthWednesday(year, week);

            StatisticsByWeekDto statisticsByWeekDto = StatisticsByWeekDto.builder()
                    .startDate(wednesday.minusDays(3).format(DateTimeFormatter.ISO_LOCAL_DATE))
                    .endDate(wednesday.plusDays(3).format(DateTimeFormatter.ISO_LOCAL_DATE))
                    .totalHours(weeklyDto.getTotalMinutes() / 60)
                    .totalMinutes(weeklyDto.getTotalMinutes() % 60)
                    .totalSales(weeklyDto.getTotalAmount())
                    .build();

            statisticsByWeekDtos.add(statisticsByWeekDto);
        }

        Boolean hasNext = checkWeeklyHasNext(statisticsByWeekDtos);

        return StatisticsByWeekResponse.of(year, statisticsByWeekDtos, hasNext);
    }

    /**
     * 주 매출통계 상세
     *
     * @param foodTruckId 푸드트럭 ID
     * @param startDate 조회 시작 시간
     * @param endDate 조회 끝 시간
     * @return 매출통계 상세
     */
    public StatisticsByWeekDetailsResponse getStatisticsByWeekDetails(
            Long foodTruckId, LocalDate startDate, LocalDate endDate) {
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
        List<SummaryByMonthDto> monthlyDtos = statisticsQueryRepository
                .getStatisticsByMonth(foodTruckId, year);

        List<StatisticsByMonthDto> statisticsByMonthDtos = new ArrayList<>();
        for (SummaryByMonthDto monthlyDto : monthlyDtos) {
            StatisticsByMonthDto statisticsByMonthDto = StatisticsByMonthDto.builder()
                    .month(monthlyDto.getMonth())
                    .totalHours(monthlyDto.getTotalMinutes() / 60)
                    .totalMinutes(monthlyDto.getTotalMinutes() % 60)
                    .totalSales(monthlyDto.getTotalAmount())
                    .build();

            statisticsByMonthDtos.add(statisticsByMonthDto);
        }

        return StatisticsByMonthResponse.builder()
                .year(year)
                .statisticsByMonth(statisticsByMonthDtos)
                .build();
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

    private boolean checkSalesHasNext(List<StatisticsBySalesDto> StatisticsBySales) {
        if (StatisticsBySales.size() > PAGE_SIZE) {
            StatisticsBySales.remove(PAGE_SIZE);
            return true;
        }
        return false;
    }

    private boolean checkWeeklyHasNext(List<StatisticsByWeekDto> statisticsByWeekDtos) {
        if (statisticsByWeekDtos.size() > PAGE_SIZE) {
            statisticsByWeekDtos.remove(PAGE_SIZE);
            return true;
        }
        return false;
    }

    private static LocalDate calculateNthWednesday(int year, int week) {
        LocalDate date = LocalDate.of(year, Month.JANUARY, 1);

        while (date.getDayOfWeek() != DayOfWeek.WEDNESDAY) {
            date = date.plusDays(1);
        }

        return date.plusWeeks(week);
    }

}
