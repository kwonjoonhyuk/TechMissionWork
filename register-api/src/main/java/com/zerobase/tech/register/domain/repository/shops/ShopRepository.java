package com.zerobase.tech.register.domain.repository.shops;

import com.zerobase.tech.register.domain.shops.Shop;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop,Long> , ShopCustomRepository {


    @EntityGraph(attributePaths = {"shopMenus"},type = EntityGraph.EntityGraphType.LOAD)
    Optional<Shop> findWithProductItemById(Long id);

    @EntityGraph(attributePaths = {"shopMenus"},type = EntityGraph.EntityGraphType.LOAD)
    Optional<Shop> findBySellerIdAndId(Long sellerId, Long id);

}
