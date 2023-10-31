package com.boyworld.carrot.docs.analysis;

import com.boyworld.carrot.api.controller.analysis.AnalysisController;
import com.boyworld.carrot.api.controller.analysis.response.StoreAnalysisResponse;
import com.boyworld.carrot.api.service.analysis.AnalysisQueryService;
import com.boyworld.carrot.api.service.analysis.dto.StoreAnalysisDto;
import com.boyworld.carrot.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.restdocs.payload.JsonFieldType;

import java.math.BigDecimal;

import static jdk.internal.org.jline.reader.impl.LineReaderImpl.CompletionType.List;
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

@WebMvcTest(AnalysisControllerDocsTest.class)
public class AnalysisControllerDocsTest extends RestDocsSupport {

    private final AnalysisQueryService analysisQueryService = mock(AnalysisQueryService.class);

    @Override
    protected Object initController() { return new AnalysisController(analysisQueryService); }

    @DisplayName("상권분석 API")
    @Test
    void getStoreAnalysis() throws Exception {

        StoreAnalysisDto item1 = StoreAnalysisDto.builder()
                .storeName("")
                .latitude(new BigDecimal("35.19684"))
                .longitude(new BigDecimal("126.8108"))
                .sido("광주광역시")
                .sigungu("광산구")
                .dong("장덕동")
                .middleClassCode("I201")
                .smallClassCode("I20109")
                .build();

        StoreAnalysisResponse response = StoreAnalysisResponse.builder()
                .categoryId(1L)
                .categoryName("한식")
                .sido("광주광역시")
                .sigungu("광산구")
                .dong("수완동")
                .radiusCount(4)
                .addressCount(15)
                .stores(List.of(null));
    }

}
