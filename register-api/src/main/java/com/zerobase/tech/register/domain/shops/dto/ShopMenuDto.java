package com.zerobase.tech.register.domain.shops.dto;


import com.zerobase.tech.register.domain.shops.ShopMenu;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShopMenuDto {

    private Long id;
    private String menuName;
    private String menuDescription;
    private Integer price;

    public static ShopMenuDto from(ShopMenu item){
        return ShopMenuDto.builder()
                .id(item.getId())
                .menuName(item.getMenuName())
                .menuDescription(item.getMenuDescription())
                .price(item.getPrice())
                .build();
    }

}
