package com.zerobase.tech.register.service;

import com.zerobase.tech.register.domain.repository.reserve.ShopReserveRepository;
import com.zerobase.tech.register.domain.repository.review.ReviewRepository;
import com.zerobase.tech.register.domain.repository.shop.ShopRepository;
import com.zerobase.tech.register.domain.review.AddReviewForm;
import com.zerobase.tech.register.domain.review.Review;
import com.zerobase.tech.register.domain.review.UpdateReviewForm;
import com.zerobase.tech.register.exception.CustomException;
import com.zerobase.tech.register.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ShopReserveRepository shopReserveRepository;
    private final ShopRepository shopRepository;

    // 리뷰 쓰기
    @Transactional
    public String createReview(Long customerId, AddReviewForm form) {

        // 1. 고객 로그인 한상태에서 reserveId 받아서 useShopVerify가 true이면 쓸수 있게 아니면 exception 발생
        String useShopVerify = shopReserveRepository.searchByUseShopVerify(customerId, form.getReserveId());
        String addReviewVerify = shopReserveRepository.searchByAddReviewVerify(customerId, form.getReserveId());
        Long sellerId = shopRepository.getSellerIdById(form.getShopId());

        // 이용하지 않은 예약건은 리뷰를 작성할 수 없도록 exception 발생
        if (useShopVerify.equals("false")) {
            throw new CustomException(ErrorCode.DO_NOT_CREATE_REVIEW_NOT_USE);
        }
        // 이미 리뷰가 쓰여 있으면 exception 발생
        if (addReviewVerify.equals("true")) {
            throw new CustomException(ErrorCode.DO_NOT_CREATE_REVIEW_ALREADY_CREATE);
        }
        // 별점을 0점보다 낮게 혹은 5점보다 높게 줄경우에는 exception 발생 정수값으로 입력하도록 함
        if (form.getStars() < 0 || form.getStars() > 5) {
            throw new CustomException(ErrorCode.DO_NOT_STARS_THAT_NUMBER);
        }
        reviewRepository.save(Review.of(customerId, sellerId, form));

        // 2. 리뷰 저장시 해당하는 shop의 stars 부분이 업데이트가 되도록 하고 addReviewVerify 부분도 true로 업데이트
        Long count = reviewRepository.searchByShopId(form.getShopId());
        Integer sum = reviewRepository.searchStarsByShopId(form.getShopId());
        double starsAverage = (double) sum / count;

        shopRepository.updateStarsById(form.getShopId(), starsAverage);
        shopReserveRepository.updateAddReviewVerifyById(form.getReserveId());


        return "리뷰가 입력되었습니다. (별점은 소수점을 제외한 숫자부분만 저장됩니다.)";
    }

    // 리뷰 수정
    @Transactional
    public String updateReview(Long customerId, UpdateReviewForm form) {
        Review review = reviewRepository.findByCustomerIdAndId(customerId, form.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REVIEW));

        review.setReviewComment(form.getReviewComment());
        review.setStars(form.getStars());

        Long count = reviewRepository.searchByShopId(review.getShopId());
        Integer sum = reviewRepository.searchStarsByShopId(review.getShopId());
        double starsAverage = (double) sum / count;

        shopRepository.updateStarsById(review.getShopId(), starsAverage);
        return "리뷰가 수정되었습니다.";
    }

    // 리뷰 보기(고객)
    public List<Review> searchReviewCustomer(Long customerId){
        return reviewRepository.findReviewByCustomerId(customerId);
    }

    // 리뷰 보기(사장)
    public List<Review> searchReviewSeller(Long sellerId){
        return reviewRepository.findReviewBySellerId(sellerId);
    }

    // 리뷰 삭제(고객)
    @Transactional
    public String deleteReview(Long customerId, Long reviewId ,Long reserveId) {
        Review review = reviewRepository.findByCustomerIdAndId(customerId, reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REVIEW));
        reviewRepository.delete(review);

        Long count = reviewRepository.searchByShopId(review.getShopId());
        System.out.println(count);
        Integer sum = reviewRepository.searchStarsByShopId(review.getShopId());
        double starsAverage;
        if (sum !=null){
            starsAverage = (double) sum / count;
        }else {
            starsAverage = 0;
        }

        shopRepository.updateStarsById(review.getShopId(), starsAverage);
        shopReserveRepository.updateReserveAddReviewVerifyById(reserveId);
        return "리뷰가 삭제되었습니다.";
    }
}
