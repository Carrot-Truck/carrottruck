package com.boyworld.carrot.docs.sale;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.boyworld.carrot.api.controller.order.response.ClientOrderResponse;
import com.boyworld.carrot.api.controller.order.response.VendorOrderResponse;
import com.boyworld.carrot.api.controller.sale.SaleController;
import com.boyworld.carrot.api.controller.sale.request.AcceptOrderRequest;
import com.boyworld.carrot.api.controller.sale.request.DeclineOrderRequest;
import com.boyworld.carrot.api.controller.sale.request.OpenSaleRequest;
import com.boyworld.carrot.api.controller.sale.response.CloseSaleResponse;
import com.boyworld.carrot.api.controller.sale.response.OpenSaleResponse;
import com.boyworld.carrot.api.service.order.OrderService;
import com.boyworld.carrot.api.service.order.dto.ClientOrderItem;
import com.boyworld.carrot.api.service.order.dto.VendorOrderItem;
import com.boyworld.carrot.api.service.sale.SaleService;
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
            .latitude(BigDecimal.valueOf(36.1234))
            .longitude(BigDecimal.valueOf(128.5678))
            .saleMenuItems(saleMenuItems)
            .build();

        OpenSaleResponse response = OpenSaleResponse.builder()
            .saleId(1L)
            .saleMenuItems(saleMenuItems)
            .build();

        given(saleService.openSale(request.toOpenSaleDto()))
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

        List<VendorOrderItem> vendorOrderItems = new ArrayList<>();
        vendorOrderItems.add(VendorOrderItem.builder()
            .orderId(1L)
            .status(Status.PROCESSING)
            .createdTime(LocalDateTime.of(2023, 10, 30, 17, 25))
            .expectTime(LocalDateTime.of(2023, 10, 30, 17, 55))
            .totalPrice(10000)
            .build()
        );
        vendorOrderItems.add(VendorOrderItem.builder()
            .orderId(2L)
            .status(Status.PENDING)
            .createdTime(LocalDateTime.of(2023, 10, 30, 17, 35))
            .expectTime(LocalDateTime.of(2023, 10, 30, 18, 5))
            .totalPrice(20000)
            .build()
        );
        VendorOrderResponse response = VendorOrderResponse.builder()
            .vendorOrderItems(vendorOrderItems)
            .build();

        given(orderService.getProcessingOrders(anyLong(), anyString()))
            .willReturn(response);

        mockMvc.perform(
            get("/sale/processing/1")
                .header("Authentication", "authentication")
        ).andDo(print())
            .andExpect(status().isOk())
            .andDo(
                document("get-processing-orders",
                    preprocessResponse(prettyPrint()),
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
                            .description("주문 ID"),
                        fieldWithPath("data.orderItems[].status").type(JsonFieldType.STRING)
                            .description("주문 상태"),
                        fieldWithPath("data.orderItems[].createdTime").type(JsonFieldType.ARRAY)
                            .description("주문 생성 시각"),
                        fieldWithPath("data.orderItems[].expectTime").type(JsonFieldType.ARRAY)
                            .description("주문 완료 예상 시각"),
                        fieldWithPath("data.orderItems[].totalPrice").type(JsonFieldType.NUMBER)
                            .description("주문 총액")
                    )
                )
            );
    }

    @DisplayName("완료된 주문 조회 API")
    @Test
    @WithMockUser(roles = "VENDOR")
    void getCompleteOrders() throws Exception {

        List<VendorOrderItem> vendorOrderItems = new ArrayList<>();
        vendorOrderItems.add(VendorOrderItem.builder()
            .orderId(3L)
            .status(Status.COMPLETE)
            .createdTime(LocalDateTime.of(2023, 11, 1, 15, 25))
            .expectTime(LocalDateTime.of(2023, 11, 1, 15, 55))
            .totalPrice(30000)
            .build()
        );
        vendorOrderItems.add(VendorOrderItem.builder()
            .orderId(4L)
            .status(Status.CANCELLED)
            .createdTime(LocalDateTime.of(2023, 11, 1, 15, 35))
            .expectTime(null)
            .totalPrice(40000)
            .build()
        );
        vendorOrderItems.add(VendorOrderItem.builder()
            .orderId(5L)
            .status(Status.DECLINED)
            .createdTime(LocalDateTime.of(2023, 11, 1, 15, 45))
            .expectTime(null)
            .totalPrice(50000)
            .build()
        );
        VendorOrderResponse response = VendorOrderResponse.builder()
            .vendorOrderItems(vendorOrderItems)
            .build();

        given(orderService.getCompleteOrders(anyLong(), anyString()))
            .willReturn(response);

        mockMvc.perform(
                get("/sale/complete/1")
                    .header("Authentication", "authentication")
            ).andDo(print())
            .andExpect(status().isOk())
            .andDo(
                document("get-complete-orders",
                    preprocessResponse(prettyPrint()),
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
                            .description("완료된 ID"),
                        fieldWithPath("data.orderItems[].status").type(JsonFieldType.STRING)
                            .description("완료된 상태"),
                        fieldWithPath("data.orderItems[].createdTime").type(JsonFieldType.ARRAY)
                            .description("완료된 시각"),
                        fieldWithPath("data.orderItems[].expectTime").type(JsonFieldType.ARRAY).optional()
                            .description("주문 완료 시각"),
                        fieldWithPath("data.orderItems[].totalPrice").type(JsonFieldType.NUMBER)
                            .description("주문 총액")
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

        given(saleService.acceptOrder(eq(request.toAcceptOrderDto()), anyString()))
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

        given(saleService.declineOrder(eq(request.toDeclineOrderDto()), anyString()))
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

        mockMvc.perform(
                put("/sale/pause/1")
                    .header("Authentication", "authentication")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(
                document("pause-order",
                    preprocessResponse(prettyPrint()),
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

    @DisplayName("품절 메뉴 등록 API")
    @Test
    @WithMockUser(roles = "VENDOR")
    void soldout() throws Exception {
        given(saleService.soldOutMenu(anyLong(), anyString()))
            .willReturn(1L);

        mockMvc.perform(
                put("/sale/soldout/1")
                    .header("Authentication", "authentication")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(
                document("soldout-menu",
                    preprocessResponse(prettyPrint()),
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

        mockMvc.perform(
                put("/sale/close/1")
                    .header("Authentication", "authentication")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(
                document("close-sale",
                    preprocessResponse(prettyPrint()),
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