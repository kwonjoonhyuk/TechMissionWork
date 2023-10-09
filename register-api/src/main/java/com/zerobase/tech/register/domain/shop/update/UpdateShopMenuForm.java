package com.zerobase.tech.register.domain.shop.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateShopMenuForm {
    private Long id;
    private Long shopId;
    private String menuName;
    private String menuDescription;
    private Integer price;
}
