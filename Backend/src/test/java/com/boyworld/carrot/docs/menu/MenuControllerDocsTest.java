package com.boyworld.carrot.docs.menu;

import com.boyworld.carrot.api.controller.menu.MenuController;
import com.boyworld.carrot.api.controller.menu.MenuResponse;
import com.boyworld.carrot.api.controller.menu.request.CreateMenuOptionRequest;
import com.boyworld.carrot.api.controller.menu.request.CreateMenuRequest;
import com.boyworld.carrot.api.controller.menu.response.CreateMenuResponse;
import com.boyworld.carrot.api.service.menu.MenuQueryService;
import com.boyworld.carrot.api.service.menu.MenuService;
import com.boyworld.carrot.api.service.menu.dto.CreateMenuDto;
import com.boyworld.carrot.api.service.menu.dto.MenuDto;
import com.boyworld.carrot.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MenuControllerDocsTest.class)
public class MenuControllerDocsTest extends RestDocsSupport {

    private final MenuService menuService = mock(MenuService.class);
    private final MenuQueryService menuQueryService = mock(MenuQueryService.class);

    @Override
    protected Object initController() {
        return new MenuController(menuService, menuQueryService);
    }

    @DisplayName("메뉴 등록 API")
    @Test
    @WithMockUser(roles = "VENDOR")
    void createMenu() throws Exception {

        CreateMenuOptionRequest option1 = CreateMenuOptionRequest.builder()
                .menuOptionName("옵션1")
                .menuOptionPrice(500)
                .menuOptionDescription("설명1")
                .build();

        CreateMenuOptionRequest option2 = CreateMenuOptionRequest.builder()
                .menuOptionName("옵션2")
                .menuOptionPrice(300)
                .menuOptionDescription("설명2")
                .build();

        CreateMenuRequest request = CreateMenuRequest.builder()
                .foodTruckId(1L)
                .menuName("달콤짭짤한 밥도둑 된장 삼겹살 구이")
                .price(8900)
                .description("동현 된장삼겹의 시그니쳐. 오직 된장 삼겹살 구이만!")
                .menuOptions(List.of(option1, option2))
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
                                        .description("메뉴 설명"),
                                fieldWithPath("menuOptions").type(JsonFieldType.ARRAY)
                                        .description("메뉴 옵션 리스트"),
                                fieldWithPath("menuOptions[].menuOptionName").type(JsonFieldType.STRING)
                                        .description("메뉴 옵션명"),
                                fieldWithPath("menuOptions[].menuOptionPrice").type(JsonFieldType.NUMBER)
                                        .description("메뉴 옵션 가격"),
                                fieldWithPath("menuOptions[].menuOptionDescription").type(JsonFieldType.STRING)
                                        .description("메뉴 옵션 설명")
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

    @DisplayName("메뉴 목록 조회 API")
    @Test
    void getMenus() throws Exception {

        long foodTruckId = 1L;

        MenuDto menu1 = MenuDto.builder()
                .menuId(1L)
                .menuName("달콤짭짤한 밥도둑 된장 삼겹살 구이")
                .description("동현 된장삼겹의 시그니쳐. 오직 된장 삼겹살 구이만!")
                .price(8900)
                .soldOut(false)
                .menuImageId(1L)
                .build();

        MenuDto menu2 = MenuDto.builder()
                .menuId(2L)
                .menuName("노른자 된장 삼겹살 덮밥")
                .description("감칠맛이 터져버린 한그릇 뚝딱 삼겹살 덮밥")
                .price(6900)
                .soldOut(false)
                .menuImageId(2L)
                .build();

        MenuResponse response = MenuResponse.builder()
                .hasNext(false)
                .menus(List.of(menu1, menu2))
                .build();

        given(menuQueryService.getMenus(anyLong(), anyLong()))
                .willReturn(response);

        mockMvc.perform(
                        get("/menu")
                                .param("foodTruckId", Long.toString(foodTruckId))
                                .param("lastMenuId", "")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("search-menu",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("foodTruckId").description("푸드트럭 식별키"),
                                parameterWithName("lastMenuId").description("마지막으로 조회된 메뉴 식별키")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT)
                                        .description("메뉴 목록 조회 결과"),
                                fieldWithPath("data.hasNext").type(JsonFieldType.BOOLEAN)
                                        .description("다음 페이지 존재 여부"),
                                fieldWithPath("data.menus").type(JsonFieldType.ARRAY)
                                        .description("메뉴 리스트"),
                                fieldWithPath("data.menus[].menuId").type(JsonFieldType.NUMBER)
                                        .description("메뉴 식별키"),
                                fieldWithPath("data.menus[].menuName").type(JsonFieldType.STRING)
                                        .description("메뉴명"),
                                fieldWithPath("data.menus[].price").type(JsonFieldType.NUMBER)
                                        .description("메뉴 가격"),
                                fieldWithPath("data.menus[].description").type(JsonFieldType.STRING)
                                        .description("메뉴 설명"),
                                fieldWithPath("data.menus[].soldOut").type(JsonFieldType.BOOLEAN)
                                        .description("품절 여부"),
                                fieldWithPath("data.menus[].menuImageId").type(JsonFieldType.NUMBER)
                                        .description("메뉴 이미지 식별키")
                        )
                ));
    }
}
