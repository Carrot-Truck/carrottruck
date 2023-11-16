package com.boyworld.carrot.client.Sse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.NoSuchElementException;

@Slf4j
@Component
@RequiredArgsConstructor
public class SseUtil {

    private final EmitterRepository emitterRepository;
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    public SseEmitter subscribe(String email) throws IOException{
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(email, emitter);
        emitter.onCompletion(() -> emitterRepository.delete(email));
        emitter.onTimeout(() -> emitterRepository.delete(email));
            log.info("connect : " + email);
            emitter.send(SseEmitter.event()
                    .id("id")
                    .name("sse")
                    .data("connect completed")
            );
            log.info("send 완료 : {}", email);

        log.info("리턴값 : {}", emitter);
        return emitter;
    }

    public void send(String email, Object data) {
        emitterRepository.get(email).ifPresentOrElse(it -> {
                    try {
                        it.send(SseEmitter.event()
                                .name("sse")
                                .data(data));
                        log.info("알림 보내기 성공");
                    } catch (IOException e) {
                        emitterRepository.delete(email);
                        throw new NoSuchElementException();
                    }
                },
                () -> log.info("emitter를 찾지 못했습니다.")
        );
    }

}
