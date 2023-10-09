package com.zerobase.tech.register.controller;

import com.zerobase.tech.register.domain.notification.KioskForm;
import com.zerobase.tech.register.service.KioskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kiosk")
public class KioskController {

    private final KioskService kioskService;

    // 키오스크 예약 확인(확인 될시에 상점 셀러에게 알림)
    @PostMapping
    public ResponseEntity<String> Get(@RequestBody KioskForm form) {
        return ResponseEntity.ok(kioskService.kioskNotificationCreate(form.getName(), form.getPhone(), form.getReserveNumber(), form.getShopId()));
    }
}
