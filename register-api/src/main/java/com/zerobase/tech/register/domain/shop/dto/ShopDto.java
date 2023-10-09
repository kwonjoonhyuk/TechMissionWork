package com.zerobase.tech.register.domain.shop.dto;

import com.zerobase.tech.register.domain.shop.Shop;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShopDto {
    private Long id;
    private String shopName;
    private String shopDescription;
    private String address;
    private String roadAddress;
    private double stars;
    private List<ShopMenuDto> items;

    public static ShopDto from(Shop shop){
        List<ShopMenuDto> items =  shop.getShopMenus()
                .stream().map(ShopMenuDto::from).collect(Collectors.toList());

        return ShopDto.builder()
                .id(shop.getId())
                .shopName(shop.getShopName())
                .shopDescription(shop.getShopDescription())
                .address(shop.getAddress())
                .roadAddress(shop.getRoadAddress())
                .stars(shop.getStars())
                .items(items)
                .build();
    }

    public static ShopDto withOutMenusfrom(Shop shop){

        return ShopDto.builder()
                .id(shop.getId())
                .shopName(shop.getShopName())
                .shopDescription(shop.getShopDescription())
                .address(shop.getAddress())
                .stars(shop.getStars())
                .roadAddress(shop.getRoadAddress())
                .build();
    }
}
