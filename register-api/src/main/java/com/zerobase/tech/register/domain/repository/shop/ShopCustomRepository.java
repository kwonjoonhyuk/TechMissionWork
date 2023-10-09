package com.zerobase.tech.register.domain.repository.shop;

import com.zerobase.tech.register.domain.shop.Shop;

import java.util.List;

public interface ShopCustomRepository {

    List<Shop> searchByShopName();

    List<Shop> getShopsByLatitudeAndLongitude(double longitude, double latitude);

    Long getSellerIdById(Long id);

    Long searchBySellerId(Long sellerId, Long shopId);

    void updateStarsById(Long shopId,double stars);

    List<Shop> searchShop();

}
