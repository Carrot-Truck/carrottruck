package com.boyworld.carrot.docs.foodtruck;

import com.boyworld.carrot.api.controller.foodtruck.FoodTruckController;
import com.boyworld.carrot.api.controller.foodtruck.request.CreateFoodTruckRequest;
import com.boyworld.carrot.api.controller.foodtruck.request.UpdateFoodTruckRequest;
import com.boyworld.carrot.api.controller.foodtruck.response.FoodTruckDetailResponse;
import com.boyworld.carrot.api.controller.foodtruck.response.FoodTruckItem;
import com.boyworld.carrot.api.controller.foodtruck.response.FoodTruckMarkerResponse;
import com.boyworld.carrot.api.controller.foodtruck.response.FoodTruckResponse;
import com.boyworld.carrot.api.service.foodtruck.FoodTruckQueryService;
import com.boyworld.carrot.api.service.foodtruck.FoodTruckService;
import com.boyworld.carrot.api.service.foodtruck.dto.CreateFoodTruckDto;
import com.boyworld.carrot.api.service.foodtruck.dto.FoodTruckDetailDto;
import com.boyworld.carrot.api.service.foodtruck.dto.FoodTruckMarkerItem;
import com.boyworld.carrot.api.service.foodtruck.dto.UpdateFoodTruckDto;
import com.boyworld.carrot.api.service.menu.dto.MenuDto;
import com.boyworld.carrot.api.service.review.dto.FoodTruckReviewDto;
import com.boyworld.carrot.api.service.schedule.dto.ScheduleDto;
import com.boyworld.carrot.docs.RestDocsSupport;
import com.boyworld.carrot.domain.foodtruck.repository.dto.SearchCondition;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FoodTruckControllerDocsTest.class)
public class FoodTruckControllerDocsTest extends RestDocsSupport {

    private final FoodTruckService foodTruckService = mock(FoodTruckService.class);
    private final FoodTruckQueryService foodTruckQueryService = mock(FoodTruckQueryService.class);

    @Override
    protected Object initController() {
        return new FoodTruckController(foodTruckService, foodTruckQueryService);
    }

