package com.zerobase.tech.user.service.customer;


import com.zerobase.tech.user.userDomain.SignUpForm;
import com.zerobase.tech.user.userDomain.model.Customer;
import com.zerobase.tech.user.userDomain.repository.CustomerRepository;
import com.zerobase.tech.user.exception.CustomException;
import com.zerobase.tech.user.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class SignUpCustomerService {

    private final CustomerRepository customerRepository;

    // 고객 회원가입
    public Customer signUp(SignUpForm signUpForm) {
        return customerRepository.save(Customer.from(signUpForm));
    }

    // 고객 회원가입 중에 이메일이 존재하는지 안하는지 여부확인
    public boolean isEmailExists(String email) {
        return customerRepository.findByEmail(email.toLowerCase(Locale.ROOT)).isPresent();
    }

    // 이메일 인증 절차 고객
    @Transactional
    public LocalDateTime changeCustomerValidateEmail(Long customerId, String verificationCode) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if (customerOptional.isPresent()) {

            Customer customer = customerOptional.get();
            customer.setVerificationCode(verificationCode);
            customer.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));
            return customer.getVerifyExpiredAt();
        }
        throw new CustomException(ErrorCode.NOT_FOUND_USER);

    }

    // 이메일 인증 코드 발송
    @Transactional
    public void verifyEmail(String email, String code) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        if (customer.getVerify().equals("true")) {
            throw new CustomException(ErrorCode.ALREADY_VERIFY);
        } else if (!customer.getVerificationCode().equals(code)) {
            throw new CustomException(ErrorCode.WRONG_VERIFICATION);
        } else if (customer.getVerifyExpiredAt().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.EXPIRED_CODE);
        }
        customer.setVerify("true");
    }

}
