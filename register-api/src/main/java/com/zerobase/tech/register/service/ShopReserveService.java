package com.zerobase.tech.register.service;

import com.zerobase.tech.register.domain.repository.reserveShop.ShopReserveRepository;
import com.zerobase.tech.register.domain.repository.shops.ShopRepository;
import com.zerobase.tech.register.domain.reserveShop.AddShopReserveForm;
import com.zerobase.tech.register.domain.reserveShop.ShopReserve;
import com.zerobase.tech.register.exceptions.CustomException;
import com.zerobase.tech.register.exceptions.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ShopReserveService {

    private final ShopReserveRepository shopReserveRepository;
    private final ShopRepository shopRepository;

    // 상점을 예약하기(고객)
    @Transactional
    public ShopReserve reserveShop(Long customerId, AddShopReserveForm form) {
        return shopReserveRepository.save(ShopReserve.of(customerId, form));
    }

    // 상점 예약 가능 여부 확인(고객)
    public String searchByShopIdAndReserveDateAndHoursAndMinutes(Long shopId, LocalDate reserveDate, Integer hours, Integer minutes) {
        if (shopReserveRepository.searchByShopIdAndReserveDateAndHoursAndMinutes(shopId, reserveDate, hours, minutes) >= 5) {
            return "해당 상점의 조회하신 날짜 시간에는 예약이 불가능합니다.";
        } else {
            return "해당 상점의 조회하신 날짜 시간에는 예약 가능합니다.";
        }
    }

    // 예약내역 확인(예약후 이용했는지 안했는지 표시) (고객)
    public List<ShopReserve> getShopReservesByCustomerId(Long customerId) {
        return shopReserveRepository.getShopReservesByCustomerId(customerId);
    }

    //예약 취소(고객)
    @Transactional
    public String deleteShopReserve(Long customerId, Long shopReserveId) {
        ShopReserve shopReserve = shopReserveRepository.findByCustomerIdAndId(customerId, shopReserveId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_RESERVATION));

        shopReserveRepository.delete(shopReserve);
        return "선택하신 예약이 취소되었습니다.";
    }

    // 예약 내역 확인 (상점 셀러)
    public List<ShopReserve> getShopReserve(Long sellerId, Long shopId) {

        // 1. 상점아이디에 sellerId가 맞는지 확인 절차
        Long count = shopRepository.searchBySellerId(sellerId, shopId);

        // 2. 맞으면 상점아이디에 맞는 내역 전부 불러오기
        if (count == 1){
            return shopReserveRepository.getShopReserve(shopId);
        }
        return null;
    }

}
