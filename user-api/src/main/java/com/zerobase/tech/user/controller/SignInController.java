package com.zerobase.tech.user.controller;


import com.zerobase.tech.user.application.SignInApplication;
import com.zerobase.tech.user.userDomain.SignInForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signIn")
@RequiredArgsConstructor
public class SignInController {

    private final SignInApplication sIgnInApplication;

    // 고객 로그인
    @PostMapping("/customer")
    public ResponseEntity<String> signInCustomer(@RequestBody SignInForm form){
        return ResponseEntity.ok(sIgnInApplication.customerLoginToken(form));
    }

    // 상점(셀러) 로그인
    @PostMapping("/seller")
    public ResponseEntity<String> signInSeller(@RequestBody SignInForm form){
        return ResponseEntity.ok(sIgnInApplication.sellerLoginToken(form));
    }

}
