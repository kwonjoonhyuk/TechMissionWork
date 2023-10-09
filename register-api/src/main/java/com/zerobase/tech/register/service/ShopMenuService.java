package com.zerobase.tech.register.service;

import com.zerobase.tech.register.domain.shop.Shop;
import com.zerobase.tech.register.domain.shop.ShopMenu;
import com.zerobase.tech.register.domain.repository.shop.ShopMenuRepository;
import com.zerobase.tech.register.domain.repository.shop.ShopRepository;
import com.zerobase.tech.register.domain.shop.add.AddShopMenuForm;
import com.zerobase.tech.register.domain.shop.update.UpdateShopMenuForm;
import com.zerobase.tech.register.exception.CustomException;
import com.zerobase.tech.register.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShopMenuService {

    private final ShopRepository shopRepository;
    private final ShopMenuRepository shopMenuRepository;

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

    @Transactional
    public void deleteShopMenu(Long sellerId , Long shopMenuId){
        ShopMenu shopMenu = shopMenuRepository.findById(shopMenuId)
                .filter(shopMenu1 -> shopMenu1.getSellerId().equals(sellerId))
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_MENU));

        shopMenuRepository.delete(shopMenu);
    }
}
