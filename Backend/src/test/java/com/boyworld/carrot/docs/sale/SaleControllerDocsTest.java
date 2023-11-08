package com.boyworld.carrot.docs.sale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.boyworld.carrot.api.controller.order.response.OrdersResponse;
import com.boyworld.carrot.api.controller.sale.SaleController;
import com.boyworld.carrot.api.controller.sale.request.AcceptOrderRequest;
import com.boyworld.carrot.api.controller.sale.request.DeclineOrderRequest;
import com.boyworld.carrot.api.controller.sale.request.OpenSaleRequest;
import com.boyworld.carrot.api.controller.sale.response.CloseSaleResponse;
import com.boyworld.carrot.api.controller.sale.response.OpenSaleResponse;
import com.boyworld.carrot.api.service.order.OrderService;
import com.boyworld.carrot.api.service.order.dto.OrderItem;
import com.boyworld.carrot.api.service.order.dto.OrderMenuItem;
import com.boyworld.carrot.api.service.order.dto.OrderMenuOptionItem;
import com.boyworld.carrot.api.service.sale.SaleService;
import com.boyworld.carrot.api.service.sale.dto.AcceptOrderDto;
import com.boyworld.carrot.api.service.sale.dto.DeclineOrderDto;
import com.boyworld.carrot.api.service.sale.dto.OpenSaleDto;
import com.boyworld.carrot.api.service.sale.dto.SaleMenuItem;
import com.boyworld.carrot.docs.RestDocsSupport;
import com.boyworld.carrot.domain.order.Status;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;

@WebMvcTest(SaleControllerDocsTest.class)
public class SaleControllerDocsTest extends RestDocsSupport {

    private final SaleService saleService = mock(SaleService.class);
    private final OrderService orderService = mock(OrderService.class);

    @Override
    protected Object initController() { return new SaleController(saleService, orderService); }

