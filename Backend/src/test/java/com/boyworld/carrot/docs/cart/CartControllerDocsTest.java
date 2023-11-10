package com.boyworld.carrot.docs.cart;

import com.boyworld.carrot.api.controller.cart.CartController;
import com.boyworld.carrot.api.controller.cart.request.CreateCartMenuRequest;
import com.boyworld.carrot.api.controller.cart.response.CartResponse;
import com.boyworld.carrot.api.service.cart.CartService;
import com.boyworld.carrot.api.service.cart.dto.CartMenuDto;
import com.boyworld.carrot.api.service.cart.dto.CartMenuOptionDto;
import com.boyworld.carrot.api.service.cart.dto.CreateCartMenuDto;
import com.boyworld.carrot.docs.RestDocsSupport;
import com.boyworld.carrot.domain.cart.Cart;
import com.boyworld.carrot.domain.cart.CartMenu;
import com.boyworld.carrot.domain.cart.CartMenuOption;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartControllerDocsTest.class)
public class CartControllerDocsTest extends RestDocsSupport {
    private final CartService cartService = mock(CartService.class);

    @Override
    protected Object initController() {
        return new CartController(cartService);
    }

    @DisplayName("장바구니 추가 API")
    @Test
    @WithMockUser(roles = {"CLIENT", "VENDOR"})
    void createCart() throws Exception {
        CreateCartMenuRequest request = CreateCartMenuRequest.builder()
                .foodTruckId(1l)
                .menuId(1l)
                .cartMenuQuantity(2)
                .cartMenuTotalPrice(9999)
                .menuOptionIds(new ArrayList<>(Arrays.asList(1l, 2l)))
                .build();
        // 요청파라미터 작성

        given(cartService.createCart(any(CreateCartMenuDto.class), anyString()))
                .willReturn(1l);

        mockMvc.perform(
                        post("/cart")
                                .header("Authentication", "authentication")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(
                        document("create-cart",
                                preprocessRequest(prettyPrint()),
                                requestFields(
                                        fieldWithPath("foodTruckId").type(JsonFieldType.NUMBER)
                                                .description("장바구니에 담은 메뉴의 푸드트럭 ID"),
                                        fieldWithPath("menuId").type(JsonFieldType.NUMBER)
                                                .description("장바구니에 담은 메뉴 ID"),
                                        fieldWithPath("cartMenuQuantity").type(JsonFieldType.NUMBER)
                                                .description("장바구니에 담은 메뉴의 수량"),
                                        fieldWithPath("cartMenuTotalPrice").type(JsonFieldType.NUMBER)
                                                .description("장바구니에 담은 메뉴 및 메뉴옵션을 더한 금액"),
                                        fieldWithPath("menuOptionIds").type(JsonFieldType.ARRAY)
                                                .description("잠바구니에 담은 메뉴의 옵션 ID 리스트")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                                                .description("코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING)
                                                .description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING)
                                                .description("메시지"),
                                        fieldWithPath("data").type(JsonFieldType.NUMBER)
                                                .description("장바구니에 담은 메뉴 ID")
                                )
                        )
                );
    }

    @DisplayName("장바구니 조회 API")
    @Test
    @WithMockUser(roles = {"CLIENT", "VENDOR"})
    void getShoppingCart() throws Exception {
        Cart cart = Cart.builder()
                .id("1")
                .foodTruckId(1L)
                .foodTruckName("동현 된장삼겹")
                .totalPrice(1234)
                .cartMenuIds(new ArrayList<>(Arrays.asList("1")))
                .build();

        CartMenu cartMenu = CartMenu.builder()
                .id("1")
                .cartId("1")
                .menuId(1L)
                .name("노른자 된장 삼겹살 덮밥")
                .price(1000)
                .cartMenuTotalPrice(1500)
                .quantity(2)
                .menuImageUrl("imageUrl")
                .cartMenuOptionIds(new ArrayList<>(Arrays.asList("1")))
                .build();

        CartMenuOption cartMenuOption = CartMenuOption.builder()
                .id("1")
                .cartMenuId("1")
                .menuOptionId(1L)
                .name("쌈장")
                .price(500)
                .build();

        List<CartMenuOptionDto> cartMenuOptionDtos = new ArrayList<>(Arrays.asList(CartMenuOptionDto.of(cartMenuOption)));
        List<CartMenuDto> cartMenus = new ArrayList<>(Arrays.asList(CartMenuDto.of(cartMenu, cartMenuOptionDtos)));

        CartResponse response = CartResponse.of(cart, cartMenus);

        given(cartService.getShoppingCart(anyString()))
                .willReturn(response);

        mockMvc.perform(
                        get("/cart")
                                .header("Authentication", "authentication")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("get-shoppingcart",
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                                                .description("코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING)
                                                .description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING)
                                                .description("메시지"),
                                        fieldWithPath("data").type(JsonFieldType.OBJECT)
                                                .description("장바구니 조회 결과"),
                                        fieldWithPath("data.foodTruckName").type(JsonFieldType.STRING)
                                                .description("장바구니에 담은 메뉴들의 푸드트럭 이름"),
                                        fieldWithPath("data.totalPrice").type(JsonFieldType.NUMBER)
                                                .description("장바구니에 담은 메뉴들의 총 가격"),
                                        fieldWithPath("data.cartMenus").type(JsonFieldType.ARRAY)
                                                .description("장바구니에 담은 메뉴 리스트"),
                                        fieldWithPath("data.cartMenus[].cartMenuId").type(JsonFieldType.STRING)
                                                .description("장바구니메뉴 식별키"),
                                        fieldWithPath("data.cartMenus[].menuName").type(JsonFieldType.STRING)
                                                .description("메뉴 이름"),
                                        fieldWithPath("data.cartMenus[].menuPrice").type(JsonFieldType.NUMBER)
                                                .description("메뉴 가격"),
                                        fieldWithPath("data.cartMenus[].cartMenuTotalPrice").type(JsonFieldType.NUMBER)
                                                .description("장바구니에 담긴 메뉴와 메뉴 옵션들을 더한 금액"),
                                        fieldWithPath("data.cartMenus[].cartMenuQuantity").type(JsonFieldType.NUMBER)
                                                .description("장바구니에 담긴 메뉴의 수"),
                                        fieldWithPath("data.cartMenus[].menuImageUrl").type(JsonFieldType.STRING)
                                                .description("메뉴 이미지"),
                                        fieldWithPath("data.cartMenus[].cartMenuOptionDtos").type(JsonFieldType.ARRAY)
                                                .description("장바구니에 담긴 메뉴의 메뉴 옵션 리스트"),
                                        fieldWithPath("data.cartMenus[].cartMenuOptionDtos[].menuOptionName").type(JsonFieldType.STRING)
                                                .description("메뉴 옵션 이름"),
                                        fieldWithPath("data.cartMenus[].cartMenuOptionDtos[].menuOptionPrice").type(JsonFieldType.NUMBER)
                                                .description("메뉴 옵션 가격")
                                )
                        )
                );
    }
}
