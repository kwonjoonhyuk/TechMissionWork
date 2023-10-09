package com.zerobase.tech.register.domain.review;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    Long shopId;
    Long customerId;
    Long sellerId;

    String reviewComment;
    Integer stars;

    public static Review of(Long customerId,Long sellerId,AddReviewForm form){
        return Review.builder()
                .customerId(customerId)
                .shopId(form.shopId)
                .sellerId(sellerId)
                .reviewComment(form.reviewComment)
                .stars(form.stars)
                .build();
    }
}
