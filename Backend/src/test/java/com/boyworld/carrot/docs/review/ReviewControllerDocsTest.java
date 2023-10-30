package com.boyworld.carrot.docs.review;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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

import com.boyworld.carrot.api.controller.review.ReviewController;
import com.boyworld.carrot.api.controller.review.request.CommentRequest;
import com.boyworld.carrot.api.controller.review.request.ReviewRequest;
import com.boyworld.carrot.api.controller.review.response.FoodTruckReviewResponse;
import com.boyworld.carrot.api.controller.review.response.MyReviewResponse;
import com.boyworld.carrot.api.service.review.ReviewService;
import com.boyworld.carrot.api.service.review.dto.FoodTruckReviewDto;
import com.boyworld.carrot.api.service.review.dto.MyReviewDto;
import com.boyworld.carrot.api.service.review.dto.ReviewFoodTruckDto;
import com.boyworld.carrot.docs.RestDocsSupport;
import com.boyworld.carrot.domain.review.Review;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;

@WebMvcTest(ReviewControllerDocsTest.class)
public class ReviewControllerDocsTest extends RestDocsSupport {

    private final ReviewService reviewService = mock(ReviewService.class);

    @Override
    protected Object initController() {
        return new ReviewController(reviewService);
    }

    @DisplayName("리뷰 등록 API")
    @Test
    @WithMockUser(roles = "CLIENT")
    void createReview() throws Exception {

        ReviewRequest request = ReviewRequest.builder()
            .memberId(1L)
            .orderId(1L)
            .foodTruckId(1L)
            .grade(5)
            .content("너무 맛있어요~!")
            .build();

        Boolean result = true;

        given(reviewService.createReview(any(ReviewRequest.class)))
            .willReturn(result);

        mockMvc.perform(
                post("/review")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(
                document("create-review",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestFields(
                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("멤버 ID"),
                        fieldWithPath("orderId").type(JsonFieldType.NUMBER).description("주문 ID"),
                        fieldWithPath("foodTruckId").type(JsonFieldType.NUMBER)
                            .description("푸드트럭 ID"),
                        fieldWithPath("grade").type(JsonFieldType.NUMBER).description("평점 (최대 5점)"),
                        fieldWithPath("content").type(JsonFieldType.STRING).description("리뷰 내용")
                    ),
                    responseFields(
                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                            .description("코드"),
                        fieldWithPath("status").type(JsonFieldType.STRING)
                            .description("상태"),
                        fieldWithPath("message").type(JsonFieldType.STRING)
                            .description("메시지"),
                        fieldWithPath("data").type(JsonFieldType.BOOLEAN)
                            .description("작성 성공 여부")
                    )
                )
            );
    }

    @DisplayName("리뷰 답글 등록 API")
    @Test
    @WithMockUser(roles = "VENDOR")
    void createComment() throws Exception{

        CommentRequest request = CommentRequest.builder()
            .comment("리뷰 감사합니다.")
            .reviewId(1L)
            .build();

        Boolean result = true;

        given(reviewService.createComment(any(CommentRequest.class)))
            .willReturn(result);

        mockMvc.perform(
                post("/review/comment")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(
                document("create-comment",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestFields(
                        fieldWithPath("reviewId").type(JsonFieldType.NUMBER).description("리뷰 ID"),
                        fieldWithPath("comment").type(JsonFieldType.STRING).description("답글")
                    ),
                    responseFields(
                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                            .description("코드"),
                        fieldWithPath("status").type(JsonFieldType.STRING)
                            .description("상태"),
                        fieldWithPath("message").type(JsonFieldType.STRING)
                            .description("메시지"),
                        fieldWithPath("data").type(JsonFieldType.BOOLEAN)
                            .description("작성 성공 여부")
                    )
                )
            );
    }

    @DisplayName("내 리뷰 조회 API")
    @Test
    @WithMockUser(roles = "CLIENT")
    void getMyReview() throws Exception {
        List<MyReviewDto> myReviewDtoList = new ArrayList<>();
        myReviewDtoList.add(MyReviewDto.builder()
            .reviewId(1L)
            .grade(5)
            .content("여기 맛집이다!!")
            .createdDate(LocalDateTime.now())
            .reviewFoodTruckDto(ReviewFoodTruckDto.builder().foodTruckId(1L).foodTruckName("내가만든푸드트럭").build())
            .build());
        MyReviewResponse myReviewResponse = MyReviewResponse.builder()
            .myReviewDtoList(myReviewDtoList)
            .build();

        // ReviewService의 getMyReview 메서드가 호출될 때 가짜 응답을 반환하도록 설정
        given(reviewService.getMyReview(anyString()))
            .willReturn(myReviewResponse);

        // API 요청 및 응답 검증
        mockMvc.perform(
            get("/review")
                .header("Authentication", "authentication")
        ).andDo(print())
        .andExpect(status().isFound())
        .andDo(
            document("get-my-review",
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
                    fieldWithPath("data.myReviewDtoList").type(JsonFieldType.ARRAY)
                        .description("내가 작성한 리뷰 정보 목록"),
                    fieldWithPath("data.myReviewDtoList[].reviewId").type(JsonFieldType.NUMBER)
                        .description("리뷰 ID"),
                    fieldWithPath("data.myReviewDtoList[].reviewFoodTruckDto").type(JsonFieldType.OBJECT)
                        .description("푸드 트럭 정보"),
                    fieldWithPath("data.myReviewDtoList[].reviewFoodTruckDto.foodTruckId").type(JsonFieldType.NUMBER)
                        .description("푸드 트럭 ID"),
                    fieldWithPath("data.myReviewDtoList[].reviewFoodTruckDto.foodTruckName").type(JsonFieldType.STRING)
                        .description("푸드 트럭 이름"),
                    fieldWithPath("data.myReviewDtoList[].grade").type(JsonFieldType.NUMBER)
                        .description("리뷰 평점"),
                    fieldWithPath("data.myReviewDtoList[].createdDate").type(JsonFieldType.ARRAY)
                        .description("리뷰 작성일"),
                    fieldWithPath("data.myReviewDtoList[].content").type(JsonFieldType.STRING)
                        .description("리뷰 내용")
                ))
        );
    }

    @DisplayName("푸드트럭 리뷰 조회 API")
    @Test
    void getFoodTruckReview() throws Exception {
        List<FoodTruckReviewDto> list = new ArrayList<>();
        list.add(FoodTruckReviewDto.builder().reviewId(1L).nickname("동혀니").grade(5).content("된장이 맛있어요").build());

        FoodTruckReviewResponse foodTruckReviewResponse = FoodTruckReviewResponse.builder()
            .foodTruckReviewDtoList(list)
            .build();

        given(reviewService.getFoodTruckReview(any(Long.class))).willReturn(foodTruckReviewResponse);

        mockMvc.perform(
                get("/review/1")
            ).andDo(print())
            .andExpect(status().isFound())
            .andDo(
                document("get-food-truck-review",
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
                        fieldWithPath("data.foodTruckReviewDtoList").type(JsonFieldType.ARRAY)
                            .description("푸드트럭 리뷰 정보 목록"),
                        fieldWithPath("data.foodTruckReviewDtoList[].reviewId").type(JsonFieldType.NUMBER)
                            .description("리뷰 ID"),
                        fieldWithPath("data.foodTruckReviewDtoList[].nickname").type(JsonFieldType.STRING)
                            .description("리뷰 작성자 닉네임"),
                        fieldWithPath("data.foodTruckReviewDtoList[].grade").type(JsonFieldType.NUMBER)
                            .description("리뷰 평점"),
                        fieldWithPath("data.foodTruckReviewDtoList[].content").type(JsonFieldType.STRING)
                            .description("리뷰 내용"),
                        fieldWithPath("data.averageGrade").type(JsonFieldType.NUMBER)
                            .description("평균 리뷰")
                    ))
            );

    }

}
