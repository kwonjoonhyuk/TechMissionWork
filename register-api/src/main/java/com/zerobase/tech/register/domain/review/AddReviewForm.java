package com.zerobase.tech.register.domain.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddReviewForm {

    Long shopId;
    Long reserveId;
    String reviewComment;

    @Range(min = 0 ,max = 5, message = "오른쪽: {min} ~ {max} 범위의 정수값을 입력해주세요")
    Integer stars;

}
