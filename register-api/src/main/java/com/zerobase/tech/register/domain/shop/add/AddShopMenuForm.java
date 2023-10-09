package com.zerobase.tech.register.domain.shop.add;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddShopMenuForm {

    private Long shopId;
    private String menuName;
    private String menuDescription;
    private Integer price;
}
