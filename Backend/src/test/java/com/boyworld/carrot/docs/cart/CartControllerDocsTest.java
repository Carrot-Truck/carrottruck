package com.boyworld.carrot.docs.cart;

import com.boyworld.carrot.api.controller.cart.CartController;
import com.boyworld.carrot.api.controller.cart.request.CreateCartMenuRequest;
import com.boyworld.carrot.api.service.cart.CartService;
import com.boyworld.carrot.api.service.cart.dto.CreateCartMenuDto;
import com.boyworld.carrot.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
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
    @WithMockUser(roles = "CLIENT")
    void createCart() throws Exception {
        CreateCartMenuRequest request = CreateCartMenuRequest.builder()
                .foodTruckId(1l)
                .menuId(1l)
                .cartMenuQuantity(2)
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
                                        fieldWithPath("cartMenuOptionIds").type(JsonFieldType.ARRAY)
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
}
