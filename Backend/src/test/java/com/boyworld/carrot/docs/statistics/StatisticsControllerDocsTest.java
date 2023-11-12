package com.boyworld.carrot.docs.statistics;

import com.boyworld.carrot.api.controller.statistics.StatisticsController;
import com.boyworld.carrot.api.controller.statistics.response.*;
import com.boyworld.carrot.api.service.statistics.StatisticsQueryService;
import com.boyworld.carrot.api.service.statistics.dto.details.SalesByDayDto;
import com.boyworld.carrot.api.service.statistics.dto.details.SalesByHourDto;
import com.boyworld.carrot.api.service.statistics.dto.details.SalesByMenuDto;
import com.boyworld.carrot.api.service.statistics.dto.list.StatisticsByMonthDto;
import com.boyworld.carrot.api.service.statistics.dto.list.StatisticsBySalesDto;
import com.boyworld.carrot.api.service.statistics.dto.list.StatisticsByWeekDto;
import com.boyworld.carrot.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.restdocs.payload.JsonFieldType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatisticsControllerDocsTest.class)
public class StatisticsControllerDocsTest extends RestDocsSupport {

    private final StatisticsQueryService statisticsQueryService = mock(StatisticsQueryService.class);

    @Override
    protected Object initController() { return new StatisticsController(statisticsQueryService); }

    @DisplayName("영업 매출 통계 리스트 API")
    @Test
    void getStatisticsBySales() throws Exception {

        StatisticsBySalesDto item1 = StatisticsBySalesDto.builder()
                .salesId(1L)
                .date(LocalDateTime.now().toLocalDate().minusDays(3).format(DateTimeFormatter.ISO_LOCAL_DATE))
                .startTime(LocalDateTime.now().minusMinutes(345).toLocalTime().format(DateTimeFormatter.ISO_LOCAL_TIME))
                .endTime(LocalDateTime.now().toLocalTime().format(DateTimeFormatter.ISO_LOCAL_TIME))
                .address("광주광역시 광산구 장덕로5번길 16")
                .totalHours(5)
                .totalMinutes(45)
                .totalSales(113800)
                .build();

        StatisticsBySalesDto item2 = StatisticsBySalesDto.builder()
                .salesId(5L)
                .date(LocalDateTime.now().toLocalDate().minusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE))
                .startTime(LocalDateTime.now().minusMinutes(567).toLocalTime().format(DateTimeFormatter.ISO_LOCAL_TIME))
                .endTime(LocalDateTime.now().toLocalTime().format(DateTimeFormatter.ISO_LOCAL_TIME))
                .address("광주광역시 광산구 선암동 214-4")
                .totalHours(9)
                .totalMinutes(27)
                .totalSales(216600)
                .build();

