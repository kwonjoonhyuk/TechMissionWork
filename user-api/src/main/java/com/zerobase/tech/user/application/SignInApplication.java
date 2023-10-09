package com.zerobase.tech.user.application;


import com.zerobase.domain.common.UserType;
import com.zerobase.domain.config.JwtAuthenticationProvider;
import com.zerobase.tech.user.userDomain.SignInForm;
import com.zerobase.tech.user.userDomain.model.Customer;
import com.zerobase.tech.user.userDomain.model.Seller;
import com.zerobase.tech.user.exception.CustomException;
import com.zerobase.tech.user.exception.ErrorCode;
import com.zerobase.tech.user.service.customer.CustomerService;
import com.zerobase.tech.user.service.seller.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInApplication {

    private final CustomerService customerService;
    private final SellerService sellerService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    // 고객 로그인 토큰 생성
    public String customerLoginToken(SignInForm form) {
        // 1. 로그인 가능 여부
        Customer customer = customerService.findValidCustomer(form.getEmail(), form.getPassword())
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_FAIL));

        // 2. 토큰 발행

        // 3. 토큰을 response 한다.
        return jwtAuthenticationProvider.createToken(customer.getEmail(), customer.getId(), UserType.CUSTOMER);
    }

    // 셀러 로그인 토큰 생성
    public String sellerLoginToken(SignInForm form) {
        // 1. 로그인 가능 여부
        Seller seller = sellerService.findValidSeller(form.getEmail(), form.getPassword())
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_FAIL));

        // 2. 토큰 발행

        // 3. 토큰을 response 한다.
        return jwtAuthenticationProvider.createToken(seller.getEmail(), seller.getId(), UserType.SELLER);
    }
}
