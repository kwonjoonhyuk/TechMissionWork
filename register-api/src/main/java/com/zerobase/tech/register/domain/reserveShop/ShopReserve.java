package com.zerobase.tech.register.domain.reserveShop;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;


import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopReserve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String shopName;
    private Long shopId;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate reserveDate;

    private Integer hours;
    private Integer minutes;

    private Long customerId;
    private String customerName;
    private String phone;

    private String useShopVerify = "false";
    private String reserveVerify = "true";
    private String addReviewVerify = "true";

    // ShopReserveService에서 사용하는 등록 함수
    public static ShopReserve of(Long customerId, AddShopReserveForm form) {
        return ShopReserve.builder()
                .shopId(form.getShopId())
                .shopName(form.getShopName())
                .customerId(customerId)
                .reserveDate(form.getReserveDate())
                .hours(form.getHours())
                .minutes(form.getMinutes())
                .customerName(form.getCustomerName())
                .phone(form.getPhone())
                .useShopVerify("false")
                .reserveVerify("true")
                .addReviewVerify("false")
                .build();
    }


}
