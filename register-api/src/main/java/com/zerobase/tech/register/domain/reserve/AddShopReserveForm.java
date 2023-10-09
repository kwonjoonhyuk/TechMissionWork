package com.zerobase.tech.register.domain.reserve;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddShopReserveForm {
    private String shopName;
    private Long shopId;
    private LocalDate reserveDate;
    private Integer hours;
    private Integer minutes;
    private String customerName;
    private String phone;
}
