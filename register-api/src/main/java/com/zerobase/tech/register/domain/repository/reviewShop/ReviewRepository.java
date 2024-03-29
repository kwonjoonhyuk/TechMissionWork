package com.zerobase.tech.register.domain.repository.reviewShop;

import com.zerobase.tech.register.domain.reviewShop.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewCustomRepository {

    Optional<Review> findByCustomerIdAndId(Long customerId, Long id);

    List<Review> findReviewByCustomerId(Long customerId);

    List<Review> findReviewBySellerId(Long sellerId);
}
