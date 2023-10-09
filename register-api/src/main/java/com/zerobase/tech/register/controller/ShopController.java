package com.zerobase.tech.register.controller;

import com.zerobase.domain.config.JwtAuthenticationProvider;
import com.zerobase.tech.register.domain.shops.add.AddShopMenuForm;
import com.zerobase.tech.register.domain.shops.dto.ShopDto;
import com.zerobase.tech.register.domain.shops.add.AddShopForm;
import com.zerobase.tech.register.domain.shops.dto.ShopMenuDto;
import com.zerobase.tech.register.domain.shops.update.UpdateShopForm;
import com.zerobase.tech.register.domain.shops.update.UpdateShopMenuForm;
import com.zerobase.tech.register.service.NotificationService;
import com.zerobase.tech.register.service.ShopMenuService;
import com.zerobase.tech.register.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/seller/shop")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;
    private final ShopMenuService shopMenuService;
    private final JwtAuthenticationProvider provider;
    private final NotificationService notificationService;

    // 상점 추가(셀러)
    @PostMapping
    public ResponseEntity<ShopDto> addShop(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                           @RequestBody AddShopForm form) {
        return ResponseEntity.ok(ShopDto.from(shopService.addShop(provider.getUserVo(token).getId(), form)));
    }

    // 상점 메뉴 추가(셀러)
    @PostMapping("/menu")
    public ResponseEntity<ShopDto> addShopMenu(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                               @RequestBody AddShopMenuForm form) {
        return ResponseEntity.ok(ShopDto.from(shopMenuService.addShopMenu(provider.getUserVo(token).getId(), form)));
    }

    // 상점 업데이트(수정)(셀러)
    @PutMapping
    public ResponseEntity<ShopDto> updateShop(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                              @RequestBody UpdateShopForm form) {
        return ResponseEntity.ok(ShopDto.from(shopService.updateShop(provider.getUserVo(token).getId(), form)));
    }

    // 상점 메뉴 업데이트(수정)(셀러)
    @PutMapping("/menu")
    public ResponseEntity<ShopMenuDto> updateShopMenu(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                      @RequestBody UpdateShopMenuForm form) {
        return ResponseEntity.ok(ShopMenuDto.from(shopMenuService.updateShopMenu(provider.getUserVo(token).getId(), form)));
    }

    // 상점 지우기(셀러)
    @DeleteMapping
    public ResponseEntity<Void> deleteShop(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                           @RequestParam Long id) {
        shopService.deleteShop(provider.getUserVo(token).getId(), id);
        return ResponseEntity.ok().build();
    }

    // 상점 메뉴 지우기(셀러)
    @DeleteMapping("/menu")
    public ResponseEntity<Void> deleteShopMenu(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                               @RequestParam Long id) {
        shopMenuService.deleteShopMenu(provider.getUserVo(token).getId(), id);
        return ResponseEntity.ok().build();
    }


    // 상점 포스기 혹은 사용하고 있는 어플리케이션 로그인 Sse 연결받아서 키오스크에서 고객이 도착확인시 알림이 오는 부분(셀러)
    @GetMapping(value = "/kioskVerify", produces = "text/event-stream; charset=utf-8")
    public SseEmitter kioskVerify(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                  @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        return notificationService.kioskVerify(provider.getUserVo(token).getId(), lastEventId);
    }




}
