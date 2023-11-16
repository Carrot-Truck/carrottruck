package com.boyworld.carrot.client.Sse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class EmitterRepository {
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter save(String email, SseEmitter emitter) {
        emitters.put(email, emitter);
        return emitter;
    }
    public void delete(String email){
        emitters.remove(email);
    }

    public Optional<SseEmitter> get(String email){
        SseEmitter result = emitters.get(email);
        return Optional.ofNullable(result);
    }
}
