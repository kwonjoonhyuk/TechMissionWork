package com.zerobase.tech.register.controller;

import com.zerobase.domain.config.JwtAuthenticationProvider;
import com.zerobase.tech.register.domain.reserveShop.AddShopReserveForm;
import com.zerobase.tech.register.domain.reserveShop.ShopReserveDto;
import com.zerobase.tech.register.service.ShopReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/shop/reserve")
@RequiredArgsConstructor
public class ShopReserveController {

    private final ShopReserveService shopReserveService;
    private final JwtAuthenticationProvider provider;

    // 예약하기(고객)
    @PostMapping()
    public ResponseEntity<ShopReserveDto> reserveShop(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                      @RequestBody AddShopReserveForm form) {

        return ResponseEntity.ok(ShopReserveDto.from(shopReserveService.reserveShop(provider.getUserVo(token).getId(), form)));
    }

    // 예약 가능여부 확인(고객)
    @GetMapping("/customer/availability")
    public ResponseEntity<String> searchByShopIdAndReserveDateAndHoursAndMinutes(@RequestParam
                                                                                 Long shopId, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate reserveDate,
                                                                                 Integer hours, Integer minutes) {
        return ResponseEntity.ok(
                shopReserveService.searchByShopIdAndReserveDateAndHoursAndMinutes(shopId, reserveDate, hours, minutes)
        );
    }

    //예약 내역 확인(고객)
    @GetMapping("/customer/reserveHistory")
    public ResponseEntity<List<ShopReserveDto>> getShopReservesByCustomerId(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
        return ResponseEntity.ok(
                shopReserveService.getShopReservesByCustomerId(provider.getUserVo(token).getId())
                        .stream().map(ShopReserveDto::get).collect(Collectors.toList())
        );
    }

    //예약 취소(고객)
    @DeleteMapping
    public ResponseEntity<String> cancelReserve(@RequestHeader(name = "X-AUTH-TOKEN") String token, Long id) {

        return ResponseEntity.ok(shopReserveService.deleteShopReserve(provider.getUserVo(token).getId(), id));
    }

    // 예약 내역 확인 (상점 셀러) 키오스크에서 확인된 예약내역은 제외하고 아직 예약목록에 있는 것들만 불러오기 해당 상점만
    @GetMapping("/seller/search")
    public ResponseEntity<List<ShopReserveDto>> getShopReserveSeller(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                                     @RequestParam Long shopId) {
        return ResponseEntity.ok(
                shopReserveService.getShopReserve(provider.getUserVo(token).getId(),shopId)
                        .stream().map(ShopReserveDto::getSeller).collect(Collectors.toList())
        );
    }


}
