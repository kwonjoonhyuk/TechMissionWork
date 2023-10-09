package com.zerobase.tech.register.domain.repository.notification;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

public interface EmitterRepository {

    SseEmitter save(String emitterId,SseEmitter sseEmitter);
    void saveEventCache(String emitterId, Object event);
    Map<String,SseEmitter> findAllEmitterStartWithById(String sellerId);
    Map<String,Object> findAllEventCacheStartWithById(String sellerId);
    void deleteById(String id);
    void deleteAllEmitterStartWithId(String sellerId);
    void deleteAllEventCacheStartWithId(String sellerId);

}
