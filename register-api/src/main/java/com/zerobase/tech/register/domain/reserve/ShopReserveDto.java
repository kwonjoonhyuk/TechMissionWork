package com.zerobase.tech.register.domain.reserve;


import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShopReserveDto {

    private Long id;
    private String shopName;
    private LocalDate reserveDate;
    private Integer hours;
    private Integer minutes;
    private String customerName;
    private String useShopVerify;
    private String message;

    public static ShopReserveDto from(ShopReserve shopReserve) {

        return ShopReserveDto.builder()
                .id(shopReserve.getId())
                .shopName(shopReserve.getShopName())
                .reserveDate(shopReserve.getReserveDate())
                .hours(shopReserve.getHours())
                .minutes(shopReserve.getMinutes())
                .customerName(shopReserve.getCustomerName())
                .message("예약이 확정되었습니다. 수정은 불가합니다. 일정이 바뀌었다면 취소하고 다시 예약해주세요")
                .build();
    }

    public static ShopReserveDto get(ShopReserve shopReserve){
        String useShopVerify;
        if (shopReserve.getUseShopVerify().equals("false")){
            useShopVerify = "예약중";
        }else {
            useShopVerify = "이용 완료";
        }
        return ShopReserveDto.builder()
                .shopName(shopReserve.getShopName())
                .customerName(shopReserve.getCustomerName())
                .reserveDate(shopReserve.getReserveDate())
                .hours(shopReserve.getHours())
                .minutes(shopReserve.getMinutes())
                .useShopVerify(useShopVerify)
                .build();
    }

    public static ShopReserveDto getSeller(ShopReserve shopReserve){
        String useShopVerify = "false";
        if (shopReserve.getUseShopVerify().equals("false")){
            useShopVerify = "예약중";
        }
        return ShopReserveDto.builder()
                .id(shopReserve.getId())
                .shopName(shopReserve.getShopName())
                .customerName(shopReserve.getCustomerName())
                .reserveDate(shopReserve.getReserveDate())
                .hours(shopReserve.getHours())
                .minutes(shopReserve.getMinutes())
                .useShopVerify(useShopVerify)
                .message("아직 예약중인 상태입니다.")
                .build();
    }
}
