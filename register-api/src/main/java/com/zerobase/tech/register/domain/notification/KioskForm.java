package com.zerobase.tech.register.domain.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KioskForm {
    String name;
    String phone;
    Long reserveNumber;
    Long shopId;
}
