package com.boyworld.carrot.docs.schedule;

import com.boyworld.carrot.api.controller.schedule.ScheduleController;
import com.boyworld.carrot.api.controller.schedule.request.CreateScheduleRequest;
import com.boyworld.carrot.api.controller.schedule.response.ScheduleDetailResponse;
import com.boyworld.carrot.api.controller.schedule.response.ScheduleResponse;
import com.boyworld.carrot.api.service.schedule.ScheduleQueryService;
import com.boyworld.carrot.api.service.schedule.ScheduleService;
import com.boyworld.carrot.api.service.schedule.dto.CreateScheduleDto;
import com.boyworld.carrot.api.service.schedule.dto.ScheduleDto;
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

@WebMvcTest(ScheduleControllerDocsTest.class)
public class ScheduleControllerDocsTest extends RestDocsSupport {

    private final ScheduleService scheduleService = mock(ScheduleService.class);
    private final ScheduleQueryService scheduleQueryService = mock(ScheduleQueryService.class);

    @Override
    protected Object initController() {
        return new ScheduleController(scheduleService, scheduleQueryService);
    }

    @DisplayName("푸드트럭 스케줄 등록 API")
    @Test
    @WithMockUser(roles = "VENDOR")
    void createSchedule() throws Exception {

        CreateScheduleRequest request = CreateScheduleRequest.builder()
                .foodTruckId(1L)
                .address("광주광역시 광산구 장덕로 5번길 16")
                .latitude("37.5665")
                .longitude("126.9780")
                .days("월요일")
                .startTime("17:00")
                .endTime("01:00")
                .build();

        ScheduleDetailResponse response = ScheduleDetailResponse.builder()
                .scheduleId(1L)
                .address("광주광역시 광산구 장덕로 5번길 16")
                .days("월요일")
                .latitude("37.5665")
                .longitude("126.9780")
                .startTime("17:00")
                .endTime("01:00")
                .build();

        given(scheduleService.createSchedule(any(CreateScheduleDto.class), anyString()))
                .willReturn(response);

        mockMvc.perform(
                        post("/schedule")
                                .header("Authentication", "authentication")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("create-schedule",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("foodTruckId").type(JsonFieldType.NUMBER)
                                        .description("푸드트럭 식별키"),
                                fieldWithPath("address").type(JsonFieldType.STRING)
                                        .description("주소"),
                                fieldWithPath("latitude").type(JsonFieldType.STRING)
                                        .description("위도"),
                                fieldWithPath("longitude").type(JsonFieldType.STRING)
                                        .description("경도"),
                                fieldWithPath("days").type(JsonFieldType.STRING)
                                        .description("요일"),
                                fieldWithPath("startTime").type(JsonFieldType.STRING)
                                        .description("시작 시간"),
                                fieldWithPath("endTime").type(JsonFieldType.STRING)
                                        .description("종료 시간")
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
                                fieldWithPath("data.scheduleId").type(JsonFieldType.NUMBER)
                                        .description("스케줄 식별키"),
                                fieldWithPath("data.address").type(JsonFieldType.STRING)
                                        .description("주소"),
                                fieldWithPath("data.latitude").type(JsonFieldType.STRING)
                                        .description("위도"),
                                fieldWithPath("data.longitude").type(JsonFieldType.STRING)
                                        .description("경도"),
                                fieldWithPath("data.days").type(JsonFieldType.STRING)
                                        .description("요일"),
                                fieldWithPath("data.startTime").type(JsonFieldType.STRING)
                                        .description("시작 시간"),
                                fieldWithPath("data.endTime").type(JsonFieldType.STRING)
                                        .description("종료 시간")
                        )
                ));
    }

    @DisplayName("푸드트럭 스케줄 목록 조회 API")
    @Test
    @WithMockUser(roles = "VENDOR")
    void getSchedules() throws Exception {
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

        ScheduleResponse response = ScheduleResponse.builder()
                .schedules(List.of(schedule1, schedule2, schedule3))
                .build();

        given(scheduleQueryService.getSchedules(anyLong(), anyString()))
                .willReturn(response);

        mockMvc.perform(
                        get("/schedule")
                                .header("Authentication", "authentication")
                                .param("foodTruckId", "1")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("search-schedule",
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
                                        .description("푸드트럭 상세 조회 결과"),
                                fieldWithPath("data.schedules").type(JsonFieldType.ARRAY)
                                        .description("스케줄 리스트"),
                                fieldWithPath("data.schedules[].scheduleId").type(JsonFieldType.NUMBER)
                                        .description("스케줄 식별키"),
                                fieldWithPath("data.schedules[].address").type(JsonFieldType.STRING)
                                        .description("주소"),
                                fieldWithPath("data.schedules[].days").type(JsonFieldType.STRING)
                                        .description("요일"),
                                fieldWithPath("data.schedules[].startTime").type(JsonFieldType.STRING)
                                        .description("시작 시간"),
                                fieldWithPath("data.schedules[].endTime").type(JsonFieldType.STRING)
                                        .description("종료 시간")
                        )
                ));
    }
}
