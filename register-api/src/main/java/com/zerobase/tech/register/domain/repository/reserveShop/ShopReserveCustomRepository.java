package com.zerobase.tech.register.domain.repository.reserveShop;


import com.zerobase.tech.register.domain.reserveShop.ShopReserve;


import java.time.LocalDate;
import java.util.List;

public interface ShopReserveCustomRepository {

    Integer searchByShopIdAndReserveDateAndHoursAndMinutes(Long shopId, LocalDate reserveDate, Integer hours, Integer minutes);

    List<ShopReserve> getShopReservesByCustomerId(Long customerId);

    Long searchCustomerIdByCustomerNameAndPhoneAndId(String customerName, String phone, Long id);

    LocalDate searchReserveDateByCustomerNameAndPhoneAndId(String customerName, String phone, Long id);

    Integer searchHoursByCustomerNameAndPhoneAndId(String customerName, String phone, Long id);

    Integer searchMinutesByCustomerNameAndPhoneAndId(String customerName, String phone, Long id);

    void updateUseShopVerifyById(Long id);

    String getUseShopVerifyById(Long id);

    List<ShopReserve> getShopReserve(Long shopId);

    String searchByUseShopVerify(Long customerId, Long reserveId);

    String searchByAddReviewVerify(Long customerId, Long reserveId);

    void updateAddReviewVerifyById(Long id);

    void updateReserveAddReviewVerifyById(Long id);


}
