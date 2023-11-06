package com.boyworld.carrot.api.service.statistics;

import com.boyworld.carrot.api.controller.statistics.response.*;
import com.boyworld.carrot.api.service.geocoding.GeocodingService;
import com.boyworld.carrot.api.service.sale.dto.SalesDto;
import com.boyworld.carrot.api.service.statistics.dto.StatisticsBySalesDto;
import com.boyworld.carrot.domain.sale.repository.query.StatisticsQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    private final GeocodingService geocodingService;

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
    public StatisticsBySalesResponse getStatisticsBySales(Long foodTruckId, Integer year, Integer month, Long lastSalesId) {
        List<SalesDto> salesDtos = statisticsQueryRepository.getSaleList(foodTruckId, year, month, lastSalesId);

        List<StatisticsBySalesDto> statisticsBySalesDtos = new ArrayList<>();
        for (SalesDto salesDto : salesDtos) {
            String address = geocodingService.reverseGeocoding(salesDto.getLatitude(),
                    salesDto.getLongitude(), "legalcode").get("legalcode");

            int totalMinutes = (int)ChronoUnit.MINUTES.between(salesDto.getStartTime(), salesDto.getEndTime());

            StatisticsBySalesDto statisticsBySalesDto = StatisticsBySalesDto.builder()
                    .salesId(salesDto.getSalesId())
                    .date(salesDto.getStartTime().toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE))
                    .startTime(salesDto.getStartTime().toLocalTime().format(DateTimeFormatter.ISO_LOCAL_TIME))
                    .endTime(salesDto.getEndTime().toLocalTime().format(DateTimeFormatter.ISO_LOCAL_TIME))
                    .address(address)
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
     * @param lastStartDate 마지막으로 조회한 영업 시작일
     * @return 매출통계 리스트
     */
    public StatisticsByWeekResponse getStatisticsByWeek(Long foodTruckId, Integer year, LocalDate lastStartDate) {
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

    private boolean checkSalesHasNext(List<StatisticsBySalesDto> StatisticsBySales) {
        if (StatisticsBySales.size() > PAGE_SIZE) {
            StatisticsBySales.remove(PAGE_SIZE);
            return true;
        }
        return false;
    }
}
