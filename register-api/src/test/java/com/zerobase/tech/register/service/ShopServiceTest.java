package com.zerobase.tech.register.service;

import com.zerobase.tech.register.domain.shops.Shop;
import com.zerobase.tech.register.domain.repository.shops.ShopMenuRepository;
import com.zerobase.tech.register.domain.repository.shops.ShopRepository;
import com.zerobase.tech.register.domain.shops.add.AddShopForm;
import com.zerobase.tech.register.domain.shops.add.AddShopMenuForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShopServiceTest {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ShopMenuRepository shopMenuRepository;

    @Test
    void ADD_SHOP() {
        Long sellerId = 1L;

        AddShopForm form = makeProductForm("이름", "설명", "경기 군포시 산본로323번길 16-25", 3);

        Shop shop = shopService.addShop(sellerId, form);

        Shop result = shopRepository.findWithProductItemById(shop.getId()).get();

        List<Shop> result_2 = shopRepository.getShopsByLatitudeAndLongitude(37.3632074085564, 126.926206256187);


        assertEquals(result_2.get(0).getShopName(), "웰빙고추김밥");

        assertEquals(result.getShopName(), "이름");
        assertEquals(result.getShopDescription(), "설명");
        assertEquals(result.getShopMenus().size(), 3);
        assertEquals(result.getShopMenus().get(0).getMenuName(), "이름0");
        assertEquals(result.getShopMenus().get(0).getPrice(), 10000);


        assertNotNull(result);
    }

    private static AddShopForm makeProductForm(String name, String description, String address, int itemCount) {
        List<AddShopMenuForm> itemForms = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            itemForms.add(makeProductItemForm(null, name + i));
        }
        return AddShopForm.builder()
                .shopName(name)
                .shopDescription(description)
                .address(address)
                .build();
    }

    private static AddShopMenuForm makeProductItemForm(Long shopId, String name) {
        return AddShopMenuForm.builder()
                .shopId(shopId)
                .menuName(name)
                .price(10000)
                .build();
    }

}