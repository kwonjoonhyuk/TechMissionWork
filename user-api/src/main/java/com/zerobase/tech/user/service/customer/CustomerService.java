package com.zerobase.tech.user.service.customer;


import com.zerobase.tech.user.userDomain.model.Customer;
import com.zerobase.tech.user.userDomain.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    // 고객 아이디와 이메일로 정보 찾기
    public Optional<Customer> findByIdAndEmail(Long id, String email) {
        return customerRepository.findById(id).stream().filter(customer -> customer.getEmail().equals(email))
                .findFirst();
    }

    // 고객 이메일 패스워드로 인증회원인지 여부 확인
    public Optional<Customer> findValidCustomer(String email, String password) {
        return customerRepository.findByEmail(email)
                .stream().filter(
                        customer -> customer.getPassword().equals(password) && customer.getVerify().equals("true")
                ).findFirst();
    }


}
