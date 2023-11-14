package com.boyworld.carrot.docs.order;

import com.boyworld.carrot.api.controller.order.OrderController;
import com.boyworld.carrot.api.controller.order.request.CreateOrderRequest;
import com.boyworld.carrot.api.controller.order.response.OrderResponse;
import com.boyworld.carrot.api.controller.order.response.OrdersResponse;
import com.boyworld.carrot.api.service.order.OrderService;
import com.boyworld.carrot.api.service.order.dto.CreateOrderMenuDto;
import com.boyworld.carrot.api.service.order.dto.OrderItem;
import com.boyworld.carrot.api.service.order.dto.OrderMenuItem;
import com.boyworld.carrot.docs.RestDocsSupport;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.order.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderControllerDocsTest.class)
public class OrderControllerDocsTest extends RestDocsSupport {

    private final OrderService orderService = mock(OrderService.class);

    @Override
    protected Object initController() {
        return new OrderController(orderService);
    }

    @DisplayName("전체 주문 내역 조회 API")
    @Test
    @WithMockUser(roles = "CLIENT")
    void getOrders() throws Exception {

        List<OrderMenuItem> orderMenuItems1 = new ArrayList<>();
        List<OrderMenuItem> orderMenuItems2 = new ArrayList<>();
        List<Long> menuOptionIdList1 = new ArrayList<>(Arrays.asList(2L, 4L));
        List<Long> menuOptionIdList2 = new ArrayList<>(Arrays.asList(1L, 2L));
        List<Long> menuOptionIdList3 = new ArrayList<>(Arrays.asList(2L, 3L));

        orderMenuItems1.add(OrderMenuItem.builder()
                .id(1L)
                .menuId(1L)
                .quantity(1)
                .menuName("메뉴 이름1")
                .price(1000)
                .menuOptionList(menuOptionIdList1)
                .build());
        orderMenuItems1.add(OrderMenuItem.builder()
                .id(2L)
                .menuId(2L)
                .quantity(1)
                .menuName("메뉴 이름2")
                .price(2000)
                .menuOptionList(menuOptionIdList2)
                .build());
        orderMenuItems2.add(OrderMenuItem.builder()
                .id(3L)
                .menuId(2L)
                .quantity(1)
                .menuName("메뉴 이름3")
                .price(3000)
                .menuOptionList(menuOptionIdList3)
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
                                        fieldWithPath("data.orderItems[].nickname").type(JsonFieldType.STRING).optional()
                                                .description("회원 닉네임"),
                                        fieldWithPath("data.orderItems[].phoneNumber").type(JsonFieldType.STRING).optional()
                                                .description("회원 연락처"),
                                        fieldWithPath("data.orderItems[].orderCnt").type(JsonFieldType.NUMBER)
                                                .description("이전 주문 횟수"),
                                        fieldWithPath("data.orderItems[].status").type(JsonFieldType.STRING)
                                                .description("주문 상태"),
                                        fieldWithPath("data.orderItems[].totalPrice").type(JsonFieldType.NUMBER)
                                                .description("주문 총액"),
                                        fieldWithPath("data.orderItems[].createdTime").type(JsonFieldType.STRING)
                                                .description("주문 생성 시각"),
                                        fieldWithPath("data.orderItems[].expectTime").type(JsonFieldType.STRING)
                                                .description("주문 완료 예상 시각"),
                                        fieldWithPath("data.orderItems[].orderMenuItems").type(JsonFieldType.ARRAY)
                                                .description("주문 메뉴 리스트"),
                                        fieldWithPath("data.orderItems[].orderMenuItems[].id").type(JsonFieldType.NUMBER)
                                                .description("주문 메뉴 식별키"),
                                        fieldWithPath("data.orderItems[].orderMenuItems[].menuId").type(JsonFieldType.NUMBER)
                                                .description("주문한 메뉴 ID"),
                                        fieldWithPath("data.orderItems[].orderMenuItems[].quantity").type(JsonFieldType.NUMBER)
                                                .description("주문 수량"),
                                        fieldWithPath("data.orderItems[].orderMenuItems[].menuName").type(JsonFieldType.STRING)
                                                .description("주문 메뉴 이름"),
                                        fieldWithPath("data.orderItems[].orderMenuItems[].price").type(JsonFieldType.NUMBER)
                                                .description("주문 메뉴 가격"),
                                        fieldWithPath("data.orderItems[].orderMenuItems[].menuOptionList").type(JsonFieldType.ARRAY)
                                                .description("메뉴 옵션 ID 리스트")
                                )

                        )
                );
    }

    @DisplayName("고객 - 주문 상세 조회 API")
    @Test
    @WithMockUser(roles = "CLIENT")
    void getOrderByClient() throws Exception {

        List<OrderMenuItem> orderMenuItems = new ArrayList<>();
        List<Long> orderMenuOptionList1 = new ArrayList<>(Arrays.asList(1L, 2L, 3L));
        List<Long> orderMenuOptionList2 = new ArrayList<>(Arrays.asList(2L, 4L));


        orderMenuItems.add(OrderMenuItem.builder()
                .id(1L)
                .menuId(1L)
                .quantity(1)
                .menuName("메뉴 이름1")
                .price(1000)
                .menuOptionList(orderMenuOptionList1)
                .build());

        orderMenuItems.add(OrderMenuItem.builder()
                .id(2L)
                .menuId(2L)
                .quantity(2)
                .menuName("메뉴 이름2")
                .price(2000)
                .menuOptionList(orderMenuOptionList2)
                .build());

        OrderResponse response = OrderResponse.builder()
                .orderItem(OrderItem.builder()
                        .orderId(1L)
                        .status(Status.COMPLETE)
                        .orderCnt(0)
                        .totalPrice(40000)
                        .createdTime(LocalDateTime.of(2023, 10, 30, 15, 35))
                        .expectTime(LocalDateTime.of(2023, 10, 30, 15, 55))
                        .orderMenuItems(orderMenuItems)
                        .build()).build();

        given(orderService.getOrder(anyLong(), anyString(), any(Role.class)))
                .willReturn(response);

        Long orderId = 1L;
        mockMvc.perform(
                        get("/order/client/{orderId}", orderId)
                                .header("Authentication", "authentication")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("get-order-by-client",
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("orderId")
                                                .description("주문 ID")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                                                .description("코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING)
                                                .description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING)
                                                .description("메시지"),
                                        fieldWithPath("data").type(JsonFieldType.OBJECT)
                                                .description("상세 조회할 주문"),
                                        fieldWithPath("data.orderItem").type(JsonFieldType.OBJECT)
                                                .description("상세 조회할 주문 정보"),
                                        fieldWithPath("data.orderItem.orderId").type(JsonFieldType.NUMBER)
                                                .description("주문 ID"),
                                        fieldWithPath("data.orderItem.orderCnt").type(JsonFieldType.NUMBER)
                                                .description("이전 주문 횟수"),
                                        fieldWithPath("data.orderItem.status").type(JsonFieldType.STRING)
                                                .description("주문 상태"),
                                        fieldWithPath("data.orderItem.totalPrice").type(JsonFieldType.NUMBER)
                                                .description("주문 총액"),
                                        fieldWithPath("data.orderItem.createdTime").type(JsonFieldType.STRING)
                                                .description("주문 생성 시각"),
                                        fieldWithPath("data.orderItem.expectTime").type(JsonFieldType.STRING)
                                                .description("주문 완료 예상 시각"),
                                        fieldWithPath("data.orderItem.orderMenuItems").type(JsonFieldType.ARRAY)
                                                .description("주문 메뉴 리스트"),
                                        fieldWithPath("data.orderItem.orderMenuItems[].id").type(JsonFieldType.NUMBER)
                                                .description("주문 메뉴 식별키"),
                                        fieldWithPath("data.orderItem.orderMenuItems[].menuId").type(JsonFieldType.NUMBER)
                                                .description("주문한 메뉴 ID"),
                                        fieldWithPath("data.orderItem.orderMenuItems[].quantity").type(JsonFieldType.NUMBER)
                                                .description("주문 수량"),
                                        fieldWithPath("data.orderItem.orderMenuItems[].menuName").type(JsonFieldType.STRING)
                                                .description("주문 메뉴 이름"),
                                        fieldWithPath("data.orderItem.orderMenuItems[].price").type(JsonFieldType.NUMBER)
                                                .description("주문 메뉴 가격"),
                                        fieldWithPath("data.orderItem.orderMenuItems[].menuOptionList").type(JsonFieldType.ARRAY)
                                                .description("메뉴 옵션 ID 리스트")
                                )
                        )
                );
    }

    @DisplayName("사업자 - 주문 상세 조회 API")
    @Test
    @WithMockUser(roles = "VENDOR")
    void getOrderByVendor() throws Exception {

        List<OrderMenuItem> orderMenuItems = new ArrayList<>();
        List<Long> orderMenuOptionList1 = new ArrayList<>(Arrays.asList(1L, 2L, 3L));
        List<Long> orderMenuOptionList2 = new ArrayList<>(Arrays.asList(2L, 4L));

        orderMenuItems.add(OrderMenuItem.builder()
                .id(1L)
                .menuId(1L)
                .quantity(1)
                .menuName("메뉴 이름1")
                .price(1000)
                .menuOptionList(orderMenuOptionList1)
                .build());

        orderMenuItems.add(OrderMenuItem.builder()
                .id(2L)
                .menuId(2L)
                .quantity(2)
                .menuName("메뉴 이름2")
                .price(2000)
                .menuOptionList(orderMenuOptionList2)
                .build());

        OrderResponse response = OrderResponse.builder()
                .orderItem(OrderItem.builder()
                        .orderId(1L)
                        .status(Status.COMPLETE)
                        .orderCnt(0)
                        .totalPrice(40000)
                        .createdTime(LocalDateTime.of(2023, 10, 30, 15, 35))
                        .expectTime(LocalDateTime.of(2023, 10, 30, 15, 55))
                        .orderMenuItems(orderMenuItems)
                        .build()).build();

        given(orderService.getOrder(anyLong(), anyString(), any(Role.class)))
                .willReturn(response);

        Long orderId = 1L;
        mockMvc.perform(
                        get("/order/vendor/{orderId}", orderId)
                                .header("Authentication", "authentication")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("get-order-by-vendor",
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("orderId")
                                                .description("주문 ID")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                                                .description("코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING)
                                                .description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING)
                                                .description("메시지"),
                                        fieldWithPath("data").type(JsonFieldType.OBJECT)
                                                .description("상세 조회할 주문"),
                                        fieldWithPath("data.orderItem").type(JsonFieldType.OBJECT)
                                                .description("상세 조회할 주문 정보"),
                                        fieldWithPath("data.orderItem.orderId").type(JsonFieldType.NUMBER)
                                                .description("주문 ID"),
                                        fieldWithPath("data.orderItem.orderCnt").type(JsonFieldType.NUMBER)
                                                .description("이전 주문 횟수"),
                                        fieldWithPath("data.orderItem.status").type(JsonFieldType.STRING)
                                                .description("주문 상태"),
                                        fieldWithPath("data.orderItem.totalPrice").type(JsonFieldType.NUMBER)
                                                .description("주문 총액"),
                                        fieldWithPath("data.orderItem.createdTime").type(JsonFieldType.STRING)
                                                .description("주문 생성 시각"),
                                        fieldWithPath("data.orderItem.expectTime").type(JsonFieldType.STRING)
                                                .description("주문 완료 예상 시각"),
                                        fieldWithPath("data.orderItem.orderMenuItems").type(JsonFieldType.ARRAY)
                                                .description("주문 메뉴 리스트"),
                                        fieldWithPath("data.orderItem.orderMenuItems[].id").type(JsonFieldType.NUMBER)
                                                .description("주문 메뉴 식별키"),
                                        fieldWithPath("data.orderItem.orderMenuItems[].menuId").type(JsonFieldType.NUMBER)
                                                .description("주문한 메뉴 ID"),
                                        fieldWithPath("data.orderItem.orderMenuItems[].quantity").type(JsonFieldType.NUMBER)
                                                .description("주문 수량"),
                                        fieldWithPath("data.orderItem.orderMenuItems[].menuName").type(JsonFieldType.STRING)
                                                .description("주문 메뉴 이름"),
                                        fieldWithPath("data.orderItem.orderMenuItems[].price").type(JsonFieldType.NUMBER)
                                                .description("주문 메뉴 가격"),
                                        fieldWithPath("data.orderItem.orderMenuItems[].menuOptionList").type(JsonFieldType.ARRAY)
                                                .description("메뉴 옵션 ID 리스트")
                                )
                        )
                );
    }

    @DisplayName("주문 생성 API")
    @Test
    @WithMockUser(roles = "CLIENT")
    void createOrder() throws Exception {

        List<CreateOrderMenuDto> orderMenuItems = new ArrayList<>();
        List<Long> orderMenuOptionList1 = new ArrayList<>(Arrays.asList(1L, 2L, 3L));
        List<Long> orderMenuOptionList2 = new ArrayList<>(Arrays.asList(2L, 4L));

        orderMenuItems.add(CreateOrderMenuDto.builder()
                .id(1L)
                .menuId(1L)
                .quantity(1)
                .menuOptionList(orderMenuOptionList1)
                .build());

        orderMenuItems.add(CreateOrderMenuDto.builder()
                .id(2L)
                .menuId(2L)
                .quantity(2)
                .menuOptionList(orderMenuOptionList2)
                .build());

        CreateOrderRequest request = CreateOrderRequest.builder()
                .foodTruckId(1L)
                .totalPrice(40000)
                .orderMenuItems(orderMenuItems)
                .build();

        given(orderService.createOrder(anyString()))
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
                                        fieldWithPath("foodTruckId").type(JsonFieldType.NUMBER)
                                                .description("주문할 푸드트럭 ID"),
                                        fieldWithPath("totalPrice").type(JsonFieldType.NUMBER)
                                                .description("주문 총액"),
                                        fieldWithPath("orderMenuItems").type(JsonFieldType.ARRAY)
                                                .description("주문할 메뉴 리스트"),
                                        fieldWithPath("orderMenuItems[].id").type(JsonFieldType.NUMBER)
                                                .description("주문 메뉴 식별키"),
                                        fieldWithPath("orderMenuItems[].menuId").type(JsonFieldType.NUMBER)
                                                .description("주문한 메뉴 ID"),
                                        fieldWithPath("orderMenuItems[].quantity").type(JsonFieldType.NUMBER)
                                                .description("주문 수량"),
                                        fieldWithPath("orderMenuItems[].menuOptionList").type(JsonFieldType.ARRAY)
                                                .description("메뉴 옵션 ID 리스트")
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

    @DisplayName("주문 취소 API")
    @Test
    @WithMockUser(roles = "CLIENT")
    void cancelOrder() throws Exception {

        given(orderService.cancelOrder(anyLong(), anyString()))
                .willReturn(1L);

        Long orderId = 1L;
        mockMvc.perform(
                        put("/order/{orderId}", orderId)
                                .header("Authentication", "authentication")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("cancel-order",
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("orderId")
                                                .description("주문 ID")
                                ),
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

    @DisplayName("주문 내역 삭제 API")
    @Test
    @WithMockUser(roles = "CLIENT")
    void deleteOrder() throws Exception {

        given(orderService.deleteOrder(anyLong(), anyString()))
                .willReturn(1L);

        Long orderId = 1L;
        mockMvc.perform(
                        delete("/order/{orderId}", orderId)
                                .header("Authentication", "authentication")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("delete-order",
                                preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("orderId")
                                                .description("주문 ID")
                                ),
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
}
