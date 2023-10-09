package com.zerobase.tech.register.domain.repository.notification;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class EmitterRepositoryImpl implements EmitterRepository{

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<String, Object> eventCache = new ConcurrentHashMap<>();

    // emitter를 저장
    @Override
    public SseEmitter save(String emitterId, SseEmitter sseEmitter) {
        emitters.put(emitterId,sseEmitter);
        return sseEmitter;
    }

    // 이벤트를 저장
    @Override
    public void saveEventCache(String eventCacheId, Object event) {
        eventCache.put(eventCacheId, event);
    }

    // 해당 seller와 관련된 모든 emitter를 찾음
    @Override
    public Map<String, SseEmitter> findAllEmitterStartWithById(String sellerId) {
        return emitters.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(sellerId))
                .collect(Collectors.toMap(Map.Entry::getKey ,Map.Entry::getValue));
    }

    // 해당 seller와 관련된 모든 이벤트를 찾음
    @Override
    public Map<String, Object> findAllEventCacheStartWithById(String sellerId) {
        return eventCache.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(sellerId))
                .collect(Collectors.toMap(Map.Entry::getKey ,Map.Entry::getValue));
    }

    //emitter를 지움
    @Override
    public void deleteById(String id) {
        emitters.remove(id);
    }

    // 해당 seller와 관련된 모든 emitter를 지움
    @Override
    public void deleteAllEmitterStartWithId(String sellerId) {
        emitters.forEach(
                (key, emitter) -> {
                    if (key.startsWith(sellerId)) {
                        emitters.remove(key);
                    }
                }
        );
    }

    // 해당 seller와 관련된 모든 이벤트를 지움
    @Override
    public void deleteAllEventCacheStartWithId(String sellerId) {
        eventCache.forEach(
                (key, emitter) -> {
                    if (key.startsWith(sellerId)) {
                        eventCache.remove(key);
                    }
                }
        );
    }
}
