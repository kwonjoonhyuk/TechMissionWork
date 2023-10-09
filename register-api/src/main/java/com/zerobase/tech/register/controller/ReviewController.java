package com.zerobase.tech.register.controller;

import com.zerobase.domain.config.JwtAuthenticationProvider;
import com.zerobase.tech.register.domain.reviewShop.AddReviewForm;
import com.zerobase.tech.register.domain.reviewShop.ReviewDto;
import com.zerobase.tech.register.domain.reviewShop.UpdateReviewForm;
import com.zerobase.tech.register.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final JwtAuthenticationProvider provider;

    // 리뷰 추가(고객)
    @PostMapping
    public ResponseEntity<String> addReview(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                            @RequestBody AddReviewForm form) {
        return ResponseEntity.ok(reviewService.createReview(provider.getUserVo(token).getId(), form));
    }

    // 리뷰 수정(고객)
    @PutMapping
    public ResponseEntity<String> updateReview(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                               @RequestBody UpdateReviewForm form) {
        return ResponseEntity.ok(reviewService.updateReview(provider.getUserVo(token).getId(), form));
    }

    // 리뷰 삭제(고객)
    @DeleteMapping
    public ResponseEntity<String> deleteReview(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                               @RequestParam Long id, Long reserveId) {
        return ResponseEntity.ok(reviewService.deleteReview(provider.getUserVo(token).getId(), id, reserveId));
    }

    // 자기가 쓴 리뷰 보기(고객)
    @GetMapping("/customer/search")
    public ResponseEntity<List<ReviewDto>> getReviewCustomer(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                             @RequestParam Long shopId) {
        return ResponseEntity.ok(
                reviewService.searchReviewCustomer(provider.getUserVo(token).getId())
                        .stream().map(ReviewDto::from).collect(Collectors.toList())
        );
    }

    // 상점 셀러가 등록한 상점에 등록된 리뷰 보기(셀러)
    @GetMapping("/seller/search")
    public ResponseEntity<List<ReviewDto>> getReviewSeller(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                             @RequestParam Long shopId) {
        return ResponseEntity.ok(
                reviewService.searchReviewSeller(provider.getUserVo(token).getId())
                        .stream().map(ReviewDto::from).collect(Collectors.toList())
        );
    }
}
