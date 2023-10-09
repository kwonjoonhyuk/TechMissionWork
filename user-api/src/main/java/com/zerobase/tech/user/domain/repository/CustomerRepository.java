package com.zerobase.tech.user.domain.repository;


import com.zerobase.tech.user.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);
    Integer findIdByNameAndPhone(String name,String phone);


}
