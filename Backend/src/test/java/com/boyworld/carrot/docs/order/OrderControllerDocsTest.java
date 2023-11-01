package com.boyworld.carrot.docs.order;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
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

import com.boyworld.carrot.api.controller.order.OrderController;
import com.boyworld.carrot.api.controller.order.request.CreateOrderRequest;
import com.boyworld.carrot.api.controller.order.response.OrderResponse;
import com.boyworld.carrot.api.controller.order.response.OrdersResponse;
import com.boyworld.carrot.api.service.order.OrderService;
import com.boyworld.carrot.api.service.order.dto.OrderItem;
import com.boyworld.carrot.api.service.order.dto.OrderMenuItem;
import com.boyworld.carrot.docs.RestDocsSupport;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.order.Status;
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

@WebMvcTest(OrderControllerDocsTest.class)
public class OrderControllerDocsTest extends RestDocsSupport {

    private final OrderService orderService = mock(OrderService.class);

    @Override
    protected Object initController() { return new OrderController(orderService); }

    @DisplayName("전체 주문 내역 조회 API")
    @Test
    @WithMockUser(roles = "CLIENT")
    void getOrders() throws Exception {

        List<OrderMenuItem> orderMenuItems1 = new ArrayList<>();
        List<OrderMenuItem> orderMenuItems2 = new ArrayList<>();
        List<Long> menuOptionIdList1 = new ArrayList<>(Arrays.asList(2L, 4L));
        List<Long> menuOptionIdList2 = new ArrayList<>(Arrays.asList(1L, 3L));
        List<Long> menuOptionIdList3 = new ArrayList<>(Arrays.asList(1L, 2L));
        orderMenuItems1.add(OrderMenuItem.builder()
            .menuId(1L)
            .name("된장삼겹")
            .quantity(1)
            .price(10000)
            .menuOptionIdList(menuOptionIdList1)
            .build());
        orderMenuItems1.add(OrderMenuItem.builder()
            .menuId(2L)
            .name("돼지갈비")
            .quantity(2)
            .price(15000)
            .menuOptionIdList(menuOptionIdList2)
            .build());
        orderMenuItems2.add(OrderMenuItem.builder()
            .menuId(2L)
            .name("돼지갈비")
            .quantity(1)
            .price(15000)
            .menuOptionIdList(menuOptionIdList3)
            .build());

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(OrderItem.builder()
            .orderId(1L)
            .orderCnt(0)
            .status(Status.COMPLETE)
            .totalPrice(40000)
            .createdTime(LocalDateTime.of(2023, 10, 30, 17, 25))
            .expectTime(LocalDateTime.of(2023, 10, 30, 17, 55))
            .orderMenuItems(orderMenuItems1)
            .build()
        );
        orderItems.add(OrderItem.builder()
            .orderId(2L)
            .orderCnt(1)
            .status(Status.PROCESSING)
            .totalPrice(15000)
            .createdTime(LocalDateTime.of(2023, 11, 1, 17, 35))
            .expectTime(LocalDateTime.of(2023, 11, 1, 18, 5))
                .orderMenuItems(orderMenuItems2)
            .build()
        );
        OrdersResponse response = OrdersResponse.builder()
            .orderItems(orderItems)
            .build();

        given(orderService.getOrders(anyString()))
            .willReturn(response);

        mockMvc.perform(
            get("/order")
                .header("Authentication", "authentication")
        ).andDo(print())
            .andExpect(status().isOk())
            .andDo(
                document("get-orders",
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
                        fieldWithPath("data.orderItems[].memberId").type(JsonFieldType.NUMBER).optional()
                            .description("회원 ID"),
                        fieldWithPath("data.orderItems[].nickname").type(JsonFieldType.NUMBER).optional()
                            .description("회원 닉네임"),
                        fieldWithPath("data.orderItems[].phoneNumber").type(JsonFieldType.NUMBER).optional()
                            .description("회원 연락처"),
                        fieldWithPath("data.orderItems[].orderCnt").type(JsonFieldType.NUMBER)
                            .description("기존 주문 횟수"),
                        fieldWithPath("data.orderItems[].status").type(JsonFieldType.STRING)
                            .description("주문 상태"),
                        fieldWithPath("data.orderItems[].totalPrice").type(JsonFieldType.NUMBER)
                            .description("주문 총액"),
                        fieldWithPath("data.orderItems[].createdTime").type(JsonFieldType.ARRAY)
                            .description("주문 생성 시각"),
                        fieldWithPath("data.orderItems[].expectTime").type(JsonFieldType.ARRAY)
                            .description("주문 완료 예상 시각"),
                        fieldWithPath("data.orderItems[].orderMenuItems").type(JsonFieldType.ARRAY)
                            .description("주문 메뉴 리스트"),
                        fieldWithPath("data.orderItems[].orderMenuItems[].menuId").type(JsonFieldType.NUMBER)
                            .description("주문 메뉴 ID"),
                        fieldWithPath("data.orderItems[].orderMenuItems[].name").type(JsonFieldType.STRING)
                            .description("주문 메뉴 이름"),
                        fieldWithPath("data.orderItems[].orderMenuItems[].quantity").type(JsonFieldType.NUMBER)
                            .description("주문 수량"),
                        fieldWithPath("data.orderItems[].orderMenuItems[].price").type(JsonFieldType.NUMBER)
                            .description("메뉴 가격"),
                        fieldWithPath("data.orderItems[].orderMenuItems[].menuOptionIdList").type(JsonFieldType.ARRAY)
                            .description("메뉴 옵션 ID 리스트")
                    )
                )
            );
    }

    @DisplayName("주문 내역 삭제 API")
    @Test
    @WithMockUser(roles = "CLIENT")
    void deleteOrder() throws Exception {

        given(orderService.deleteOrder(anyLong(), anyString()))
            .willReturn(1L);

        mockMvc.perform(
            delete("/order/1")
                .header("Authentication", "authentication")
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(
                document("delete-order",
                    preprocessResponse(prettyPrint()),
                    responseFields(
                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                            .description("코드"),
                        fieldWithPath("status").type(JsonFieldType.STRING)
                            .description("상태"),
                        fieldWithPath("message").type(JsonFieldType.STRING)
                            .description("메시지"),
                        fieldWithPath("data").type(JsonFieldType.NUMBER)
                            .description("삭제한 주문 ID")
                    )
                )
            );
    }

    @DisplayName("고객 - 주문 상세 조회 API")
    @Test
    @WithMockUser(roles = "CLIENT")
    void getOrderByClient() throws Exception {

        List<OrderMenuItem> orderMenuItems = new ArrayList<>();

        orderMenuItems.add(OrderMenuItem.builder()
                .quantity(1)
                .menuOptionIdList(new ArrayList<>(Arrays.asList(1L, 2L, 3L)))
            .build());

        OrderResponse response = OrderResponse.builder()
            .orderItem(OrderItem.builder()
                .orderId(1L)
                .memberId(1L)
                .totalPrice(10000)
                .status(Status.COMPLETE)
                .createdTime(LocalDateTime.of(2023, 10, 30, 15, 35))
                .expectTime(LocalDateTime.of(2023, 10, 30, 15, 55))
                .orderMenuItems(orderMenuItems)
                .build()).build();

        given(orderService.getOrder(anyLong(), anyString(), Role.valueOf(anyString())))
            .willReturn(response);

        mockMvc.perform(
            get("/order/client/1")
                .header("Authentication", "authentication")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(
                document("get-order-client",
                    preprocessResponse(prettyPrint()),
                    responseFields(

                    )
                )
            );
    }

    @DisplayName("고객 - 주문 상세 조회 API")
    @Test
    @WithMockUser(roles = "VENDOR")
    void getOrderByVendor() throws Exception {

        List<OrderMenuItem> orderMenuItems = new ArrayList<>();

        orderMenuItems.add(OrderMenuItem.builder()
                .quantity(1)
                .menuOptionIdList(new ArrayList<>(Arrays.asList(1L, 2L, 3L)))
                .build());

        OrderResponse response = OrderResponse.builder()
            .orderItem(OrderItem.builder()
                .orderId(1L)
                .memberId(1L)
                .totalPrice(10000)
                .status(Status.COMPLETE)
                .createdTime(LocalDateTime.of(2023, 10, 30, 15, 35))
                .expectTime(LocalDateTime.of(2023, 10, 30, 15, 55))
                .orderMenuItems(orderMenuItems)
                .build()).build();

        given(orderService.getOrder(anyLong(), anyString(), Role.valueOf(anyString())))
                .willReturn(response);

        mockMvc.perform(
                        get("/order/vendor/1")
                                .header("Authentication", "authentication")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("get-order-vendor",
                                preprocessResponse(prettyPrint()),
                                responseFields(

                                )
                        )
                );
    }

    @DisplayName("주문 생성 API")
    @Test
    @WithMockUser(roles = "CLIENT")
    void createOrder() throws Exception {

        List<OrderMenuItem> orderMenuItems = new ArrayList<>();

        orderMenuItems.add(OrderMenuItem.builder()
            .quantity(1)
            .menuOptionIdList(new ArrayList<>(Arrays.asList(1L, 2L, 3L)))
            .build());

        CreateOrderRequest request = CreateOrderRequest.builder()
            .orderMenuItems(orderMenuItems)
            .build();

        given(orderService.createOrder(eq(request.toCreateOrderDto()), anyString()))
            .willReturn(1L);

        mockMvc.perform(
            post("/order/create")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(
                document("create-order",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestFields(
                        fieldWithPath("orderMenuItems").type(JsonFieldType.ARRAY)
                            .description("주문할 메뉴 리스트")
                    ),
                    responseFields(
                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                            .description("코드"),
                        fieldWithPath("status").type(JsonFieldType.STRING)
                            .description("상태"),
                        fieldWithPath("message").type(JsonFieldType.STRING)
                            .description("메시지"),
                        fieldWithPath("data").type(JsonFieldType.NUMBER)
                            .description("생성된 주문 ID")
                    )
                )
            );

    }

    @DisplayName("취소할 주문 정보")
    @Test
    @WithMockUser(roles = "CLIENT")
    void cancelOrder() throws Exception {

        given(orderService.cancelOrder(anyLong(), anyString()))
            .willReturn(1L);

        mockMvc.perform(
            put("/order/1")
                .header("Authentication", "authentication")
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(
                document("create-order",
                    preprocessResponse(prettyPrint()),
                    responseFields(
                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                            .description("코드"),
                        fieldWithPath("status").type(JsonFieldType.STRING)
                            .description("상태"),
                        fieldWithPath("message").type(JsonFieldType.STRING)
                            .description("메시지"),
                        fieldWithPath("data").type(JsonFieldType.NUMBER)
                            .description("취소한 주문 ID")
                    )
                )
            );
    }

}
