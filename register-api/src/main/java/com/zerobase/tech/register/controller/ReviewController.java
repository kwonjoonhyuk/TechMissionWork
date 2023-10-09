package com.zerobase.tech.register.controller;

import com.zerobase.domain.config.JwtAuthenticationProvider;
import com.zerobase.tech.register.domain.review.AddReviewForm;
import com.zerobase.tech.register.domain.review.ReviewDto;
import com.zerobase.tech.register.domain.review.UpdateReviewForm;
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

    @PostMapping
    public ResponseEntity<String> addReview(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                            @RequestBody AddReviewForm form) {
        return ResponseEntity.ok(reviewService.createReview(provider.getUserVo(token).getId(), form));
    }

    @PutMapping
    public ResponseEntity<String> updateReview(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                               @RequestBody UpdateReviewForm form) {
        return ResponseEntity.ok(reviewService.updateReview(provider.getUserVo(token).getId(), form));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteReview(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                               @RequestParam Long id, Long reserveId) {
        return ResponseEntity.ok(reviewService.deleteReview(provider.getUserVo(token).getId(), id, reserveId));
    }

    @GetMapping("/customer/search")
    public ResponseEntity<List<ReviewDto>> getReviewCustomer(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                             @RequestParam Long shopId) {
        return ResponseEntity.ok(
                reviewService.searchReviewCustomer(provider.getUserVo(token).getId())
                        .stream().map(ReviewDto::from).collect(Collectors.toList())
        );
    }

    @GetMapping("/seller/search")
    public ResponseEntity<List<ReviewDto>> getReviewSeller(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                             @RequestParam Long shopId) {
        return ResponseEntity.ok(
                reviewService.searchReviewSeller(provider.getUserVo(token).getId())
                        .stream().map(ReviewDto::from).collect(Collectors.toList())
        );
    }
}
