package com.boyworld.carrot.docs.address;

import com.boyworld.carrot.api.controller.address.AddressController;
import com.boyworld.carrot.api.controller.address.response.AddressResponse;
import com.boyworld.carrot.api.controller.address.response.ReverseGeocodingResponse;
import com.boyworld.carrot.api.service.address.AddressQueryService;
import com.boyworld.carrot.api.service.address.dto.AddressInfoDto;
import com.boyworld.carrot.api.service.geocoding.GeocodingService;
import com.boyworld.carrot.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.restdocs.payload.JsonFieldType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private final GeocodingService geocodingService = mock(GeocodingService.class);

    @Override
    protected Object initController() { return new AddressController(addressQueryService, geocodingService); }

    @DisplayName("시도 리스트 조회 API")
    @Test
    void getSido() throws Exception {

        List<AddressInfoDto> addr = new ArrayList<>();

        String[] sidos = new String[] {"강원도", "경기도", "광주광역시"};

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

        Long sid = 3L;

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

        String[] dongs = new String[] {"송정동", "도산동", "도호동"};

        for (int i = 0; i < dongs.length; i++) {
            addr.add(AddressInfoDto.builder()
                    .id((long) (i+1))
                    .name(dongs[i])
                    .build());
        }

        AddressResponse response = AddressResponse.builder()
                .address(addr)
                .build();

        Long sid = 5L;

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

    @DisplayName("좌표 변환 API")
    @Test
    void reverseGeocoding() throws Exception {

        BigDecimal latitude = new BigDecimal("36.12341234");
        BigDecimal longitude = new BigDecimal("128.3456789");

        String addr = "서울 마포구 동교로 104";

        ReverseGeocodingResponse response = ReverseGeocodingResponse.builder()
                .address(addr)
                .build();

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("roadaddr", addr);

        given(geocodingService.reverseGeocoding(any(BigDecimal.class), any(BigDecimal.class), anyString()))
                .willReturn(responseMap);

        mockMvc.perform(get("/address/rgc")
                        .param("latitude", String.valueOf(latitude))
                        .param("longitude", String.valueOf(longitude)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-address-geocode",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("latitude")
                                        .description("위도"),
                                parameterWithName("longitude")
                                        .description("경도")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data.address").type(JsonFieldType.STRING)
                                        .description("도로명 주소")
                        )
                ));
    }

}
