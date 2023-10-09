package com.zerobase.tech.register.domain.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

// 키오스크 예약 확인 입력 폼
public class KioskForm {
    String name;
    String phone;
    Long reserveNumber;
    Long shopId;
}
