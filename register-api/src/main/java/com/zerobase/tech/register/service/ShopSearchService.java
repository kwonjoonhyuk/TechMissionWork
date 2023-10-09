package com.zerobase.tech.register.service;

import com.zerobase.tech.register.domain.shop.Shop;
import com.zerobase.tech.register.domain.repository.shop.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopSearchService {

    private final ShopRepository shopRepository;

    public List<Shop> getShopsByLatitudeAndLongitude(double longitude,double latitude){
        return shopRepository.getShopsByLatitudeAndLongitude(longitude,latitude);
    }

    public List<Shop> getShopName(){
        return shopRepository.searchByShopName();
    }

    public List<Shop> searchShopByStarsDesc(){
        return shopRepository.searchShop();
    }
}
