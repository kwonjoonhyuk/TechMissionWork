package com.zerobase.tech.register.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    NOT_FOUND_SHOP(HttpStatus.BAD_REQUEST,"매장을 찾을 수 없습니다."),
    NOT_FOUND_REVIEW(HttpStatus.BAD_REQUEST,"해당 리뷰를 찾을 수 없습니다."),
    NOT_INPUT_ADDRESS(HttpStatus.BAD_REQUEST,"주소가 입력되지 않았습니다."),
    NOT_FOUND_ADDRESS(HttpStatus.BAD_REQUEST,"주소를 찾을수 없습니다 다시 확인해주세요."),
    SAME_MENU_NAME(HttpStatus.BAD_REQUEST,"동일한 메뉴가 존재합니다."),
    NOT_FOUND_RESERVATION(HttpStatus.BAD_REQUEST,"예약 내역을 찾을 수 없습니다. 다시 확인해주세요"),
    NOT_EQUALS_DATE(HttpStatus.BAD_REQUEST,"예약 날짜와 다릅니다."),
    NOT_EQUALS_HOURS(HttpStatus.BAD_REQUEST,"예약 시간보다 늦습니다."),
    NOT_EQUALS_MINUTES(HttpStatus.BAD_REQUEST,"예약 시간 10분전을 초과하셨습니다."),
    DO_NOT_CREATE_REVIEW_NOT_USE(HttpStatus.BAD_REQUEST,"이용하지 않은 예약건은 리뷰를 작성하실 수 없습니다."),
    DO_NOT_CREATE_REVIEW_ALREADY_CREATE(HttpStatus.BAD_REQUEST,"이미 리뷰를 작성하신 건은 더이상 예약 리뷰를 작성하실 수 없습니다."),
    DO_NOT_STARS_THAT_NUMBER(HttpStatus.BAD_REQUEST,"0점보다 낮게 혹은 5점보다 높게 별점을 줄수 없습니다. 정확하게 0~5사이에 숫자(소수점 제외)를 입력해주세요"),
    NOT_FOUND_MENU(HttpStatus.BAD_REQUEST,"메뉴를 찾을 수 없습니다.");
    private final HttpStatus httpStatus;
    private final String detail;

}
