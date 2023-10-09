package com.zerobase.tech.register.domain.repository.shop;


import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import com.zerobase.tech.register.domain.shop.QShop;
import com.zerobase.tech.register.domain.shop.Shop;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ShopRepositoryImpl implements ShopCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;
    QShop shop = QShop.shop;

    @Override
    public List<Shop> searchByShopName() {
        return jpaQueryFactory.selectFrom(shop)
                .orderBy(shop.shopName.asc())
                .fetch();
    }

    @Override
    public List<Shop> getShopsByLatitudeAndLongitude(double longitude, double latitude) {


        // latitude를 radian 로 계산
        NumberExpression<Double> radiansLatitude =
                Expressions.numberTemplate(Double.class, "radians({0})", latitude);

        // 계산된 latitude -> 코사인 계산
        NumberExpression<Double> cosLatitude =
                Expressions.numberTemplate(Double.class, "cos({0})", radiansLatitude);
        NumberExpression<Double> cosSubwayLatitude =
                Expressions.numberTemplate(Double.class, "cos(radians({0}))", shop.latitude);

        // 계산된 latitude -> 사인 계산
        NumberExpression<Double> sinLatitude =
                Expressions.numberTemplate(Double.class, "sin({0})", radiansLatitude);
        NumberExpression<Double> sinSubWayLatitude =
                Expressions.numberTemplate(Double.class, "sin(radians({0}))", shop.latitude);

        // 사이 거리 계산
        NumberExpression<Double> cosLongitude =
                Expressions.numberTemplate(Double.class, "cos(radians({0}) - radians({1}))",
                        shop.longitude, longitude);

        NumberExpression<Double> acosExpression =
                Expressions.numberTemplate(Double.class, "acos({0})",
                        cosLatitude.multiply(cosSubwayLatitude).multiply(cosLongitude).add(sinLatitude.multiply(sinSubWayLatitude)));
        // 최종 계산
        NumberExpression<Double> distanceExpression =
                Expressions.numberTemplate(Double.class, "6371 * {0}", acosExpression);

        System.out.println("실행됨");

        return jpaQueryFactory.selectFrom(shop)
                .orderBy(distanceExpression.asc())
                .limit(10)
                .fetch();

    }

    @Override
    public Long getSellerIdById(Long id) {
        QShop shop = QShop.shop;
        return jpaQueryFactory.select(shop.sellerId)
                .from(shop)
                .where(shop.id.eq(id))
                .fetchFirst();
    }

    @Override
    public Long searchBySellerId(Long sellerId ,Long shopId) {
        QShop shop = QShop.shop;

        return jpaQueryFactory.select(shop.count())
                .from(shop)
                .where(shop.sellerId.eq(sellerId))
                .where(shop.id.eq(shopId))
                .fetchFirst();
    }

    @Override
    public void updateStarsById(Long shopId,double stars) {
        QShop shop = QShop.shop;

        jpaQueryFactory.update(shop)
                .set(shop.stars,stars)
                .where(shop.id.eq(shopId))
                .execute();

    }

    @Override
    public List<Shop> searchShop() {
        return jpaQueryFactory.selectFrom(shop)
                .orderBy(shop.stars.desc())
                .fetch();
    }
}
