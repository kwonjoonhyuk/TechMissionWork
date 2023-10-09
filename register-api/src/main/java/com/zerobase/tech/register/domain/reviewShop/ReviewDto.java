package com.zerobase.tech.register.domain.reviewShop;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private Long id;
    private String reviewComment;
    private Integer stars;

    public static ReviewDto from(Review review) {

        return ReviewDto.builder()
                .id(review.getId())
                .reviewComment(review.getReviewComment())
                .stars(review.getStars())
                .build();
    }
}
