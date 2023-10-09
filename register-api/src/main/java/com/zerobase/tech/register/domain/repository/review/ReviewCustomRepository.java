package com.zerobase.tech.register.domain.repository.review;

public interface ReviewCustomRepository {

    Long searchByShopId(Long shopId);

    Integer searchStarsByShopId(Long shopId);

}
