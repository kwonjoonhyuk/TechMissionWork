package com.zerobase.tech.register.service;

import com.zerobase.tech.register.domain.notification.Notify;
import com.zerobase.tech.register.domain.repository.notification.EmitterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {

    //SSE 연결 지속 시간 설정
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private final EmitterRepository emitterRepository;

    // 키오스크에서 고객이 확인을 요청
    public SseEmitter kioskVerify(Long userId, String lastEventId) {

        // 1.
        String id = userId + "_" + System.currentTimeMillis();

        // 2.
        SseEmitter emitter = emitterRepository.save(id, new SseEmitter(DEFAULT_TIMEOUT));

        emitter.onCompletion(() -> emitterRepository.deleteById(id));
        emitter.onTimeout(() -> emitterRepository.deleteById(id));

        // 3. 503 에러를 방지하기 위한 더미 이벤트 전송
        sendToSeller(emitter, id, "EventStream Created. [userId=" + userId + "]");

        // 4. 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
        if (!lastEventId.isEmpty()) {
            Map<String, Object> events = emitterRepository.findAllEventCacheStartWithById(String.valueOf(userId));
            events.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> sendToSeller(emitter, entry.getKey(), entry.getValue()));
        }
        return emitter;
    }

    // 상점 seller에게 확인 데이터 및 알림 전송
    @Transactional
    public void send(Long sellerId, Long shopId, String content) {
        Notify notify = createNotification(sellerId, shopId, content);
        String id = String.valueOf(sellerId);

        // 로그인 한 상점 seller 유저의 SseEmitter 모두 가져오기
        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllEmitterStartWithById(id);
        sseEmitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notify);
                    sendToSeller(emitter, key, content);
                }
        );
    }

    // 키오스크에서 고객이 확인 요청한것을 데이터 전송
    private void sendToSeller(SseEmitter emitter, String id, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(id)
                    .name("Kiosk reservation verify")
                    .data(data)
                    .comment("보내짐"));
        } catch (IOException e) {
            emitterRepository.deleteById(id);
            throw new RuntimeException("연결 오류!!");
        }
    }

    //알림 생성
    private Notify createNotification(Long sellerId, Long shopId, String content) {
        return Notify.builder()
                .sellerId(sellerId)
                .shopId(shopId)
                .content(content)
                .isRead("false")
                .build();
    }


}