    @DisplayName("푸드트럭 등록 API")
    @Test
    @WithMockUser(roles = "VENDOR")
    void createFoodTruck() throws Exception {
        CreateFoodTruckRequest request = CreateFoodTruckRequest.builder()
                .categoryId(1L)
                .foodTruckName("동현 된장삼겹")
                .phoneNumber("010-1234-5678")
                .content("된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭")
                .originInfo("돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)")
                .prepareTime(40)
                .waitLimits(10)
                .build();

        MockMultipartFile file = new MockMultipartFile("file", "image.jpg", MediaType.IMAGE_JPEG_VALUE, "image data".getBytes());

        String jsonRequest = objectMapper.writeValueAsString(request);
        MockMultipartFile jsonRequestPart = new MockMultipartFile("request", "request.json", APPLICATION_JSON_VALUE, jsonRequest.getBytes(UTF_8));

        given(foodTruckService.createFoodTruck(any(CreateFoodTruckDto.class), anyString(), any(MultipartFile.class)))
                .willReturn(1L);

        mockMvc.perform(
                        multipart("/food-truck/vendor")
                                .file(file)
                                .file(jsonRequestPart)
                                .header("Authentication", "authentication")
                                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("create-food-truck",
                        preprocessResponse(prettyPrint()),
                        requestParts(
                                partWithName("file").description("푸드트럭 이미지"),
                                partWithName("request").description("푸드트럭 정보")
                        ),
                        requestPartFields("request",
                                fieldWithPath("categoryId").type(JsonFieldType.NUMBER)
                                        .description("카테고리 식별키"),
                                fieldWithPath("foodTruckName").type(JsonFieldType.STRING)
                                        .description("푸드트럭 이름"),
                                fieldWithPath("phoneNumber").type(JsonFieldType.STRING)
                                        .description("연락처"),
                                fieldWithPath("content").type(JsonFieldType.STRING)
                                        .description("가게 소개"),
                                fieldWithPath("originInfo").type(JsonFieldType.STRING)
                                        .description("원산지 정보"),
                                fieldWithPath("prepareTime").type(JsonFieldType.NUMBER)
                                        .description("예상 준비 시간"),
                                fieldWithPath("waitLimits").type(JsonFieldType.NUMBER)
                                        .description("최대 주문 대기 건수")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER)
                                        .description("저장된 푸드트럭 식별키")
                        )
                ));
    }

    @DisplayName("푸드트럭 지도 검색 API")
    @Test
    void getFoodTruckMarkers() throws Exception {
        FoodTruckMarkerItem info1 = FoodTruckMarkerItem.builder()
                .categoryId(1L)
                .foodTruckId(1L)
                .latitude("37.5665")
                .longitude("126.9780")
                .isOpen(true)
                .build();

        FoodTruckMarkerItem info2 = FoodTruckMarkerItem.builder()
                .categoryId(2L)
                .foodTruckId(2L)
                .latitude("35.1595")
                .longitude("126.8526")
                .isOpen(false)
                .build();

        FoodTruckMarkerResponse response = FoodTruckMarkerResponse.builder()
                .markerCount(2)
                .markerItems(List.of(info1, info2))
                .build();

        given(foodTruckQueryService.getFoodTruckMarkers(any(SearchCondition.class)))
                .willReturn(response);

        mockMvc.perform(
                        get("/food-truck/marker")
                                .param("categoryId", "")
                                .param("keyword", "")
                                .param("latitude", "")
                                .param("longitude", "")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("search-food-truck-marker",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("categoryId")
                                        .description("카테고리 식별키"),
                                parameterWithName("keyword")
                                        .description("푸드트럭/메뉴 이름"),
                                parameterWithName("latitude")
                                        .description("현재 사용자의 위도"),
                                parameterWithName("longitude")
                                        .description("현재 사용자의 경도")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data.markerCount").type(JsonFieldType.NUMBER)
                                        .description("마커 개수"),
                                fieldWithPath("data.markerItems").type(JsonFieldType.ARRAY)
                                        .description("푸드트럭 검색 결과 목록"),
                                fieldWithPath("data.markerItems[].categoryId").type(JsonFieldType.NUMBER)
                                        .description("카테고리 식별키"),
                                fieldWithPath("data.markerItems[].foodTruckId").type(JsonFieldType.NUMBER)
                                        .description("푸드트럭 식별키"),
                                fieldWithPath("data.markerItems[].latitude").type(JsonFieldType.STRING)
                                        .description("위도"),
                                fieldWithPath("data.markerItems[].longitude").type(JsonFieldType.STRING)
                                        .description("경도"),
                                fieldWithPath("data.markerItems[].isOpen").type(JsonFieldType.BOOLEAN)
                                        .description("영업여부")
                        )
                ));
    }

    @DisplayName("푸드트럭 검색 결과 목록 조회 API")
    @Test
    @WithMockUser(roles = "CLIENT")
    void getFoodTrucks() throws Exception {
        FoodTruckItem item1 = FoodTruckItem.builder()
                .categoryId(1L)
                .foodTruckId(1L)
                .foodTruckName("동현 된장삼겹")
                .isOpen(false)
                .isLiked(true)
                .prepareTime(30)
                .grade(4.5)
                .reviewCount(1324)
                .distance(123)
                .address("광주 광산구 장덕로 5번길 16")
                .foodTruckImageId(1L)
                .isNew(true)
                .build();

        FoodTruckItem item2 = FoodTruckItem.builder()
                .categoryId(2L)
                .foodTruckId(2L)
                .foodTruckName("팔천순대")
                .isOpen(true)
                .isLiked(false)
                .prepareTime(20)
                .grade(4.0)
                .reviewCount(1324)
                .distance(100)
                .address("수완자이아파트정문")
                .foodTruckImageId(2L)
                .isNew(false)
                .build();
        List<FoodTruckItem> items = List.of(item1, item2);

        FoodTruckResponse response = FoodTruckResponse.builder()
                .hasNext(false)
                .foodTruckItems(items)
                .build();

        given(foodTruckQueryService.getFoodTrucks(any(SearchCondition.class), anyString(), anyString()))
                .willReturn(response);

        mockMvc.perform(
                        get("/food-truck")
                                .header("Authentication", "authentication")
                                .param("categoryId", "")
                                .param("keyword", "")
                                .param("latitude", "")
                                .param("longitude", "")
                                .param("lastFoodTruckId", "")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("search-food-truck-list",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("categoryId")
                                        .description("카테고리 식별키"),
                                parameterWithName("keyword")
                                        .description("푸드트럭/메뉴 이름"),
                                parameterWithName("latitude")
                                        .description("현재 사용자의 위도"),
                                parameterWithName("longitude")
                                        .description("현재 사용자의 경도"),
                                parameterWithName("lastFoodTruckId")
                                        .description("마지막으로 조회된 푸드트럭 식별키")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data.hasNext").type(JsonFieldType.BOOLEAN)
                                        .description("다음 페이지 존재 여부"),
                                fieldWithPath("data.foodTruckItems").type(JsonFieldType.ARRAY)
                                        .description("푸드트럭 검색 결과 목록"),
                                fieldWithPath("data.foodTruckItems[].categoryId").type(JsonFieldType.NUMBER)
                                        .description("카테고리 식별키"),
                                fieldWithPath("data.foodTruckItems[].foodTruckId").type(JsonFieldType.NUMBER)
                                        .description("푸드트럭 식별키"),
                                fieldWithPath("data.foodTruckItems[].foodTruckName").type(JsonFieldType.STRING)
                                        .description("푸드트럭 이름"),
                                fieldWithPath("data.foodTruckItems[].isOpen").type(JsonFieldType.BOOLEAN)
                                        .description("영업 상태"),
                                fieldWithPath("data.foodTruckItems[].isLiked").type(JsonFieldType.BOOLEAN)
                                        .description("찜 여부"),
                                fieldWithPath("data.foodTruckItems[].prepareTime").type(JsonFieldType.NUMBER)
                                        .description("예상 준비 시간"),
                                fieldWithPath("data.foodTruckItems[].grade").type(JsonFieldType.NUMBER)
                                        .description("평점"),
                                fieldWithPath("data.foodTruckItems[].reviewCount").type(JsonFieldType.NUMBER)
                                        .description("리뷰 개수"),
                                fieldWithPath("data.foodTruckItems[].distance").type(JsonFieldType.NUMBER)
                                        .description("현재 사용자와의 거리"),
                                fieldWithPath("data.foodTruckItems[].address").type(JsonFieldType.STRING)
                                        .description("푸드트럭 주소"),
                                fieldWithPath("data.foodTruckItems[].foodTruckImageId").type(JsonFieldType.NUMBER)
                                        .description("푸드트럭 이미지 식별키"),
                                fieldWithPath("data.foodTruckItems[].isNew").type(JsonFieldType.BOOLEAN)
                                        .description("신규 등록 여부")
                        )
                ));
    }

    @DisplayName("푸드트럭 상세조회 API")
    @Test
    @WithMockUser(roles = {"CLIENT", "VENDOR"})
    void getFoodTruck() throws Exception {
        FoodTruckDetailDto foodTruckDetail = FoodTruckDetailDto.builder()
                .foodTruckId(1L)
                .foodTruckName("동현 된장삼겹")
                .phoneNumber("010-1234-5678")
                .content("된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭")
                .originInfo("돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)")
                .isOpen(false)
                .isLiked(true)
                .prepareTime(30)
                .grade(4.5)
                .reviewCount(1324)
                .distance(123)
                .address("광주 광산구 장덕로 5번길 16")
                .foodTruckImageId(1L)
                .isNew(true)
                .vendorName("김동현")
                .tradeName("동현 된장삼겹")
                .businessNumber("123-45-23523")
                .build();

        MenuDto menu1 = MenuDto.builder()
                .menuId(1L)
                .menuName("달콤짭짤한 밥도둑 된장 삼겹살 구이")
                .description("동현 된장삼겹의 시그니쳐. 오직 된장 삼겹살 구이만!")
                .price(8900)
                .soldOut(false)
                .build();

        MenuDto menu2 = MenuDto.builder()
                .menuId(2L)
                .menuName("노른자 된장 삼겹살 덮밥")
                .description("감칠맛이 터져버린 한그릇 뚝딱 삼겹살 덮밥")
                .price(6900)
                .soldOut(false)
                .build();

        ScheduleDto schedule1 = ScheduleDto.builder()
                .scheduleId(1L)
                .address("광주 광산구 장덕로5번길 16")
                .days("월요일")
                .startTime("17:00")
                .endTime("01:00")
                .build();

        ScheduleDto schedule2 = ScheduleDto.builder()
                .scheduleId(2L)
                .address("광주 광산구 장덕로5번길 16")
                .days("화요일")
                .startTime("17:00")
                .endTime("01:00")
                .build();

        ScheduleDto schedule3 = ScheduleDto.builder()
                .scheduleId(3L)
                .address("")
                .days("수요일")
                .startTime("")
                .endTime("")
                .build();

        FoodTruckReviewDto review1 = FoodTruckReviewDto.builder()
                .reviewId(1L)
                .nickname("아닌데?소대장")
                .grade(4)
                .content("정말 맛있게 먹었어요")
                .build();

        FoodTruckReviewDto review2 = FoodTruckReviewDto.builder()
                .reviewId(2L)
                .nickname("어서와")
                .grade(5)
                .content("안주로 최고에요!! 너무 맛있어서 숙취에 시달릴만큼 많이 마셨어요")
                .build();

        FoodTruckDetailResponse response = FoodTruckDetailResponse.builder()
                .foodTruckDetail(foodTruckDetail)
                .menus(List.of(menu1, menu2))
                .schedules(List.of(schedule1, schedule2, schedule3))
                .reviews(List.of(review1, review2))
                .build();

        given(foodTruckQueryService.getFoodTruck(anyLong(), anyString()))
                .willReturn(response);

        mockMvc.perform(
                        get("/food-truck/{foodTruckId}", foodTruckDetail.getFoodTruckId())
                                .header("Authentication", "authentication")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("search-food-truck-detail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
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
                                        .description("푸드트럭 상세 조회 결과"),
                                fieldWithPath("data.foodTruckDetail").type(JsonFieldType.OBJECT)
                                        .description("푸드트럭 상세 정보"),
                                fieldWithPath("data.foodTruckDetail.foodTruckId").type(JsonFieldType.NUMBER)
                                        .description("푸드트럭 식별키"),
                                fieldWithPath("data.foodTruckDetail.foodTruckName").type(JsonFieldType.STRING)
                                        .description("푸드트럭 이름"),
                                fieldWithPath("data.foodTruckDetail.phoneNumber").type(JsonFieldType.STRING)
                                        .description("연락처"),
                                fieldWithPath("data.foodTruckDetail.content").type(JsonFieldType.STRING)
                                        .description("가게 소개"),
                                fieldWithPath("data.foodTruckDetail.originInfo").type(JsonFieldType.STRING)
                                        .description("원산지 정보"),
                                fieldWithPath("data.foodTruckDetail.isOpen").type(JsonFieldType.BOOLEAN)
                                        .description("영업 상태"),
                                fieldWithPath("data.foodTruckDetail.isLiked").type(JsonFieldType.BOOLEAN)
                                        .description("찜 여부"),
                                fieldWithPath("data.foodTruckDetail.prepareTime").type(JsonFieldType.NUMBER)
                                        .description("예상 준비 시간"),
                                fieldWithPath("data.foodTruckDetail.grade").type(JsonFieldType.NUMBER)
                                        .description("평점"),
                                fieldWithPath("data.foodTruckDetail.reviewCount").type(JsonFieldType.NUMBER)
                                        .description("리뷰 개수"),
                                fieldWithPath("data.foodTruckDetail.distance").type(JsonFieldType.NUMBER)
                                        .description("현재 사용자와의 거리"),
                                fieldWithPath("data.foodTruckDetail.address").type(JsonFieldType.STRING)
                                        .description("푸드트럭 주소"),
                                fieldWithPath("data.foodTruckDetail.foodTruckImageId").type(JsonFieldType.NUMBER)
                                        .description("푸드트럭 이미지 식별키"),
                                fieldWithPath("data.foodTruckDetail.isNew").type(JsonFieldType.BOOLEAN)
                                        .description("신규 등록 여부"),
                                fieldWithPath("data.foodTruckDetail.vendorName").type(JsonFieldType.STRING)
                                        .description("대표자명"),
                                fieldWithPath("data.foodTruckDetail.tradeName").type(JsonFieldType.STRING)
                                        .description("상호명"),
                                fieldWithPath("data.foodTruckDetail.businessNumber").type(JsonFieldType.STRING)
                                        .description("사업자 등록번호"),
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
                                fieldWithPath("data.schedules").type(JsonFieldType.ARRAY)
                                        .description("운영시간 리스트"),
                                fieldWithPath("data.schedules[].scheduleId").type(JsonFieldType.NUMBER)
                                        .description("운영시간 식별키"),
                                fieldWithPath("data.schedules[].address").type(JsonFieldType.STRING)
                                        .description("주소"),
                                fieldWithPath("data.schedules[].days").type(JsonFieldType.STRING)
                                        .description("요일"),
                                fieldWithPath("data.schedules[].startTime").type(JsonFieldType.STRING)
                                        .description("시작 시간"),
                                fieldWithPath("data.schedules[].endTime").type(JsonFieldType.STRING)
                                        .description("종료 시간"),
                                fieldWithPath("data.reviews").type(JsonFieldType.ARRAY)
                                        .description("리뷰 리스트"),
                                fieldWithPath("data.reviews[].reviewId").type(JsonFieldType.NUMBER)
                                        .description("리뷰 식별키"),
                                fieldWithPath("data.reviews[].nickname").type(JsonFieldType.STRING)
                                        .description("작성자 닉네임"),
                                fieldWithPath("data.reviews[].grade").type(JsonFieldType.NUMBER)
                                        .description("만족도"),
                                fieldWithPath("data.reviews[].content").type(JsonFieldType.STRING)
                                        .description("리뷰 내용")
                        )
                ));
    }

    @DisplayName("푸드트럭 수정 API")
    @Test
    @WithMockUser(roles = "VENDOR")
    void editFoodTruck() throws Exception {
        UpdateFoodTruckRequest request = UpdateFoodTruckRequest.builder()
                .categoryId(1L)
                .foodTruckName("동현 된장삼겹")
                .phoneNumber("010-4321-8756")
                .content("된장 삼겹 구이 & 삼겹 덮밥 전문 푸드트럭")
                .originInfo("돼지고기(국산), 고축가루(국산), 참깨(중국산), 양파(국산), 대파(국산), 버터(프랑스)")
                .prepareTime(40)
                .waitLimits(10)
                .build();

        Long foodTruckId = 1L;

        given(foodTruckService.editFoodTruck(any(UpdateFoodTruckDto.class), any(MultipartFile.class), anyString()))
                .willReturn(foodTruckId);

        MockMultipartFile file = new MockMultipartFile("file", "image.jpg", MediaType.IMAGE_JPEG_VALUE, "image data".getBytes());

        String jsonRequest = objectMapper.writeValueAsString(request);
        MockMultipartFile jsonRequestPart = new MockMultipartFile("request", "request.json", APPLICATION_JSON_VALUE, jsonRequest.getBytes(UTF_8));

        // multipart 는 기본적으로 POST 요청을 위한 처리로만 사용되고 있으므로 아래와 같이 Override 해서 만들어줘야함
        MockMultipartHttpServletRequestBuilder builder =
                RestDocumentationRequestBuilders.
                        multipart("/food-truck/{foodTruckId}", foodTruckId);

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
                .andDo(document("edit-food-truck",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("foodTruckId").description("푸드트럭 식별키")
                        ),
                        requestParts(
                                partWithName("file").description("푸드트럭 이미지"),
                                partWithName("request").description("푸드트럭 정보")
                        ),
                        requestPartFields("request",
                                fieldWithPath("categoryId").type(JsonFieldType.NUMBER)
                                        .description("카테고리 식별키"),
                                fieldWithPath("foodTruckName").type(JsonFieldType.STRING)
                                        .description("푸드트럭 이름"),
                                fieldWithPath("phoneNumber").type(JsonFieldType.STRING)
                                        .description("연락처"),
                                fieldWithPath("content").type(JsonFieldType.STRING)
                                        .description("가게 소개"),
                                fieldWithPath("originInfo").type(JsonFieldType.STRING)
                                        .description("원산지 정보"),
                                fieldWithPath("prepareTime").type(JsonFieldType.NUMBER)
                                        .description("예상 준비 시간"),
                                fieldWithPath("waitLimits").type(JsonFieldType.NUMBER)
                                        .description("최대 주문 대기 건수")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER)
                                        .description("수정된 푸드트럭 식별키")
                        )
                ));
    }
}
