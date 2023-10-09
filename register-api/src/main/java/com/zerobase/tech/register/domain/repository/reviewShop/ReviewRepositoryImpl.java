package com.zerobase.tech.register.domain.repository.reviewShop;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.tech.register.domain.reviewShop.QReview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewCustomRepository{

    private final JPAQueryFactory query;

    @Override
    public Long searchByShopId(Long shopId) {
        QReview review = QReview.review;

        return query.select(review.count())
                .from(review)
                .where(review.shopId.eq(shopId))
                .fetchOne();
    }

    @Override
    public Integer searchStarsByShopId(Long shopId) {
        QReview review = QReview.review;

        return query.select(review.stars.sum())
                .from(review)
                .where(review.shopId.eq(shopId))
                .fetchFirst();
    }
}