        StatisticsBySalesDto item3 = StatisticsBySalesDto.builder()
                .salesId(24L)
                .date(LocalDateTime.now().toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .startTime(LocalDateTime.now().minusMinutes(573).toLocalTime().format(DateTimeFormatter.ISO_LOCAL_TIME))
                .endTime(LocalDateTime.now().toLocalTime().format(DateTimeFormatter.ISO_LOCAL_TIME))
                .address("광주광역시 광산구 장덕로5번길 16")
                .totalHours(9)
                .totalMinutes(33)
                .totalSales(213100)
                .build();

        List<StatisticsBySalesDto> items = List.of(item1, item2, item3);

        StatisticsBySalesResponse response = StatisticsBySalesResponse.builder()
                .year(LocalDate.now().getYear())
                .statisticsBySales(items)
                .hasNext(false)
                .build();

        given(statisticsQueryService.getStatisticsBySales(anyLong(), anyInt(), anyInt(), anyLong()))
                .willReturn(response);

        Long fid = 1L;

        mockMvc.perform(
                        get("/statistics/{foodTruckId}/sales", fid)
                                .param("year", "2023")
                                .param("month", "11")
                                .param("lastSalesId", "0")
                                .header("Authentication", "authentication")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-statistics-sales-list",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("foodTruckId")
                                        .description("푸드트럭 ID")
                        ),
                        queryParameters(
                                parameterWithName("year")
                                        .description("연도"),
                                parameterWithName("month")
                                        .description("월"),
                                parameterWithName("lastSalesId")
                                        .description("마지막으로 조회한 영업 ID")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data.year").type(JsonFieldType.NUMBER)
                                        .description("영업년도"),
                                fieldWithPath("data.statisticsBySales[].salesId").type(JsonFieldType.NUMBER)
                                        .description("영업 ID"),
                                fieldWithPath("data.statisticsBySales[].date").type(JsonFieldType.STRING)
                                        .description("영업일"),
                                fieldWithPath("data.statisticsBySales[].startTime").type(JsonFieldType.STRING)
                                        .description("영업 시작 시간"),
                                fieldWithPath("data.statisticsBySales[].endTime").type(JsonFieldType.STRING)
                                        .description("영업 종료 시간"),
                                fieldWithPath("data.statisticsBySales[].address").type(JsonFieldType.STRING)
                                        .description("도로명 주소"),
                                fieldWithPath("data.statisticsBySales[].totalHours").type(JsonFieldType.NUMBER)
                                        .description("판매한 시간"),
                                fieldWithPath("data.statisticsBySales[].totalMinutes").type(JsonFieldType.NUMBER)
                                        .description("판매한 분"),
                                fieldWithPath("data.statisticsBySales[].totalSales").type(JsonFieldType.NUMBER)
                                        .description("총 매출액"),
                                fieldWithPath("data.hasNext").type(JsonFieldType.BOOLEAN)
                                        .description("다음 페이지 존재 여부")
                        )
                ));
    }
    
    @DisplayName("영업 매출 통계 상세 API")
    @Test
    void getStatisticsBySalesDetails() throws Exception {

        SalesByMenuDto menu1 = SalesByMenuDto.builder()
                .menuId(1L)
                .menuName("통베이컨 슈림프 디럭스")
                .totalOrders(5)
                .totalSales(59500)
                .build();

        SalesByMenuDto menu2 = SalesByMenuDto.builder()
                .menuId(2L)
                .menuName("통베이컨 치킨 디럭스")
                .totalOrders(4)
                .totalSales(43600)
                .build();

        SalesByMenuDto menu3 = SalesByMenuDto.builder()
                .menuId(3L)
                .menuName("슈림프 치킨 디럭스")
                .totalOrders(2)
                .totalSales(12700)
                .build();

        List<SalesByMenuDto> menus = List.of(menu1, menu2, menu3);

        SalesByHourDto hour1 = SalesByHourDto.builder()
                .startHour(LocalDateTime.now().minusHours(5).toLocalTime().getHour())
                .totalOrders(1)
                .totalSales(10900)
                .build();

        SalesByHourDto hour2 = SalesByHourDto.builder()
                .startHour(LocalDateTime.now().minusHours(4).toLocalTime().getHour())
                .totalOrders(2)
                .totalSales(22800)
                .build();

        SalesByHourDto hour3 = SalesByHourDto.builder()
                .startHour(LocalDateTime.now().minusHours(3).toLocalTime().getHour())
                .totalOrders(2)
                .totalSales(12700)
                .build();

        SalesByHourDto hour4 = SalesByHourDto.builder()
                .startHour(LocalDateTime.now().minusHours(2).toLocalTime().getHour())
                .totalOrders(3)
                .totalSales(34700)
                .build();

        SalesByHourDto hour5 = SalesByHourDto.builder()
                .startHour(LocalDateTime.now().minusHours(1).toLocalTime().getHour())
                .totalOrders(3)
                .totalSales(34700)
                .build();

        SalesByHourDto hour6 = SalesByHourDto.builder()
                .startHour(LocalDateTime.now().minusHours(0).toLocalTime().getHour())
                .totalOrders(1)
                .totalSales(10900)
                .build();

        List<SalesByHourDto> hours = List.of(hour1, hour2, hour3, hour4, hour5, hour6);

        StatisticsBySalesDetailsResponse response = StatisticsBySalesDetailsResponse.builder()
                .latitude(new BigDecimal("35.19684"))
                .longitude(new BigDecimal("126.8108"))
                .salesByMenu(menus)
                .salesByHour(hours)
                .build();

        given(statisticsQueryService.getStatisticsBySalesDetails(anyLong(), anyLong()))
                .willReturn(response);

        Long fid = 1L;
        Long sid = 1L;

        mockMvc.perform(
                        get("/statistics/{foodTruckId}/sales-detail/{salesId}", fid, sid)
                                .header("Authentication", "authentication")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-statistics-sales-details",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("foodTruckId")
                                        .description("푸드트럭 ID"),
                                parameterWithName("salesId")
                                        .description("영업 ID")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data.latitude").type(JsonFieldType.STRING)
                                        .description("영업 위치 위도"),
                                fieldWithPath("data.longitude").type(JsonFieldType.STRING)
                                        .description("영업 위치 경도"),
                                fieldWithPath("data.salesByMenu[].menuId").type(JsonFieldType.NUMBER)
                                        .description("메뉴 ID"),
                                fieldWithPath("data.salesByMenu[].menuName").type(JsonFieldType.STRING)
                                        .description("메뉴 이름"),
                                fieldWithPath("data.salesByMenu[].totalOrders").type(JsonFieldType.NUMBER)
                                        .description("주문 수"),
                                fieldWithPath("data.salesByMenu[].totalSales").type(JsonFieldType.NUMBER)
                                        .description("주문 매출액"),
                                fieldWithPath("data.salesByHour[].startHour").type(JsonFieldType.NUMBER)
                                        .description("판매 시작 시간"),
                                fieldWithPath("data.salesByHour[].totalOrders").type(JsonFieldType.NUMBER)
                                        .description("주문 수"),
                                fieldWithPath("data.salesByHour[].totalSales").type(JsonFieldType.NUMBER)
                                        .description("주문 매출액")
                                )
                ));
    }

    @DisplayName("주별 매출 통계 리스트 API")
    @Test
    void getStatisticsByWeek() throws Exception {

        StatisticsByWeekDto item1 = StatisticsByWeekDto.builder()
                .startDate(LocalDateTime.now().toLocalDate().minusDays(6).format(DateTimeFormatter.ISO_LOCAL_DATE))
                .endDate(LocalDateTime.now().toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .totalHours(28)
                .totalMinutes(36)
                .totalSales(527700)
                .week(40)
                .build();

        StatisticsByWeekDto item2 = StatisticsByWeekDto.builder()
                .startDate(LocalDateTime.now().toLocalDate().minusDays(13).format(DateTimeFormatter.ISO_LOCAL_DATE))
                .endDate(LocalDateTime.now().toLocalDate().minusDays(7).format(DateTimeFormatter.ISO_LOCAL_DATE))
                .totalHours(58)
                .totalMinutes(36)
                .totalSales(992200)
                .week(39)
                .build();

        List<StatisticsByWeekDto> items = List.of(item1, item2);

        StatisticsByWeekResponse response = StatisticsByWeekResponse.builder()
                .year(LocalDate.now().getYear())
                .statisticsByWeek(items)
                .lastWeek(39)
                .hasNext(false)
                .build();

        given(statisticsQueryService.getStatisticsByWeek(anyLong(), anyInt(), anyInt()))
                .willReturn(response);

        Long fid = 1L;

        mockMvc.perform(
                        get("/statistics/{foodTruckId}/weekly", fid)
                                .param("year", "2023")
                                .param("lastWeek", "1")
                                .header("Authentication", "authentication")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-statistics-weekly-list",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("foodTruckId")
                                        .description("푸드트럭 ID")
                        ),
                        queryParameters(
                                parameterWithName("year")
                                        .description("연도"),
                                parameterWithName("lastWeek")
                                        .description("마지막으로 조회한 영업주")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data.year").type(JsonFieldType.NUMBER)
                                        .description("영업년도"),
                                fieldWithPath("data.lastWeek").type(JsonFieldType.NUMBER)
                                        .description("마지막으로 조회된 주"),
                                fieldWithPath("data.statisticsByWeek[].startDate").type(JsonFieldType.STRING)
                                        .description("주 시작일"),
                                fieldWithPath("data.statisticsByWeek[].endDate").type(JsonFieldType.STRING)
                                        .description("주 종료일"),
                                fieldWithPath("data.statisticsByWeek[].totalHours").type(JsonFieldType.NUMBER)
                                        .description("판매한 시간"),
                                fieldWithPath("data.statisticsByWeek[].totalMinutes").type(JsonFieldType.NUMBER)
                                        .description("판매한 분"),
                                fieldWithPath("data.statisticsByWeek[].totalSales").type(JsonFieldType.NUMBER)
                                        .description("총 매출액"),
                                fieldWithPath("data.statisticsByWeek[].week").type(JsonFieldType.NUMBER)
                                        .description("해당 주"),
                                fieldWithPath("data.hasNext").type(JsonFieldType.BOOLEAN)
                                        .description("다음 페이지 존재 여부")
                        )
                ));
    }

    @DisplayName("주별 매출 통계 상세 API")
    @Test
    void getStatisticsByWeekDetails() throws Exception {

        LocalDate startDate = LocalDateTime.now().toLocalDate().minusDays(6);
        LocalDate endDate = LocalDateTime.now().toLocalDate();

        SalesByMenuDto menu1 = SalesByMenuDto.builder()
                .menuId(1L)
                .menuName("통베이컨 슈림프 디럭스")
                .totalOrders(15)
                .totalSales(178500)
                .build();

        SalesByMenuDto menu2 = SalesByMenuDto.builder()
                .menuId(2L)
                .menuName("통베이컨 치킨 디럭스")
                .totalOrders(12)
                .totalSales(130800)
                .build();

        SalesByMenuDto menu3 = SalesByMenuDto.builder()
                .menuId(3L)
                .menuName("슈림프 치킨 디럭스")
                .totalOrders(10)
                .totalSales(109000)
                .build();

        SalesByMenuDto menu4 = SalesByMenuDto.builder()
                .menuId(4L)
                .menuName("소고기 스테이크 볶음밥")
                .totalOrders(13)
                .totalSales(102700)
                .build();

        SalesByMenuDto menu5 = SalesByMenuDto.builder()
                .menuId(5L)
                .menuName("레드 슈림프 볶음밥")
                .totalOrders(10)
                .totalSales(79000)
                .build();

        SalesByMenuDto menu10 = SalesByMenuDto.builder()
                .menuId(10L)
                .menuName("콜라")
                .totalOrders(12)
                .totalSales(25800)
                .build();

        List<SalesByMenuDto> menus = List.of(menu1, menu2, menu3, menu4, menu5, menu10);

        List<SalesByHourDto> hours = new ArrayList<>();
        int remainSales = 5277;
        int remainOrders = 72;
        for (int i = 13; i <= 22; i++) {
            int totalSales = remainSales / (23 - i);
            int totalOrders = remainOrders / (23 - i);
            if (i != 22) {
                totalSales *= (Math.random() - 0.5) * 2 * 0.25 + 1;
                totalOrders *= (Math.random() - 0.5) * 2 * 0.25 + 1;
            }
            SalesByHourDto hourData = SalesByHourDto.builder()
                    .startHour(i)
                    .totalOrders(totalOrders)
                    .totalSales(totalSales * 100)
                    .build();

            hours.add(hourData);
            remainSales -= totalSales;
            remainOrders -= totalOrders;
        }
        
        List<String> daysName = List.of("월", "화", "수", "목", "금", "토", "일");
        List<SalesByDayDto> days = new ArrayList<>();
        remainSales = 5277;
        remainOrders = 72;
        for (int i = 0; i < 7; i++) {
            int totalSales = remainSales / (7 - i);
            int totalOrders = remainOrders / (7 - i);
            if (i != 6) {
                totalSales *= (Math.random() - 0.5) * 2 * 0.25 + 1;
                totalOrders *= (Math.random() - 0.5) * 2 * 0.25 + 1;
            }
            SalesByDayDto dayData = SalesByDayDto.builder()
                    .day(i)
                    .totalOrders(totalOrders)
                    .totalSales(totalSales * 100)
                    .build();

            days.add(dayData);
            remainSales -= totalSales;
            remainOrders -= totalOrders;
        }

        StatisticsByWeekDetailsResponse response = StatisticsByWeekDetailsResponse.builder()
                .salesByMenu(menus)
                .salesByHour(hours)
                .salesByDay(days)
                .build();

        given(statisticsQueryService.getStatisticsByWeekDetails(anyLong(),
                any(LocalDateTime.class), any(LocalDateTime.class)))
                .willReturn(response);

        Long fid = 1L;

        mockMvc.perform(
                        get("/statistics/{foodTruckId}/weekly-detail", fid)
                                .param("startDate", startDate.format(DateTimeFormatter.ISO_LOCAL_DATE))
                                .param("endDate", endDate.format(DateTimeFormatter.ISO_LOCAL_DATE))
                                .header("Authentication", "authentication")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-statistics-weekly-details",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("foodTruckId")
                                        .description("푸드트럭 ID")
                        ),
                        queryParameters(
                                parameterWithName("startDate")
                                        .description("주 시작일"),
                                parameterWithName("endDate")
                                        .description("주 종료일")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data.salesByMenu[].menuId").type(JsonFieldType.NUMBER)
                                        .description("메뉴 ID"),
                                fieldWithPath("data.salesByMenu[].menuName").type(JsonFieldType.STRING)
                                        .description("메뉴 이름"),
                                fieldWithPath("data.salesByMenu[].totalOrders").type(JsonFieldType.NUMBER)
                                        .description("주문 수"),
                                fieldWithPath("data.salesByMenu[].totalSales").type(JsonFieldType.NUMBER)
                                        .description("주문 매출액"),
                                fieldWithPath("data.salesByHour[].startHour").type(JsonFieldType.NUMBER)
                                        .description("판매 시작 시간"),
                                fieldWithPath("data.salesByHour[].totalOrders").type(JsonFieldType.NUMBER)
                                        .description("주문 수"),
                                fieldWithPath("data.salesByHour[].totalSales").type(JsonFieldType.NUMBER)
                                        .description("주문 매출액"),
                                fieldWithPath("data.salesByDay[].day").type(JsonFieldType.NUMBER)
                                        .description("영업 요일"),
                                fieldWithPath("data.salesByDay[].totalOrders").type(JsonFieldType.NUMBER)
                                        .description("주문 수"),
                                fieldWithPath("data.salesByDay[].totalSales").type(JsonFieldType.NUMBER)
                                        .description("주문 매출액")
                        )
                ));
    }

    @DisplayName("월별 매출 통계 리스트 API")
    @Test
    void getStatisticsByMonth() throws Exception {

        StatisticsByMonthDto item1 = StatisticsByMonthDto.builder()
                .month(10)
                .totalHours(87)
                .totalMinutes(12)
                .totalSales(1773200)
                .build();

        StatisticsByMonthDto item2 = StatisticsByMonthDto.builder()
                .month(9)
                .totalHours(1)
                .totalMinutes(12)
                .totalSales(37600)
                .build();

        List<StatisticsByMonthDto> items = List.of(item1, item2);

        StatisticsByMonthResponse response = StatisticsByMonthResponse.builder()
                .year(LocalDate.now().getYear())
                .statisticsByMonth(items)
                .build();

        given(statisticsQueryService.getStatisticsByMonth(anyLong(), anyInt()))
                .willReturn(response);

        Long fid = 1L;

        mockMvc.perform(
                        get("/statistics/{foodTruckId}/monthly", fid)
                                .param("year", "2023")
                                .header("Authentication", "authentication")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-statistics-monthly-list",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("foodTruckId")
                                        .description("푸드트럭 ID")
                        ),
                        queryParameters(
                                parameterWithName("year")
                                        .description("연도")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data.year").type(JsonFieldType.NUMBER)
                                        .description("영업년도"),
                                fieldWithPath("data.statisticsByMonth[].month").type(JsonFieldType.NUMBER)
                                        .description("영업월"),
                                fieldWithPath("data.statisticsByMonth[].totalHours").type(JsonFieldType.NUMBER)
                                        .description("판매한 시간"),
                                fieldWithPath("data.statisticsByMonth[].totalMinutes").type(JsonFieldType.NUMBER)
                                        .description("판매한 분"),
                                fieldWithPath("data.statisticsByMonth[].totalSales").type(JsonFieldType.NUMBER)
                                        .description("총 매출액")
                        )
                ));
    }

    @DisplayName("월별 매출 통계 상세 API")
    @Test
    void getStatisticsByMonthDetails() throws Exception {

        SalesByMenuDto menu1 = SalesByMenuDto.builder()
                .menuId(1L)
                .menuName("통베이컨 슈림프 디럭스")
                .totalOrders(40)
                .totalSales(476000)
                .build();

        SalesByMenuDto menu2 = SalesByMenuDto.builder()
                .menuId(2L)
                .menuName("통베이컨 치킨 디럭스")
                .totalOrders(24)
                .totalSales(261600)
                .build();

        SalesByMenuDto menu3 = SalesByMenuDto.builder()
                .menuId(3L)
                .menuName("슈림프 치킨 디럭스")
                .totalOrders(15)
                .totalSales(163500)
                .build();

        SalesByMenuDto menu4 = SalesByMenuDto.builder()
                .menuId(4L)
                .menuName("소고기 스테이크 볶음밥")
                .totalOrders(19)
                .totalSales(150100)
                .build();

        SalesByMenuDto menu5 = SalesByMenuDto.builder()
                .menuId(5L)
                .menuName("레드 슈림프 볶음밥")
                .totalOrders(17)
                .totalSales(134300)
                .build();

        SalesByMenuDto menu10 = SalesByMenuDto.builder()
                .menuId(10L)
                .menuName("콜라")
                .totalOrders(30)
                .totalSales(60000)
                .build();

        List<SalesByMenuDto> menus = List.of(menu1, menu2, menu3, menu4, menu5, menu10);

        List<SalesByHourDto> hours = new ArrayList<>();
        int remainSales = 17732;
        int remainOrders = 145;
        for (int i = 13; i <= 22; i++) {
            int totalSales = remainSales / (23 - i);
            int totalOrders = remainOrders / (23 - i);
            if (i != 22) {
                totalSales *= (Math.random() - 0.5) * 2 * 0.25 + 1;
                totalOrders *= (Math.random() - 0.5) * 2 * 0.25 + 1;
            }
            SalesByHourDto hourData = SalesByHourDto.builder()
                    .startHour(i)
                    .totalOrders(totalOrders)
                    .totalSales(totalSales * 100)
                    .build();

            hours.add(hourData);
            remainSales -= totalSales;
            remainOrders -= totalOrders;
        }

        List<String> daysName = List.of("월", "화", "수", "목", "금", "토", "일");
        List<SalesByDayDto> days = new ArrayList<>();
        remainSales = 17732;
        remainOrders = 145;
        for (int i = 0; i < 7; i++) {
            int totalSales = remainSales / (7 - i);
            int totalOrders = remainOrders / (7 - i);
            if (i != 6) {
                totalSales *= (Math.random() - 0.5) * 2 * 0.25 + 1;
                totalOrders *= (Math.random() - 0.5) * 2 * 0.25 + 1;
            }
            SalesByDayDto dayData = SalesByDayDto.builder()
                    .day(i)
                    .totalOrders(totalOrders)
                    .totalSales(totalSales * 100)
                    .build();

            days.add(dayData);
            remainSales -= totalSales;
            remainOrders -= totalOrders;
        }

        StatisticsByMonthDetailsResponse response = StatisticsByMonthDetailsResponse.builder()
                .salesByMenu(menus)
                .salesByHour(hours)
                .salesByDay(days)
                .build();

        given(statisticsQueryService.getStatisticsByMonthDetails(anyLong(), anyInt(), anyInt()))
                .willReturn(response);

        Long fid = 1L;

        mockMvc.perform(
                        get("/statistics/{foodTruckId}/monthly-detail", fid)
                                .param("year", "2023")
                                .param("month", "10")
                                .header("Authentication", "authentication")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-statistics-monthly-details",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("foodTruckId")
                                        .description("푸드트럭 ID")
                        ),
                        queryParameters(
                                parameterWithName("year")
                                        .description("영업연도"),
                                parameterWithName("month")
                                        .description("영업월")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data.salesByMenu[].menuId").type(JsonFieldType.NUMBER)
                                        .description("메뉴 ID"),
                                fieldWithPath("data.salesByMenu[].menuName").type(JsonFieldType.STRING)
                                        .description("메뉴 이름"),
                                fieldWithPath("data.salesByMenu[].totalOrders").type(JsonFieldType.NUMBER)
                                        .description("주문 수"),
                                fieldWithPath("data.salesByMenu[].totalSales").type(JsonFieldType.NUMBER)
                                        .description("주문 매출액"),
                                fieldWithPath("data.salesByHour[].startHour").type(JsonFieldType.NUMBER)
                                        .description("판매 시작 시간"),
                                fieldWithPath("data.salesByHour[].totalOrders").type(JsonFieldType.NUMBER)
                                        .description("주문 수"),
                                fieldWithPath("data.salesByHour[].totalSales").type(JsonFieldType.NUMBER)
                                        .description("주문 매출액"),
                                fieldWithPath("data.salesByDay[].day").type(JsonFieldType.NUMBER)
                                        .description("영업 요일"),
                                fieldWithPath("data.salesByDay[].totalOrders").type(JsonFieldType.NUMBER)
                                        .description("주문 수"),
                                fieldWithPath("data.salesByDay[].totalSales").type(JsonFieldType.NUMBER)
                                        .description("주문 매출액")
                        )
                ));
    }
}
