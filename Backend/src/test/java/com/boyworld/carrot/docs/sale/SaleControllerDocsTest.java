package com.boyworld.carrot.docs.sale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.boyworld.carrot.api.controller.order.response.OrderResponse;
import com.boyworld.carrot.api.controller.sale.SaleController;
import com.boyworld.carrot.api.controller.sale.request.OpenSaleRequest;
import com.boyworld.carrot.api.controller.sale.response.OpenSaleResponse;
import com.boyworld.carrot.api.service.order.OrderService;
import com.boyworld.carrot.api.service.order.dto.OrderItem;
import com.boyworld.carrot.api.service.sale.SaleService;
import com.boyworld.carrot.api.service.sale.dto.SaleMenuItem;
import com.boyworld.carrot.docs.RestDocsSupport;
import com.boyworld.carrot.domain.order.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
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

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(OrderItem.builder()
            .orderId(1L)
            .status(Status.PROCESSING)
            .createdTime(LocalDateTime.of(2023, 10, 30, 17, 25))
            .expectTime(LocalDateTime.of(2023, 10, 30, 17, 55))
            .totalPrice(10000)
            .build()
        );
        orderItems.add(OrderItem.builder()
            .orderId(2L)
            .status(Status.PROCESSING)
            .createdTime(LocalDateTime.of(2023, 10, 30, 17, 35))
            .expectTime(LocalDateTime.of(2023, 10, 30, 18, 5))
            .totalPrice(20000)
            .build()
        );
        OrderResponse response = OrderResponse.builder()
            .orderItems(orderItems)
            .build();

        given(orderService.getProcessingOrders(1L, "ssafy@ssafy.com"))
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
                            .description("진행 중인 주문"),
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

    void getCompleteOrders() throws Exception {

    }

    void accept() throws Exception {

    }

    void decline() throws Exception {

    }

    void pause() throws Exception {

    }

    void soldout() throws Exception {

    }

    void closeSale() throws Exception {

    }
}
