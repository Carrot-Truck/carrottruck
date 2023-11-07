package com.boyworld.carrot.docs.category;

import com.boyworld.carrot.api.controller.foodtruck.CategoryController;
import com.boyworld.carrot.api.controller.foodtruck.response.*;
import com.boyworld.carrot.api.service.foodtruck.CategoryQueryService;
import com.boyworld.carrot.api.service.foodtruck.CategoryService;
import com.boyworld.carrot.api.service.foodtruck.FoodTruckQueryService;
import com.boyworld.carrot.api.service.foodtruck.FoodTruckService;
import com.boyworld.carrot.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryControllerDocsTest.class)
public class CategoryControllerDocsTest extends RestDocsSupport {

    private final CategoryService categoryService = mock(CategoryService.class);
    private final CategoryQueryService categoryQueryService = mock(CategoryQueryService.class);

    @Override
    protected Object initController() {
        return new CategoryController(categoryService, categoryQueryService);
    }

    @DisplayName("카테고리 목록 조회 API")
    @Test
    @WithMockUser(roles = {"CLIENT", "VENDOR"})
    void getFoodTruckOverviews() throws Exception {

        CategoryDetailResponse category1 = CategoryDetailResponse.builder()
                .categoryId(1L)
                .categoryName("한식/도시락")
                .build();

        CategoryDetailResponse category2 = CategoryDetailResponse.builder()
                .categoryId(2L)
                .categoryName("분식")
                .build();

        CategoryResponse response = CategoryResponse.builder()
                .categories(List.of(category1, category2))
                .build();

        given(categoryQueryService.getCategories())
                .willReturn(response);

        mockMvc.perform(
                        get("/category")
                                .header("Authentication", "authentication")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("search-category",
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT)
                                        .description("응답 데이터"),
                                fieldWithPath("data.categoryCount").type(JsonFieldType.NUMBER)
                                        .description("카테고리 목록 개수"),
                                fieldWithPath("data.categories").type(JsonFieldType.ARRAY)
                                        .description("카테고리 목록"),
                                fieldWithPath("data.categories[].categoryId").type(JsonFieldType.NUMBER)
                                        .description("카테고리 식별키"),
                                fieldWithPath("data.categories[].categoryName").type(JsonFieldType.STRING)
                                        .description("카테고리 이름")
                        )
                ));
    }
}
