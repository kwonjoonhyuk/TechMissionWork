package com.zerobase.tech.user.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    ALREADY_REGISTER_USER(HttpStatus.BAD_REQUEST,"이미 가입되어 있는 회원입니다."),
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST,"일치하는 회원이 없습니다."),
    EXPIRED_CODE(HttpStatus.BAD_REQUEST,"인증시간이 만료되었습니다."),
    LOGIN_FAIL(HttpStatus.BAD_REQUEST,"아이디나 패스워드를 확인해주세요."),
    ALREADY_VERIFY(HttpStatus.BAD_REQUEST,"이미 인증이 완료 되었습니다."),
    WRONG_VERIFICATION(HttpStatus.BAD_REQUEST,"잘못된 인증 시도입니다.");


    private final HttpStatus httpStatus;
    private final String detail;
}
