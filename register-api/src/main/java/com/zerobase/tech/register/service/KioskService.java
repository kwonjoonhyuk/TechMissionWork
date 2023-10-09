package com.zerobase.tech.register.service;

import com.zerobase.tech.register.domain.notification.Notify;
import com.zerobase.tech.register.domain.repository.notifications.NotifyRepository;
import com.zerobase.tech.register.domain.repository.reserveShop.ShopReserveRepository;
import com.zerobase.tech.register.domain.repository.shops.ShopRepository;

import com.zerobase.tech.register.exceptions.CustomException;
import com.zerobase.tech.register.exceptions.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class KioskService {

    private final NotifyRepository notifyRepository;
    private final ShopReserveRepository shopReserveRepository;
    private final NotificationService notificationService;
    private final ShopRepository shopRepository;


    // 키오스크  예약 확인시 알림 DB에 저장 및 상점 셀러에게 알림이 감
    @Transactional
    public String kioskNotificationCreate(String name, String phone, Long reserveNumber, Long shopId) {
        // 1. 고객 이름과 핸드폰번호와 접수번호로 고객 아이디 가져오기
        Long customerId = shopReserveRepository.searchCustomerIdByCustomerNameAndPhoneAndId(name, phone, reserveNumber);

        System.out.println("customerId" + customerId);

        // 2. 유저 이름과 핸드폰번호와 접수번호로 예약 날짜 시간 가져오기
        String date = String.valueOf(shopReserveRepository.searchReserveDateByCustomerNameAndPhoneAndId(name, phone, reserveNumber));
        String hours = String.valueOf(shopReserveRepository.searchHoursByCustomerNameAndPhoneAndId(name, phone, reserveNumber));
        String minutes = String.valueOf(shopReserveRepository.searchMinutesByCustomerNameAndPhoneAndId(name, phone, reserveNumber));

        // 현재 시간 날짜 분 비교하여 10분전까지만 확인되게끔 함
        LocalDate getDate = LocalDate.parse(date);

        if (!LocalDate.now().equals(getDate)) {
            // 예약날짜랑 맞지않는 경우
            throw new CustomException(ErrorCode.NOT_EQUALS_DATE);
        } else if (LocalDate.now().equals(getDate) && minutes.equals("30") && LocalDateTime.now().getHour() > Integer.parseInt(hours)) {

            // 예약시간보다 늦은 경우
            throw new CustomException(ErrorCode.NOT_EQUALS_HOURS);

        } else if (LocalDate.now().equals(getDate) && minutes.equals("0") && LocalDateTime.now().getMinute() > 0
                && LocalDateTime.now().getHour() >= Integer.parseInt(hours)) {

            // 예약시간보다 늦은 경우
            throw new CustomException(ErrorCode.NOT_EQUALS_HOURS);

        } else if (LocalDate.now().equals(getDate) && minutes.equals("30") && LocalDateTime.now().getHour() == Integer.parseInt(hours) &&
                Integer.parseInt(minutes) - LocalDateTime.now().getMinute() < 10) {

            // 예약 10분 전시간보다(분) 늦는경우 30분에 예약되어있는경우
            throw new CustomException(ErrorCode.NOT_EQUALS_MINUTES);

        } else if (LocalDate.now().equals(getDate) && minutes.equals("0") && Integer.parseInt(hours) - LocalDateTime.now().getHour() == 1
                && Integer.parseInt(minutes) - LocalDateTime.now().getMinute() < -50) {

            // 예약 10분 전시간보다(분) 늦는경우 00분에 예약되어있는경우
            throw new CustomException(ErrorCode.NOT_EQUALS_MINUTES);

        } else if (LocalDate.now().equals(getDate) && minutes.equals("0") && Integer.parseInt(hours) - LocalDateTime.now().getHour() < 1) {

            // 00분에 예약되어 있는경우 시간을 초과하는 경우
            throw new CustomException(ErrorCode.NOT_EQUALS_MINUTES);

        } else {
            // 3. 예약목록이 true가 아니라면 접수된 예약 verifyuse true로 업데이트 하기
            if (shopReserveRepository.getUseShopVerifyById(reserveNumber).equals("false")) {
                shopReserveRepository.updateUseShopVerifyById(reserveNumber);
                // 4. 상점 아이디로 seller의 아이디 가져와서 알림보내고 노티 디비에 저장
                Long sellerId = shopRepository.getSellerIdById(shopId);
                String content = name + " is " + date + " " + hours + "hour " + minutes + "minutes verify on Kiosk!!";
                notificationService.send(sellerId, shopId, content);

                Notify notify = Notify.builder()
                        .sellerId(sellerId)
                        .shopId(shopId)
                        .reserveNumber(reserveNumber)
                        .customerId(customerId)
                        .content(content)
                        .isRead("false")
                        .build();
                notifyRepository.save(notify);
                return "예약 확인 완료";
            }
            return "이미 예약 확인 완료되었습니다.";
        }
    }

}
