package com.zerobase.tech.register.domain.shop.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateShopForm {

    private Long id;;
    private String shopName;
    private String shopDescription;
    private String address;
    private List<UpdateShopMenuForm> items;
}
