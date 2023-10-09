package com.zerobase.tech.register.domain.repository.reserve;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.tech.register.domain.reserve.QShopReserve;
import com.zerobase.tech.register.domain.reserve.ShopReserve;
import com.zerobase.tech.register.domain.shop.QShop;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ShopReserveRepositoryImpl implements ShopReserveCustomRepository {

    private final JPAQueryFactory query;

    @Override
    public Integer searchByShopIdAndReserveDateAndHoursAndMinutes(Long shopId, LocalDate reserveDate, Integer hours, Integer minutes) {

        QShopReserve shopReserve = QShopReserve.shopReserve;

        return query
                .selectFrom(shopReserve)
                .where(shopReserve.shopId.eq(shopId))
                .where(shopReserve.reserveDate.eq(reserveDate))
                .where(shopReserve.hours.eq(hours))
                .where(shopReserve.minutes.eq(minutes))
                .fetch().size();
    }

    @Override
    public List<ShopReserve> getShopReservesByCustomerId(Long customerId) {
        QShopReserve shopReserve = QShopReserve.shopReserve;

        return query
                .selectFrom(shopReserve)
                .where(shopReserve.customerId.eq(customerId))
                .orderBy(shopReserve.id.desc())
                .fetch();
    }

    @Override
    public Long searchCustomerIdByCustomerNameAndPhoneAndId(String customerName, String phone, Long id) {
        QShopReserve shopReserve = QShopReserve.shopReserve;

        return query.select(shopReserve.customerId)
                .from(shopReserve)
                .where(shopReserve.customerName.eq(customerName))
                .where(shopReserve.phone.eq(phone))
                .where(shopReserve.id.eq(id))
                .fetchFirst();
    }

    @Override
    public LocalDate searchReserveDateByCustomerNameAndPhoneAndId(String customerName, String phone, Long id) {
        QShopReserve shopReserve = QShopReserve.shopReserve;

        return query.select(shopReserve.reserveDate)
                .from(shopReserve)
                .where(shopReserve.customerName.eq(customerName))
                .where(shopReserve.phone.eq(phone))
                .where(shopReserve.id.eq(id))
                .fetchFirst();
    }

    @Override
    public Integer searchHoursByCustomerNameAndPhoneAndId(String customerName, String phone, Long id) {
        QShopReserve shopReserve = QShopReserve.shopReserve;

        return query.select(shopReserve.hours)
                .from(shopReserve)
                .where(shopReserve.customerName.eq(customerName))
                .where(shopReserve.phone.eq(phone))
                .where(shopReserve.id.eq(id))
                .fetchFirst();
    }

    @Override
    public Integer searchMinutesByCustomerNameAndPhoneAndId(String customerName, String phone, Long id) {
        QShopReserve shopReserve = QShopReserve.shopReserve;

        return query.select(shopReserve.minutes)
                .from(shopReserve)
                .where(shopReserve.customerName.eq(customerName))
                .where(shopReserve.phone.eq(phone))
                .where(shopReserve.id.eq(id))
                .fetchFirst();
    }

    @Override
    public void updateUseShopVerifyById(Long id) {
        QShopReserve shopReserve = QShopReserve.shopReserve;
        query.update(shopReserve)
                .set(shopReserve.useShopVerify, "true")
                .where(shopReserve.id.eq(id))
                .execute();
    }

    @Override
    public String getUseShopVerifyById(Long id) {
        QShopReserve shopReserve = QShopReserve.shopReserve;

        return query.select(shopReserve.useShopVerify)
                .from(shopReserve)
                .where(shopReserve.id.eq(id))
                .fetchFirst();
    }

    @Override
    public List<ShopReserve> getShopReserve(Long shopId) {
        QShopReserve shopReserve = QShopReserve.shopReserve;

        return query.selectFrom(shopReserve)
                .where(shopReserve.shopId.eq(shopId))
                .where(shopReserve.useShopVerify.eq("false"))
                .fetch();
    }

    @Override
    public String searchByUseShopVerify(Long customerId ,Long reserveId) {
        QShopReserve shopReserve = QShopReserve.shopReserve;

        return query.select(shopReserve.useShopVerify)
                .from(shopReserve)
                .where(shopReserve.customerId.eq(customerId))
                .where(shopReserve.id.eq(reserveId))
                .fetchFirst();

    }

    @Override
    public String searchByAddReviewVerify(Long customerId, Long reserveId) {
        QShopReserve shopReserve = QShopReserve.shopReserve;

        return query.select(shopReserve.addReviewVerify)
                .from(shopReserve)
                .where(shopReserve.customerId.eq(customerId))
                .where(shopReserve.id.eq(reserveId))
                .fetchFirst();

    }

    @Override
    public void updateAddReviewVerifyById(Long id) {
        QShopReserve shopReserve = QShopReserve.shopReserve;
        query.update(shopReserve)
                .set(shopReserve.addReviewVerify, "true")
                .where(shopReserve.id.eq(id))
                .execute();
    }

    @Override
    public void updateReserveAddReviewVerifyById(Long id) {
        QShopReserve shopReserve = QShopReserve.shopReserve;
        query.update(shopReserve)
                .set(shopReserve.addReviewVerify, "false")
                .where(shopReserve.id.eq(id))
                .execute();
    }


}
