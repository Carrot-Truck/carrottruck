package com.boyworld.carrot.docs.menu;

import com.boyworld.carrot.api.controller.menu.MenuController;
import com.boyworld.carrot.api.controller.menu.request.CreateMenuOptionRequest;
import com.boyworld.carrot.api.controller.menu.request.CreateMenuRequest;
import com.boyworld.carrot.api.controller.menu.request.EditMenuRequest;
import com.boyworld.carrot.api.controller.menu.response.CreateMenuResponse;
import com.boyworld.carrot.api.controller.menu.response.MenuDetailResponse;
import com.boyworld.carrot.api.controller.menu.response.MenuOptionResponse;
import com.boyworld.carrot.api.controller.menu.response.MenuResponse;
import com.boyworld.carrot.api.service.menu.MenuQueryService;
import com.boyworld.carrot.api.service.menu.MenuService;
import com.boyworld.carrot.api.service.menu.dto.CreateMenuDto;
import com.boyworld.carrot.api.service.menu.dto.CreateMenuOptionDto;
import com.boyworld.carrot.api.service.menu.dto.EditMenuDto;
import com.boyworld.carrot.api.service.menu.dto.MenuDto;
import com.boyworld.carrot.docs.RestDocsSupport;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
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
                .menuOptionSize(2)
                .build();

        MockMultipartFile file = new MockMultipartFile("file", "image.jpg", MediaType.IMAGE_JPEG_VALUE, "image data".getBytes());

        String jsonRequest = objectMapper.writeValueAsString(request);
        MockMultipartFile jsonRequestPart = new MockMultipartFile("request", "request.json", APPLICATION_JSON_VALUE, jsonRequest.getBytes(UTF_8));

        given(menuService.createMenu(any(CreateMenuDto.class), any(MultipartFile.class), anyString()))
                .willReturn(response);

        mockMvc.perform(
                        multipart("/menu")
                                .file(file)
                                .file(jsonRequestPart)
                                .header("Authentication", "authentication")
                                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("create-menu",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParts(
                                partWithName("file").description("메뉴 이미지"),
                                partWithName("request").description("메뉴 정보")
                        ),
                        requestPartFields("request",
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
                                        .description("메뉴 설명"),
                                fieldWithPath("data.menuOptionSize").type(JsonFieldType.NUMBER)
                                        .description("저장된 메뉴 옵션 개수")
                        )
                ));
    }

    @DisplayName("메뉴 목록 조회 API")
    @Test
    @WithMockUser(roles = {"VENDOR", "CLIENT"})
    void getMenus() throws Exception {

        long foodTruckId = 1L;

        MenuDto menu1 = MenuDto.builder()
                .menuId(1L)
                .menuName("달콤짭짤한 밥도둑 된장 삼겹살 구이")
                .menuDescription("동현 된장삼겹의 시그니쳐. 오직 된장 삼겹살 구이만!")
                .menuPrice(8900)
                .menuSoldOut(false)
                .menuImageUrl("imageUrl")
                .build();

        MenuDto menu2 = MenuDto.builder()
                .menuId(2L)
                .menuName("노른자 된장 삼겹살 덮밥")
                .menuDescription("감칠맛이 터져버린 한그릇 뚝딱 삼겹살 덮밥")
                .menuPrice(6900)
                .menuSoldOut(false)
                .menuImageUrl("imageUrl")
                .build();

        MenuResponse response = MenuResponse.builder()
                .menus(List.of(menu1, menu2))
                .build();

        given(menuQueryService.getMenus(anyLong()))
                .willReturn(response);

        mockMvc.perform(
                        get("/menu")
                                .header("Authentication", "authentication")
                                .param("foodTruckId", Long.toString(foodTruckId))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("search-menu",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("foodTruckId").description("푸드트럭 식별키")
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
                                fieldWithPath("data.menuCount").type(JsonFieldType.NUMBER)
                                        .description("메뉴 개수"),
                                fieldWithPath("data.menus").type(JsonFieldType.ARRAY)
                                        .description("메뉴 리스트"),
                                fieldWithPath("data.menus[].menuId").type(JsonFieldType.NUMBER)
                                        .description("메뉴 식별키"),
                                fieldWithPath("data.menus[].menuName").type(JsonFieldType.STRING)
                                        .description("메뉴명"),
                                fieldWithPath("data.menus[].menuPrice").type(JsonFieldType.NUMBER)
                                        .description("메뉴 가격"),
                                fieldWithPath("data.menus[].menuDescription").type(JsonFieldType.STRING)
                                        .description("메뉴 설명"),
                                fieldWithPath("data.menus[].menuSoldOut").type(JsonFieldType.BOOLEAN)
                                        .description("품절 여부"),
                                fieldWithPath("data.menus[].menuImageUrl").type(JsonFieldType.STRING)
                                        .description("메뉴 이미지 저장 경로")
                        )
                ));
    }

    @DisplayName("메뉴 상세 조회 API")
    @Test
    @WithMockUser(roles = {"VENDOR", "CLIENT"})
    void getMenu() throws Exception {

        long menuId = 1L;
        MenuOptionResponse option1 = MenuOptionResponse.builder()
                .menuOptionId(1L)
                .menuOptionName("옵션1")
                .menuOptionPrice(500)
                .menuOptionDescription("설명1")
                .menuOptionSoldOut(false)
                .build();

        MenuOptionResponse option2 = MenuOptionResponse.builder()
                .menuOptionId(2L)
                .menuOptionName("옵션2")
                .menuOptionPrice(300)
                .menuOptionDescription("설명2")
                .menuOptionSoldOut(false)
                .build();

        MenuDto menu = MenuDto.builder()
                .menuId(2L)
                .menuName("노른자 된장 삼겹살 덮밥")
                .menuDescription("감칠맛이 터져버린 한그릇 뚝딱 삼겹살 덮밥")
                .menuPrice(6900)
                .menuSoldOut(false)
                .menuImageUrl("imageUrl")
                .build();

        MenuDetailResponse response = MenuDetailResponse.builder()
                .menu(menu)
                .menuOptions(List.of(option1, option2))
                .build();

        given(menuQueryService.getMenu(anyLong()))
                .willReturn(response);

        mockMvc.perform(
                        get("/menu/{menuId}", menuId)
                                .header("Authentication", "authentication")
                                .param("menuId", "1")
                                .queryParam("foodTruckId", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("search-menu-detail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("menuId").description("메뉴 식별키")
                        ),
                        queryParameters(
                                parameterWithName("foodTruckId").description("푸드트럭 식별키")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT)
                                        .description("메뉴 상세 조회 결과"),
                                fieldWithPath("data.menu").type(JsonFieldType.OBJECT)
                                        .description("메뉴 상세 정보"),
                                fieldWithPath("data.menu.menuId").type(JsonFieldType.NUMBER)
                                        .description("메뉴 식별키"),
                                fieldWithPath("data.menu.menuName").type(JsonFieldType.STRING)
                                        .description("메뉴명"),
                                fieldWithPath("data.menu.menuPrice").type(JsonFieldType.NUMBER)
                                        .description("메뉴 가격"),
                                fieldWithPath("data.menu.menuDescription").type(JsonFieldType.STRING)
                                        .description("메뉴 설명"),
                                fieldWithPath("data.menu.menuSoldOut").type(JsonFieldType.BOOLEAN)
                                        .description("품절 여부"),
                                fieldWithPath("data.menu.menuImageUrl").type(JsonFieldType.STRING)
                                        .description("메뉴 이미지 저장 경로"),
                                fieldWithPath("data.menuOptionCount").type(JsonFieldType.NUMBER)
                                        .description("메뉴 옵션 개수"),
                                fieldWithPath("data.menuOptions").type(JsonFieldType.ARRAY)
                                        .description("메뉴 옵션 리스트"),
                                fieldWithPath("data.menuOptions[].menuOptionId").type(JsonFieldType.NUMBER)
                                        .description("메뉴 옵션 식별키"),
                                fieldWithPath("data.menuOptions[].menuOptionName").type(JsonFieldType.STRING)
                                        .description("메뉴 옵션명"),
                                fieldWithPath("data.menuOptions[].menuOptionPrice").type(JsonFieldType.NUMBER)
                                        .description("메뉴 옵션 가격"),
                                fieldWithPath("data.menuOptions[].menuOptionDescription").type(JsonFieldType.STRING)
                                        .description("메뉴 옵션 설명"),
                                fieldWithPath("data.menuOptions[].menuOptionSoldOut").type(JsonFieldType.BOOLEAN)
                                        .description("품절 여부")
                        )
                ));
    }

    @DisplayName("메뉴 수정 API")
    @Test
    @WithMockUser(roles = "VENDOR")
    void editMenu() throws Exception {
        EditMenuRequest request = EditMenuRequest.builder()
                .menuName("달콤짭짤한 밥도둑 된장 삼겹살 구이")
                .menuPrice(8900)
                .menuDescription("동현 된장삼겹의 시그니쳐. 오직 된장 삼겹살 구이만!")
                .build();

        Long menuId = 1L;

        MockMultipartFile file = new MockMultipartFile("file", "image.jpg", MediaType.IMAGE_JPEG_VALUE, "image data".getBytes());

        String jsonRequest = objectMapper.writeValueAsString(request);
        MockMultipartFile jsonRequestPart = new MockMultipartFile("request", "request.json", APPLICATION_JSON_VALUE, jsonRequest.getBytes(UTF_8));

        given(menuService.editMenu(any(EditMenuDto.class), any(MultipartFile.class), anyString()))
                .willReturn(menuId);

        // multipart 는 기본적으로 POST 요청을 위한 처리로만 사용되고 있으므로 아래와 같이 Override 해서 만들어줘야함
        MockMultipartHttpServletRequestBuilder builder =
                RestDocumentationRequestBuilders.
                        multipart("/menu/{menuId}", menuId);

        builder.with(new RequestPostProcessor() {
            @Override
            public @NotNull MockHttpServletRequest postProcessRequest(@NotNull MockHttpServletRequest request) {
                request.setMethod("PATCH");
                return request;
            }
        });

        mockMvc.perform(
                        builder
                                .file(file)
                                .file(jsonRequestPart)
                                .header("Authentication", "authentication")
                                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("edit-menu",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("menuId").description("메뉴 식별키")
                        ),
                        requestParts(
                                partWithName("file").description("푸드트럭 이미지"),
                                partWithName("request").description("푸드트럭 정보")
                        ),
                        requestPartFields("request",
                                fieldWithPath("menuName").type(JsonFieldType.STRING)
                                        .description("메뉴 이름"),
                                fieldWithPath("menuPrice").type(JsonFieldType.NUMBER)
                                        .description("메뉴 가격"),
                                fieldWithPath("menuDescription").type(JsonFieldType.STRING)
                                        .description("메뉴 설명")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER)
                                        .description("수정된 메뉴 식별키")
                        )
                ));
    }

    @DisplayName("메뉴 삭제 API")
    @Test
    @WithMockUser(roles = "VENDOR")
    void deleteMenu() throws Exception {
        Long menuId = 1L;

        given(menuService.deleteMenu(anyLong(), anyString()))
                .willReturn(menuId);

        mockMvc.perform(
                        delete("/menu/{menuId}", menuId)
                                .header("Authentication", "authentication")
                )
                .andDo(print())
                .andExpect(status().isFound())
                .andDo(document("delete-menu",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("menuId").description("메뉴 식별키")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER)
                                        .description("삭제된 메뉴 식별키")
                        )
                ));
    }

    @DisplayName("메뉴 옵션 등록")
    @Test
    @WithMockUser(roles = "VENDOR")
    void createMenuOption() throws Exception {

        CreateMenuOptionRequest request = CreateMenuOptionRequest.builder()
                .menuOptionName("옵션1")
                .menuOptionPrice(500)
                .menuOptionDescription("설명1")
                .build();

        MenuOptionResponse response = MenuOptionResponse.builder()
                .menuOptionId(1L)
                .menuOptionName("옵션1")
                .menuOptionPrice(500)
                .menuOptionDescription("설명1")
                .menuOptionSoldOut(false)
                .build();

        given(menuService.createMenuOption(any(CreateMenuOptionDto.class), anyString(), anyLong()))
                .willReturn(response);

        mockMvc.perform(
                        post("/menu/{menuId}/option", 1L)
                                .header("Authentication", "authentication")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("create-menu-option",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("menuOptionName").type(JsonFieldType.STRING)
                                        .description("메뉴 옵션명"),
                                fieldWithPath("menuOptionPrice").type(JsonFieldType.NUMBER)
                                        .description("메뉴 옵션 가격"),
                                fieldWithPath("menuOptionDescription").type(JsonFieldType.STRING)
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
                                fieldWithPath("data.menuOptionId").type(JsonFieldType.NUMBER)
                                        .description("메뉴 옵션 식별키"),
                                fieldWithPath("data.menuOptionName").type(JsonFieldType.STRING)
                                        .description("메뉴 옵션명"),
                                fieldWithPath("data.menuOptionPrice").type(JsonFieldType.NUMBER)
                                        .description("메뉴 옵션 가격"),
                                fieldWithPath("data.menuOptionDescription").type(JsonFieldType.STRING)
                                        .description("메뉴 옵션 설명"),
                                fieldWithPath("data.menuOptionSoldOut").type(JsonFieldType.BOOLEAN)
                                        .description("품절 여부")
                        )
                ));
    }

    @DisplayName("메뉴 옵션 삭제 API")
    @Test
    @WithMockUser(roles = "VENDOR")
    void deleteMenuOption() throws Exception {

        Long deleteId = 1L;

        given(menuService.deleteMenuOption(anyLong(), anyString()))
                .willReturn(deleteId);

        mockMvc.perform(
                        delete("/menu/option/{menuOptionId}", deleteId)
                                .header("Authentication", "authentication")
                )
                .andDo(print())
                .andExpect(status().isFound())
                .andDo(document("delete-menu-option",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("menuOptionId").description("메뉴 옵션 식별키")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER)
                                        .description("삭제된 메뉴 옵션 식별키")
                        )
                ));
    }
}
