package com.zerobase.tech.register.domain.reviewShop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateReviewForm {

    private Long id;
    private String reviewComment;
    private Integer stars;
}
