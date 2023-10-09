package com.zerobase.domain.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

// 유저에게서 가져올수 있는 것들
public class UserVo {

    private Long id;
    private String email;


}
