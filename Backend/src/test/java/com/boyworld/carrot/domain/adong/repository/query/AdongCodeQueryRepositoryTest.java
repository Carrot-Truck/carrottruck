package com.boyworld.carrot.domain.adong.repository.query;

import com.boyworld.carrot.IntegrationTestSupport;
import com.boyworld.carrot.domain.adong.repository.AdongCodeQueryRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class AdongCodeQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private AdongCodeQueryRepository adongCodeQueryRepository;

    @DisplayName("행정동 주소로 행정동코드를 가져온다.")
    @Test
    void getAdongCode() {
        String sido = "광주광역시";
        String sigungu = "광산구";
        String dong = "수완동";

        String result = adongCodeQueryRepository.getAdongCode(sido, sigungu, dong);

        log.debug("getAdongCode#result={}", result);
    }

    @DisplayName("존재하지 않는 주소로 호출한다면 null을 반환한다.")
    @Test
    void getAdongCodeNotExists() {
        String sido = "광주광역시";
        String sigungu = "광산구";
        String dong = "장덕동";

        String result = adongCodeQueryRepository.getAdongCode(sido, sigungu, dong);

        log.debug("getAdongCodeNotExists#result={}", result);
    }
}