    @DisplayName("영업 개시 API")
    @Test
    @WithMockUser(roles = "VENDOR")
    void openSale() throws Exception {

        List<SaleMenuItem> saleMenuItems = new ArrayList<>();

        List<Long> menuOptionId = new ArrayList<>(Arrays.asList(1L, 2L, 3L));
        saleMenuItems.add(SaleMenuItem.builder()
            .menuId(1L)
            .menuOptionId(menuOptionId)
            .build());

        OpenSaleRequest request = OpenSaleRequest.builder()
            .foodTruckId(1L)
            .address("서울 마포구 동교로 104")
            .latitude(BigDecimal.valueOf(36.1234))
            .longitude(BigDecimal.valueOf(128.5678))
            .saleMenuItems(saleMenuItems)
            .build();

        OpenSaleResponse response = OpenSaleResponse.builder()
            .saleId(1L)
            .saleMenuItems(saleMenuItems)
            .build();

        given(saleService.openSale(any(OpenSaleDto.class), anyString()))
            .willReturn(response);

        mockMvc.perform(
            post("/sale/open")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(
                document("open-sale",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestFields(
                        fieldWithPath("foodTruckId").type(JsonFieldType.NUMBER)
                            .description("푸드트럭 ID"),
                        fieldWithPath("address").type(JsonFieldType.STRING)
                            .description("푸드트럭 위치 - 도로명"),
                        fieldWithPath("longitude").type(JsonFieldType.NUMBER)
                            .description("푸드트럭 위치 - 경도"),
                        fieldWithPath("latitude").type(JsonFieldType.NUMBER)
                            .description("푸드트럭 위치 - 위도"),
                        fieldWithPath("saleMenuItems").type(JsonFieldType.ARRAY)
                            .description("판매할 메뉴"),
                        fieldWithPath("saleMenuItems[].menuId").type(JsonFieldType.NUMBER)
                            .description("메뉴 ID"),
                        fieldWithPath("saleMenuItems[].menuOptionId").type(JsonFieldType.ARRAY)
                            .description("메뉴 옵션 ID 리스트")
                    ),
                    responseFields(
                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                            .description("코드"),
                        fieldWithPath("status").type(JsonFieldType.STRING)
                            .description("상태"),
                        fieldWithPath("message").type(JsonFieldType.STRING)
                            .description("메시지"),
                        fieldWithPath("data").type(JsonFieldType.OBJECT)
                            .description("개시한 영업 정보"),
                        fieldWithPath("data.saleId").type(JsonFieldType.NUMBER)
                            .description("개시한 영업 ID"),
                        fieldWithPath("data.saleMenuItems").type(JsonFieldType.ARRAY)
                            .description("판매할 메뉴"),
                        fieldWithPath("data.saleMenuItems[].menuId").type(JsonFieldType.NUMBER)
                            .description("메뉴 ID"),
                        fieldWithPath("data.saleMenuItems[].menuOptionId").type(JsonFieldType.ARRAY)
                            .description("메뉴 옵션 ID 리스트")
                    )
            )
        );
    }

    @DisplayName("진행 중인 주문 조회 API")
    @Test
    @WithMockUser(roles = "VENDOR")
    void getProcessingOrders() throws Exception {

        List<OrderMenuItem> orderMenuItems1 = new ArrayList<>();
        List<OrderMenuItem> orderMenuItems2 = new ArrayList<>();
        List<OrderMenuOptionItem> menuOptionIdList1 = new ArrayList<>();
        menuOptionIdList1.add(OrderMenuOptionItem.builder().id(2L).quantity(1).build());
        menuOptionIdList1.add(OrderMenuOptionItem.builder().id(4L).quantity(2).build());
        List<OrderMenuOptionItem> menuOptionIdList2 = new ArrayList<>();
        menuOptionIdList1.add(OrderMenuOptionItem.builder().id(1L).quantity(3).build());
        menuOptionIdList1.add(OrderMenuOptionItem.builder().id(3L).quantity(1).build());
        List<OrderMenuOptionItem> menuOptionIdList3 = new ArrayList<>();
        menuOptionIdList1.add(OrderMenuOptionItem.builder().id(1L).quantity(1).build());
        menuOptionIdList1.add(OrderMenuOptionItem.builder().id(2L).quantity(1).build());
        orderMenuItems1.add(OrderMenuItem.builder()
            .id(1L)
            .menuId(1L)
            .quantity(1)
            .menuOptionList(menuOptionIdList1)
            .build());
        orderMenuItems1.add(OrderMenuItem.builder()
            .id(2L)
            .menuId(2L)
            .quantity(2)
            .menuOptionList(menuOptionIdList2)
            .build());
        orderMenuItems2.add(OrderMenuItem.builder()
            .id(3L)
            .menuId(2L)
            .quantity(1)
            .menuOptionList(menuOptionIdList3)
            .build());

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(OrderItem.builder()
            .orderId(1L)
            .memberId(1L)
            .nickname("매미킴")
            .phoneNumber("010-1324-9786")
            .status(Status.PROCESSING)
            .orderCnt(0)
            .totalPrice(40000)
            .createdTime(LocalDateTime.of(2023, 10, 30, 17, 25))
            .expectTime(LocalDateTime.of(2023, 10, 30, 17, 55))
            .orderMenuItems(orderMenuItems1)
            .build()
        );
        orderItems.add(OrderItem.builder()
            .orderId(2L)
            .memberId(3L)
            .nickname("양진리")
            .phoneNumber("010-9876-6543")
            .orderCnt(2)
            .status(Status.PENDING)
            .totalPrice(15000)
            .createdTime(LocalDateTime.of(2023, 11, 1, 17, 35))
            .expectTime(null)
            .orderMenuItems(orderMenuItems2)
            .build()
        );

        OrdersResponse response = OrdersResponse.builder()
            .orderItems(orderItems)
            .build();

        given(orderService.getProcessingOrders(anyLong(), anyString()))
            .willReturn(response);

        Long foodTruckId = 1L;
        mockMvc.perform(
            get("/sale/processing/{foodTruckId}", foodTruckId)
                .header("Authentication", "authentication")
        ).andDo(print())
            .andExpect(status().isOk())
            .andDo(
                document("get-processing-orders",
                    preprocessResponse(prettyPrint()),
                    pathParameters(
                        parameterWithName("foodTruckId")
                            .description("푸드트럭 ID")
                    ),
                    responseFields(
                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                            .description("코드"),
                        fieldWithPath("status").type(JsonFieldType.STRING)
                            .description("상태"),
                        fieldWithPath("message").type(JsonFieldType.STRING)
                            .description("메시지"),
                        fieldWithPath("data").type(JsonFieldType.OBJECT)
                            .description("진행 중인 주문"),
                        fieldWithPath("data.orderItems").type(JsonFieldType.ARRAY)
                            .description("진행 중인 주문 정보"),
                        fieldWithPath("data.orderItems[].orderId").type(JsonFieldType.NUMBER)
                            .description("완료된 주문 ID"),
                        fieldWithPath("data.orderItems[].memberId").type(JsonFieldType.NUMBER)
                            .description("주문한 회원 ID"),
                        fieldWithPath("data.orderItems[].nickname").type(JsonFieldType.STRING)
                            .description("주문한 회원 닉네임"),
                        fieldWithPath("data.orderItems[].phoneNumber").type(JsonFieldType.STRING)
                            .description("주문한 회원 연락처"),
                        fieldWithPath("data.orderItems[].status").type(JsonFieldType.STRING)
                            .description("주문 상태"),
                        fieldWithPath("data.orderItems[].orderCnt").type(JsonFieldType.NUMBER)
                            .description("이전 주문 횟수"),
                        fieldWithPath("data.orderItems[].totalPrice").type(JsonFieldType.NUMBER)
                            .description("주문 총액"),
                        fieldWithPath("data.orderItems[].createdTime").type(JsonFieldType.ARRAY)
                            .description("주문 생성 시각"),
                        fieldWithPath("data.orderItems[].expectTime").type(JsonFieldType.ARRAY).optional()
                            .description("주문 완료 (예상) 시각"),
                        fieldWithPath("data.orderItems[].orderMenuItems").type(JsonFieldType.ARRAY)
                            .description("주문 메뉴 리스트"),
                        fieldWithPath("data.orderItems[].orderMenuItems[].id").type(JsonFieldType.ARRAY)
                            .description("주문 메뉴 식별키"),
                        fieldWithPath("data.orderItems[].orderMenuItems[].menuId").type(JsonFieldType.NUMBER)
                            .description("주문한 메뉴 ID"),
                        fieldWithPath("data.orderItems[].orderMenuItems[].quantity").type(JsonFieldType.NUMBER)
                            .description("주문 수량"),
                        fieldWithPath("data.orderItems[].orderMenuItems[].menuOptionList").type(JsonFieldType.ARRAY)
                            .description("메뉴 옵션 ID 리스트"),
                        fieldWithPath("orderMenuItems[].menuOptionList[].id").type(JsonFieldType.NUMBER)
                                .description("메뉴 옵션 ID"),
                        fieldWithPath("orderMenuItems[].menuOptionList[].quantity").type(JsonFieldType.NUMBER)
                                .description("옵션 수량")
                    )
                )
            );
    }

    @DisplayName("완료된 주문 조회 API")
    @Test
    @WithMockUser(roles = "VENDOR")
    void getCompleteOrders() throws Exception {

        List<OrderMenuItem> orderMenuItems1 = new ArrayList<>();
        List<OrderMenuItem> orderMenuItems2 = new ArrayList<>();
        List<OrderMenuOptionItem> menuOptionIdList1 = new ArrayList<>();
        menuOptionIdList1.add(OrderMenuOptionItem.builder().id(2L).quantity(1).build());
        menuOptionIdList1.add(OrderMenuOptionItem.builder().id(4L).quantity(2).build());
        List<OrderMenuOptionItem> menuOptionIdList2 = new ArrayList<>();
        menuOptionIdList1.add(OrderMenuOptionItem.builder().id(1L).quantity(3).build());
        menuOptionIdList1.add(OrderMenuOptionItem.builder().id(3L).quantity(1).build());
        List<OrderMenuOptionItem> menuOptionIdList3 = new ArrayList<>();
        menuOptionIdList1.add(OrderMenuOptionItem.builder().id(1L).quantity(1).build());
        menuOptionIdList1.add(OrderMenuOptionItem.builder().id(2L).quantity(1).build());
        orderMenuItems1.add(OrderMenuItem.builder()
            .menuId(1L)
            .quantity(1)
            .menuOptionList(menuOptionIdList1)
            .build());
        orderMenuItems1.add(OrderMenuItem.builder()
            .menuId(2L)
            .quantity(2)
            .menuOptionList(menuOptionIdList2)
            .build());
        orderMenuItems2.add(OrderMenuItem.builder()
            .menuId(2L)
            .quantity(1)
            .menuOptionList(menuOptionIdList3)
            .build());

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(OrderItem.builder()
            .orderId(1L)
            .memberId(1L)
            .nickname("매미킴")
            .phoneNumber("010-1324-9786")
            .status(Status.COMPLETE)
            .orderCnt(0)
            .totalPrice(40000)
            .createdTime(LocalDateTime.of(2023, 10, 30, 17, 25))
            .expectTime(LocalDateTime.of(2023, 10, 30, 17, 55))
            .orderMenuItems(orderMenuItems1)
            .build()
        );
        orderItems.add(OrderItem.builder()
            .orderId(2L)
            .memberId(3L)
            .nickname("양진리")
            .phoneNumber("010-9876-6543")
            .orderCnt(2)
            .status(Status.CANCELLED)
            .totalPrice(15000)
            .createdTime(LocalDateTime.of(2023, 11, 1, 17, 35))
            .expectTime(null)
            .orderMenuItems(orderMenuItems2)
            .build()
        );
        orderItems.add(OrderItem.builder()
            .orderId(3L)
            .memberId(2L)
            .nickname("최정문")
            .phoneNumber("010-3214-6543")
            .orderCnt(2)
            .status(Status.DECLINED)
            .totalPrice(15000)
            .createdTime(LocalDateTime.of(2023, 11, 1, 18, 35))
            .expectTime(null)
            .orderMenuItems(orderMenuItems1)
            .build()
        );
        OrdersResponse response = OrdersResponse.builder()
            .orderItems(orderItems)
            .build();

        given(orderService.getCompleteOrders(anyLong(), anyString()))
            .willReturn(response);

        Long foodTruckId = 1L;
        mockMvc.perform(
                get("/sale/complete/{foodTruckId}", foodTruckId)
                    .header("Authentication", "authentication")
            ).andDo(print())
            .andExpect(status().isOk())
            .andDo(
                document("get-complete-orders",
                    preprocessResponse(prettyPrint()),
                    pathParameters(
                        parameterWithName("foodTruckId")
                            .description("푸드트럭 ID")
                    ),
                    responseFields(
                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                            .description("코드"),
                        fieldWithPath("status").type(JsonFieldType.STRING)
                            .description("상태"),
                        fieldWithPath("message").type(JsonFieldType.STRING)
                            .description("메시지"),
                        fieldWithPath("data").type(JsonFieldType.OBJECT)
                            .description("완료된 주문"),
                        fieldWithPath("data.orderItems").type(JsonFieldType.ARRAY)
                            .description("완료된 주문 정보"),
                        fieldWithPath("data.orderItems[].orderId").type(JsonFieldType.NUMBER)
                            .description("완료된 주문 ID"),
                        fieldWithPath("data.orderItems[].memberId").type(JsonFieldType.NUMBER)
                            .description("주문한 회원 ID"),
                        fieldWithPath("data.orderItems[].nickname").type(JsonFieldType.STRING)
                            .description("주문한 회원 닉네임"),
                        fieldWithPath("data.orderItems[].phoneNumber").type(JsonFieldType.STRING)
                            .description("주문한 회원 연락처"),
                        fieldWithPath("data.orderItems[].status").type(JsonFieldType.STRING)
                            .description("주문 상태"),
                        fieldWithPath("data.orderItems[].orderCnt").type(JsonFieldType.NUMBER)
                            .description("이전 주문 횟수"),
                        fieldWithPath("data.orderItems[].totalPrice").type(JsonFieldType.NUMBER)
                            .description("주문 총액"),
                        fieldWithPath("data.orderItems[].createdTime").type(JsonFieldType.ARRAY)
                            .description("주문 생성 시각"),
                        fieldWithPath("data.orderItems[].expectTime").type(JsonFieldType.ARRAY).optional()
                            .description("주문 완료 시각"),
                        fieldWithPath("data.orderItems[].orderMenuItems").type(JsonFieldType.ARRAY)
                            .description("주문 메뉴 리스트"),
                        fieldWithPath("data.orderItems[].orderMenuItems[].menuId").type(JsonFieldType.NUMBER)
                            .description("주문 메뉴 ID"),
                        fieldWithPath("data.orderItems[].orderMenuItems[].quantity").type(JsonFieldType.NUMBER)
                            .description("주문 수량"),
                        fieldWithPath("data.orderItems[].orderMenuItems[].menuOptionIdList").type(JsonFieldType.ARRAY)
                            .description("메뉴 옵션 ID 리스트")
                    )
                )
            );
    }

    @DisplayName("주문 수락 API")
    @Test
    @WithMockUser(roles = "VENDOR")
    void accept() throws Exception {

        AcceptOrderRequest request = AcceptOrderRequest.builder()
            .orderId(1L)
            .prepareTime(30)
            .build();

        given(saleService.acceptOrder(any(AcceptOrderDto.class), anyString()))
            .willReturn(1L);

        mockMvc.perform(
            post("/sale/accept")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(
                document("accept-order",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestFields(
                        fieldWithPath("orderId").type(JsonFieldType.NUMBER)
                            .description("주문 ID"),
                        fieldWithPath("prepareTime").type(JsonFieldType.NUMBER)
                            .description("주문 소요 시간")
                    ),
                    responseFields(
                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                            .description("코드"),
                        fieldWithPath("status").type(JsonFieldType.STRING)
                            .description("상태"),
                        fieldWithPath("message").type(JsonFieldType.STRING)
                            .description("메시지"),
                        fieldWithPath("data").type(JsonFieldType.NUMBER)
                            .description("수락한 주문 ID")
                    )
                )
            );
    }

    @DisplayName("주문 거절 API")
    @Test
    @WithMockUser(roles = "VENDOR")
    void decline() throws Exception {
        DeclineOrderRequest request = DeclineOrderRequest.builder()
            .orderId(2L)
            .reason("개인 사정")
            .build();

        given(saleService.declineOrder(any(DeclineOrderDto.class), anyString()))
            .willReturn(2L);

        mockMvc.perform(
                post("/sale/decline")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(
                document("decline-order",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestFields(
                        fieldWithPath("orderId").type(JsonFieldType.NUMBER)
                            .description("주문 ID"),
                        fieldWithPath("reason").type(JsonFieldType.STRING)
                            .description("주문 거절 사유")
                    ),
                    responseFields(
                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                            .description("코드"),
                        fieldWithPath("status").type(JsonFieldType.STRING)
                            .description("상태"),
                        fieldWithPath("message").type(JsonFieldType.STRING)
                            .description("메시지"),
                        fieldWithPath("data").type(JsonFieldType.NUMBER)
                            .description("거절한 주문 ID")
                    )
                )
            );
    }

    @DisplayName("주문 일시 정지 API")
    @Test
    @WithMockUser(roles = "VENDOR")
    void pause() throws Exception {
        given(saleService.pauseOrder(anyLong(), anyString()))
            .willReturn(1L);

        Long foodTruckId = 1L;
        mockMvc.perform(
                put("/sale/pause/{foodTruckId}", foodTruckId)
                    .header("Authentication", "authentication")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(
                document("pause-order",
                    preprocessResponse(prettyPrint()),
                    pathParameters(
                        parameterWithName("foodTruckId")
                            .description("푸드트럭 ID")
                    ),
                    responseFields(
                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                            .description("코드"),
                        fieldWithPath("status").type(JsonFieldType.STRING)
                            .description("상태"),
                        fieldWithPath("message").type(JsonFieldType.STRING)
                            .description("메시지"),
                        fieldWithPath("data").type(JsonFieldType.NUMBER)
                            .description("주문 일시 정지한 푸드트럭 ID")
                    )
                )
            );
    }

    @DisplayName("주문 일시 정지 해제 API")
    @Test
    @WithMockUser(roles = "VENDOR")
    void restart() throws Exception {
        given(saleService.restartOrder(anyLong(), anyString()))
            .willReturn(1L);

        Long foodTruckId = 1L;
        mockMvc.perform(
                put("/sale/restart/{foodTruckId}", foodTruckId)
                    .header("Authentication", "authentication")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(
                document("restart-order",
                    preprocessResponse(prettyPrint()),
                    pathParameters(
                        parameterWithName("foodTruckId")
                            .description("푸드트럭 ID")
                    ),
                    responseFields(
                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                            .description("코드"),
                        fieldWithPath("status").type(JsonFieldType.STRING)
                            .description("상태"),
                        fieldWithPath("message").type(JsonFieldType.STRING)
                            .description("메시지"),
                        fieldWithPath("data").type(JsonFieldType.NUMBER)
                            .description("주문 일시 정지 해제한 푸드트럭 ID")
                    )
                )
            );
    }

    @DisplayName("품절 메뉴 등록 API")
    @Test
    @WithMockUser(roles = "VENDOR")
    void soldout() throws Exception {
        given(saleService.soldOutMenu(anyLong(), anyString()))
            .willReturn(1L);

        Long menuId = 1L;
        mockMvc.perform(
                put("/sale/soldout/{menuId}", menuId)
                    .header("Authentication", "authentication")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(
                document("soldout-menu",
                    preprocessResponse(prettyPrint()),
                    pathParameters(
                        parameterWithName("menuId")
                            .description("품절 등록할 메뉴 ID")
                    ),
                    responseFields(
                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                            .description("코드"),
                        fieldWithPath("status").type(JsonFieldType.STRING)
                            .description("상태"),
                        fieldWithPath("message").type(JsonFieldType.STRING)
                            .description("메시지"),
                        fieldWithPath("data").type(JsonFieldType.NUMBER)
                            .description("품절 등록한 메뉴 ID")
                    )
                )
            );
    }

    @DisplayName("영업 종료 API")
    @Test
    @WithMockUser(roles = "VENDOR")
    void closeSale() throws Exception {

        CloseSaleResponse response = CloseSaleResponse.builder()
            .saleId(1L)
            .orderNumber(100)
            .totalAmount(100000)
            .createdTime(LocalDateTime.of(2023, 11, 1, 15, 0))
            .endTime(LocalDateTime.of(2023, 11, 1, 20, 0))
            .build();

        given(saleService.closeSale(anyLong(), anyString()))
            .willReturn(response);

        Long foodTruckId = 1L;
        mockMvc.perform(
                put("/sale/close/{foodTruckId}", foodTruckId)
                    .header("Authentication", "authentication")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(
                document("close-sale",
                    preprocessResponse(prettyPrint()),
                    pathParameters(
                        parameterWithName("foodTruckId")
                            .description("푸드트럭 ID")
                    ),
                    responseFields(
                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                            .description("코드"),
                        fieldWithPath("status").type(JsonFieldType.STRING)
                            .description("상태"),
                        fieldWithPath("message").type(JsonFieldType.STRING)
                            .description("메시지"),
                        fieldWithPath("data").type(JsonFieldType.OBJECT)
                            .description("종료한 영업 정보"),
                        fieldWithPath("data.saleId").type(JsonFieldType.NUMBER)
                            .description("종료한 영업 ID"),
                        fieldWithPath("data.orderNumber").type(JsonFieldType.NUMBER)
                            .description("영업 총 주문 수"),
                        fieldWithPath("data.totalAmount").type(JsonFieldType.NUMBER)
                            .description("영업 총 매출액"),
                        fieldWithPath("data.createdTime").type(JsonFieldType.ARRAY)
                            .description("영업 개시 시각"),
                        fieldWithPath("data.endTime").type(JsonFieldType.ARRAY)
                            .description("영업 종료 시각")
                    )
                )
            );
    }
}