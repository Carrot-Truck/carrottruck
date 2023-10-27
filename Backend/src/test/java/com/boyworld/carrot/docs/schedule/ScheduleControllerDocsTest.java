package com.boyworld.carrot.docs.schedule;

import com.boyworld.carrot.api.controller.schedule.ScheduleController;
import com.boyworld.carrot.api.controller.schedule.request.CreateScheduleRequest;
import com.boyworld.carrot.api.controller.schedule.response.ScheduleResponse;
import com.boyworld.carrot.api.service.schedule.ScheduleService;
import com.boyworld.carrot.api.service.schedule.dto.CreateScheduleDto;
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

@WebMvcTest(ScheduleControllerDocsTest.class)
public class ScheduleControllerDocsTest extends RestDocsSupport {

    private final ScheduleService scheduleService = mock(ScheduleService.class);

    @Override
    protected Object initController() {
        return new ScheduleController(scheduleService);
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

        ScheduleResponse response = ScheduleResponse.builder()
                .scheduleId(1L)
                .address("광주광역시 광산구 장덕로 5번길 16")
                .days("월요일")
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
                                fieldWithPath("data.days").type(JsonFieldType.STRING)
                                        .description("요일"),
                                fieldWithPath("data.startTime").type(JsonFieldType.STRING)
                                        .description("시작 시간"),
                                fieldWithPath("data.endTime").type(JsonFieldType.STRING)
                                        .description("종료 시간")
                        )
                ));
    }
}
