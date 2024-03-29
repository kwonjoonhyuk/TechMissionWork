package com.zerobase.tech.user.userDomain.repository;


import com.zerobase.tech.user.userDomain.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    Optional<Seller> findByIdAndEmail(Long id, String email);

    Optional<Seller> findByEmailAndPasswordAndVerify(String email, String password, String verify);

    Optional<Seller> findByEmail(String email);
}
