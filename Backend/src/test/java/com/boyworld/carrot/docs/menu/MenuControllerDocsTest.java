package com.boyworld.carrot.docs.menu;

import com.boyworld.carrot.api.controller.menu.MenuController;
import com.boyworld.carrot.api.controller.menu.request.CreateMenuRequest;
import com.boyworld.carrot.api.controller.menu.response.CreateMenuResponse;
import com.boyworld.carrot.api.service.menu.MenuService;
import com.boyworld.carrot.api.service.menu.dto.CreateMenuDto;
import com.boyworld.carrot.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MenuControllerDocsTest.class)
public class MenuControllerDocsTest extends RestDocsSupport {

    private final MenuService menuService = mock(MenuService.class);

    @Override
    protected Object initController() {
        return new MenuController(menuService);
    }

    @DisplayName("메뉴 등록 API")
    @Test
    @WithMockUser(roles = "VENDOR")
    void createMenu() throws Exception {

        CreateMenuRequest request = CreateMenuRequest.builder()
                .foodTruckId(1L)
                .menuName("달콤짭짤한 밥도둑 된장 삼겹살 구이")
                .price(8900)
                .description("동현 된장삼겹의 시그니쳐. 오직 된장 삼겹살 구이만!")
                .build();

        CreateMenuResponse response = CreateMenuResponse.builder()
                .menuId(1L)
                .foodTruckId(1L)
                .menuName("달콤짭짤한 밥도둑 된장 삼겹살 구이")
                .price(8900)
                .description("동현 된장삼겹의 시그니쳐. 오직 된장 삼겹살 구이만!")
                .build();

        given(menuService.createMenu(any(CreateMenuDto.class), anyString()))
                .willReturn(response);

        mockMvc.perform(
                        post("/menu")
                                .header("Authentication", "authentication")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("create-menu",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("foodTruckId").type(JsonFieldType.NUMBER)
                                        .description("푸드트럭 식별키"),
                                fieldWithPath("menuName").type(JsonFieldType.STRING)
                                        .description("메뉴명"),
                                fieldWithPath("price").type(JsonFieldType.NUMBER)
                                        .description("메뉴 가격"),
                                fieldWithPath("description").type(JsonFieldType.STRING)
                                        .description("메뉴 설명")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT)
                                        .description("응답 데이터"),
                                fieldWithPath("data.menuId").type(JsonFieldType.NUMBER)
                                        .description("메뉴 식별키"),
                                fieldWithPath("data.foodTruckId").type(JsonFieldType.NUMBER)
                                        .description("푸드트럭 식별키"),
                                fieldWithPath("data.menuName").type(JsonFieldType.STRING)
                                        .description("메뉴명"),
                                fieldWithPath("data.price").type(JsonFieldType.NUMBER)
                                        .description("메뉴 가격"),
                                fieldWithPath("data.description").type(JsonFieldType.STRING)
                                        .description("메뉴 설명")
                        )
                ));
    }
}
