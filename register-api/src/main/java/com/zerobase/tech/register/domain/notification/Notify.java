package com.zerobase.tech.register.domain.notification;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notify {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private Long sellerId;
    private Long shopId;
    private Long reserveNumber;
    private Long customerId;

    private String content;
    private String isRead;




}
