package com.zerobase.tech.register.service;

import com.zerobase.tech.register.domain.shops.Shop;
import com.zerobase.tech.register.domain.shops.ShopMenu;
import com.zerobase.tech.register.domain.repository.shops.ShopRepository;
import com.zerobase.tech.register.domain.shops.add.AddShopForm;
import com.zerobase.tech.register.domain.shops.update.UpdateShopForm;
import com.zerobase.tech.register.domain.shops.update.UpdateShopMenuForm;
import com.zerobase.tech.register.exceptions.CustomException;
import com.zerobase.tech.register.exceptions.ErrorCode;
import com.zerobase.tech.register.kakaoRestApi.KakaoAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;

    // 상점 추가(셀러)
    @Transactional
    public Shop addShop(Long sellerId, AddShopForm form) {
        return shopRepository.save(Objects.requireNonNull(Shop.of(sellerId, form)));
    }

    // 상점 수정(셀러)
    @Transactional
    public Shop updateShop(Long sellerId, UpdateShopForm form) {
        Shop shop = shopRepository.findBySellerIdAndId(sellerId, form.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SHOP));
        shop.setShopName(form.getShopName());
        shop.setShopDescription(form.getShopDescription());

        KakaoAddress kakaoAddress = new KakaoAddress();
        ArrayList<String> arrayList = new ArrayList<>();

        arrayList = kakaoAddress.getLocation(form.getAddress());
        if (arrayList.isEmpty()){
            throw new CustomException(ErrorCode.NOT_FOUND_ADDRESS);
        }
        shop.setAddress(arrayList.get(0));
        shop.setRoadAddress(arrayList.get(1));
        shop.setLongitude(Double.parseDouble(arrayList.get(2)));
        shop.setLatitude(Double.parseDouble(arrayList.get(3)));


        for(UpdateShopMenuForm menuForm : form.getItems()){
            ShopMenu menu = shop.getShopMenus().stream()
                    .filter(shopItem -> shopItem.getId().equals(menuForm.getShopId()))
                    .findFirst().orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_SHOP));
            menu.setMenuName(menuForm.getMenuName());
            menu.setMenuDescription(menuForm.getMenuDescription());
            menu.setPrice(menuForm.getPrice());
        }
        return shop;
    }

    // 상점 삭제(셀러)
    @Transactional
    public void deleteShop(Long sellerId , Long shopId){
        Shop shop = shopRepository.findBySellerIdAndId(sellerId,shopId)
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_SHOP));
        shopRepository.delete(shop);
    }
}
