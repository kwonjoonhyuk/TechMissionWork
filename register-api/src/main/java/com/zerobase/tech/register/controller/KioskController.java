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

    @PostMapping
    public ResponseEntity<String> Get(@RequestBody KioskForm form) {
        return ResponseEntity.ok(kioskService.kioskNotificationCreate(form.getName(), form.getPhone(), form.getReserveNumber(), form.getShopId()));
    }
}
