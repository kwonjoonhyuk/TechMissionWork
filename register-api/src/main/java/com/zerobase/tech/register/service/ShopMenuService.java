package com.zerobase.tech.register.service;

import com.zerobase.tech.register.domain.shops.Shop;
import com.zerobase.tech.register.domain.shops.ShopMenu;
import com.zerobase.tech.register.domain.repository.shops.ShopMenuRepository;
import com.zerobase.tech.register.domain.repository.shops.ShopRepository;
import com.zerobase.tech.register.domain.shops.add.AddShopMenuForm;
import com.zerobase.tech.register.domain.shops.update.UpdateShopMenuForm;
import com.zerobase.tech.register.exceptions.CustomException;
import com.zerobase.tech.register.exceptions.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShopMenuService {

    private final ShopRepository shopRepository;
    private final ShopMenuRepository shopMenuRepository;

    // 상점 메뉴 추가(셀러)
    @Transactional
    public Shop addShopMenu(Long sellerId, AddShopMenuForm form) {
        Shop shop = shopRepository.findBySellerIdAndId(sellerId,form.getShopId())
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_SHOP));

        if (shop.getShopMenus().stream()
                .anyMatch(menu -> menu.getMenuName().equals(form.getMenuName()))){
            throw new CustomException(ErrorCode.SAME_MENU_NAME);
        }

        ShopMenu shopMenu = ShopMenu.of(sellerId,form);
        shop.getShopMenus().add(shopMenu);
        return shop;
    }

    // 상점 메뉴 수정(셀러)
    @Transactional
    public ShopMenu updateShopMenu(Long sellerId, UpdateShopMenuForm form) {
        ShopMenu shopMenu = shopMenuRepository.findById(form.getId())
                .filter(shopMenu1 -> shopMenu1.getSellerId().equals(sellerId))
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_MENU));

        shopMenu.setMenuName(form.getMenuName());
        shopMenu.setMenuDescription(form.getMenuDescription());
        shopMenu.setPrice(form.getPrice());
        return shopMenu;
    }

    // 상점 메뉴 지우기(셀러)
    @Transactional
    public void deleteShopMenu(Long sellerId , Long shopMenuId){
        ShopMenu shopMenu = shopMenuRepository.findById(shopMenuId)
                .filter(shopMenu1 -> shopMenu1.getSellerId().equals(sellerId))
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_MENU));

        shopMenuRepository.delete(shopMenu);
    }
}
