package com.zerobase.tech.register.domain.repository.reviewShop;

public interface ReviewCustomRepository {

    Long searchByShopId(Long shopId);

    Integer searchStarsByShopId(Long shopId);

}
