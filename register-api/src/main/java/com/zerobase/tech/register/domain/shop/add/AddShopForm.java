package com.zerobase.tech.register.domain.shop.add;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddShopForm {

    private String shopName;
    private String shopDescription;
    private String address;
    private final List<AddShopMenuForm> items = new ArrayList<>();
}
