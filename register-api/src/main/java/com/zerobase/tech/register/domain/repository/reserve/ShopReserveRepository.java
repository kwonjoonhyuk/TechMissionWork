package com.zerobase.tech.register.domain.repository.reserve;

import com.zerobase.tech.register.domain.reserve.ShopReserve;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ShopReserveRepository extends JpaRepository<ShopReserve,Long>,ShopReserveCustomRepository {
    Optional<ShopReserve> findByCustomerIdAndId(Long customerId,Long id);

}
