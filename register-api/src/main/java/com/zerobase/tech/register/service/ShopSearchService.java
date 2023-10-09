package com.zerobase.tech.register.service;

import com.zerobase.tech.register.domain.shops.Shop;
import com.zerobase.tech.register.domain.repository.shops.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopSearchService {

    private final ShopRepository shopRepository;

    // 상점 거리순 검색
    public List<Shop> getShopsByLatitudeAndLongitude(double longitude,double latitude){
        return shopRepository.getShopsByLatitudeAndLongitude(longitude,latitude);
    }

    // 상점 가나다 순 검색
    public List<Shop> getShopName(){
        return shopRepository.searchByShopName();
    }

    // 상점 별점 순 검색
    public List<Shop> searchShopByStarsDesc(){
        return shopRepository.searchShop();
    }
}
