package com.boyworld.carrot.docs.address;

import com.boyworld.carrot.api.controller.address.AddressController;
import com.boyworld.carrot.api.controller.address.response.AddressResponse;
import com.boyworld.carrot.api.service.address.AddressQueryService;
import com.boyworld.carrot.api.service.address.dto.AddressInfoDto;
import com.boyworld.carrot.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.ArrayList;
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

@WebMvcTest(AddressControllerDocsTest.class)
public class AddressControllerDocsTest extends RestDocsSupport {

    private final AddressQueryService addressQueryService = mock(AddressQueryService.class);

    @Override
    protected Object initController() { return new AddressController(addressQueryService); }

    @DisplayName("시도 리스트 조회 API")
    @Test
    void getSido() throws Exception {

        List<AddressInfoDto> addr = new ArrayList<>();

        String[] sidos = new String[] {"강원도", "경기도", "충청북도", "충청남도", "인천광역시",
                "부산광역시", "대구광역시", "서울특별시", "대전광역시", "울산광역시",
                "광주광역시", "세종특별자치시", "전라남도", "전라북도", "경상북도",
                "경상남도", "제주특별자치도"};

        for (int i = 0; i < sidos.length; i++) {
            addr.add(AddressInfoDto.builder()
                    .id((long) (i+1))
                    .name(sidos[i])
                    .build());
        }

        AddressResponse response = AddressResponse.builder()
                .address(addr)
                .build();

        given(addressQueryService.getSido())
                .willReturn(response);

        mockMvc.perform(get("/address/sido"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-address-sido",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data.address[].id").type(JsonFieldType.NUMBER)
                                        .description("시도 ID"),
                                fieldWithPath("data.address[].name").type(JsonFieldType.STRING)
                                        .description("시도 이름")
                        )
                ));
    }

    @DisplayName("시군구 리스트 조회 API")
    @Test
    void getSigungu() throws Exception {

        List<AddressInfoDto> addr = new ArrayList<>();

        String[] sigungus = new String[] {"동구", "서구", "북구", "남구", "광산구"};

        for (int i = 0; i < sigungus.length; i++) {
            addr.add(AddressInfoDto.builder()
                    .id((long) (i+1))
                    .name(sigungus[i])
                    .build());
        }

        AddressResponse response = AddressResponse.builder()
                .address(addr)
                .build();

        Long sid = 11L;

        given(addressQueryService.getSigungu(anyLong()))
                .willReturn(response);

        mockMvc.perform(get("/address/sigungu/{sidoId}", sid))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-address-sigungu",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("sidoId")
                                        .description("시도 ID")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data.address[].id").type(JsonFieldType.NUMBER)
                                        .description("시군구 ID"),
                                fieldWithPath("data.address[].name").type(JsonFieldType.STRING)
                                        .description("시군구 이름")
                        )
                ));
    }

    @DisplayName("읍면동 리스트 조회 API")
    @Test
    void getDong() throws Exception {

        List<AddressInfoDto> addr = new ArrayList<>();

        String[] dongs = new String[] {"송정동", "도산동", "도호동", "신촌동", "서봉동", "운수동", "선암동", "소촌동", "우산동",
                "황룡동", "박호동", "비아동", "도천동", "수완동", "월계동", "쌍암동", "산월동", "신창동", "신가동", "운남동",
                "안청동", "진곡동", "장덕동", "흑석동", "하남동", "장수동", "산정동", "월곡동", "등임동", "산막동", "고룡동",
                "신룡동", "두정동", "임곡동", "광산동", "오산동", "사호동", "하산동", "유계동", "본덕동", "용봉동", "요기동",
                "복룡동", "송대동", "옥동", "월전동", "장록동", "송촌동", "지죽동", "용동", "용곡동", "지정동", "명화동", "동산동",
                "연산동", "도덕동", "송산동", "지평동", "오운동", "삼거동", "양동", "내산동", "대산동", "송학동", "신동", "삼도동",
                "남산동", "송치동", "산수동", "선동", "지산동", "왕동", "북산동", "명도동", "동호동", "덕림동", "양산동", "동림동",
                "오선동"};

        for (int i = 0; i < dongs.length; i++) {
            addr.add(AddressInfoDto.builder()
                    .id((long) (i+1))
                    .name(dongs[i])
                    .build());
        }

        AddressResponse response = AddressResponse.builder()
                .address(addr)
                .build();

        Long sid = 139L;

        given(addressQueryService.getDong(anyLong()))
                .willReturn(response);

        mockMvc.perform(get("/address/dong/{sigunguId}", sid))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-address-dong",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("sigunguId")
                                        .description("시군구 ID")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data.address[].id").type(JsonFieldType.NUMBER)
                                        .description("읍면동 ID"),
                                fieldWithPath("data.address[].name").type(JsonFieldType.STRING)
                                        .description("읍면동 이름")
                        )
                ));
    }

}
