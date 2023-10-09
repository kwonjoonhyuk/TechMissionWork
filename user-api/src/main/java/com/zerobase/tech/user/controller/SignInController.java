package com.zerobase.tech.user.controller;


import com.zerobase.tech.user.application.SignInApplication;
import com.zerobase.tech.user.domain.SignInForm;
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

    @PostMapping("/customer")
    public ResponseEntity<String> signInCustomer(@RequestBody SignInForm form){
        return ResponseEntity.ok(sIgnInApplication.customerLoginToken(form));
    }

    @PostMapping("/seller")
    public ResponseEntity<String> signInSeller(@RequestBody SignInForm form){
        return ResponseEntity.ok(sIgnInApplication.sellerLoginToken(form));
    }

}
