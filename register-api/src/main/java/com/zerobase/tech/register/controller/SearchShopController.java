package com.zerobase.tech.register.controller;

import com.zerobase.tech.register.domain.shop.Shop;
import com.zerobase.tech.register.domain.shop.dto.ShopDto;
import com.zerobase.tech.register.service.ShopSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search/shop")
@RequiredArgsConstructor
public class SearchShopController {

    private final ShopSearchService shopSearchService;

    @GetMapping
    public ResponseEntity<List<ShopDto>> searchByLongitudeAndLatitude(@RequestParam double longitude, double latitude){
        return ResponseEntity.ok(
                shopSearchService.getShopsByLatitudeAndLongitude(longitude,latitude).stream()
                        .map(ShopDto::withOutMenusfrom).collect(Collectors.toList())
        );
    }

    @GetMapping("/ASC")
    public ResponseEntity<List<ShopDto>> searchByShopName(){
        return ResponseEntity.ok(
                shopSearchService.getShopName().stream()
                        .map(ShopDto::withOutMenusfrom).collect(Collectors.toList())
        );
    }

    // 별점 순으로 검색
    @GetMapping("/stars/desc")
    public ResponseEntity<List<ShopDto>> searchShopByStars(){
        return ResponseEntity.ok(
                shopSearchService.searchShopByStarsDesc().stream()
                        .map(ShopDto::withOutMenusfrom).collect(Collectors.toList())
        );
    }

}
