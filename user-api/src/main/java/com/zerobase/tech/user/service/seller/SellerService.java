package com.zerobase.tech.user.service.seller;

import com.zerobase.tech.user.userDomain.SignUpForm;
import com.zerobase.tech.user.userDomain.model.Seller;
import com.zerobase.tech.user.userDomain.repository.SellerRepository;
import com.zerobase.tech.user.exception.CustomException;
import com.zerobase.tech.user.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerRepository sellerRepository;

    // 이메일과 패스워드로 상점(셀러) 이메일 인증여부 확인
    public Optional<Seller> findValidSeller(String email, String password){
        return sellerRepository.findByEmailAndPasswordAndVerify(email,password,"true");
    }

    // 상점(셀러) 회원가입
    public Seller signUp(SignUpForm form){
        return sellerRepository.save(Seller.from(form));
    }

    // 상점(셀러) 이메일 존재하는지 여부 확인(회원가입 절차중)
    public boolean isEmailExists(String email){
        return sellerRepository.findByEmail(email).isPresent();
    }

    // 이메일 인증
    @Transactional
    public void verifyEmail(String email , String code){
        Seller seller = sellerRepository.findByEmail(email)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_USER));

        if (seller.getVerify().equals("true")) {
            throw new CustomException(ErrorCode.ALREADY_VERIFY);
        } else if (!seller.getVerificationCode().equals(code)) {
            throw new CustomException(ErrorCode.WRONG_VERIFICATION);
        } else if (seller.getVerifyExpiredAt().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.EXPIRED_CODE);
        }
        seller.setVerify("true");
    }

    // 이메일 인증 코드 발송
    @Transactional
    public LocalDateTime changeSellerValidateEmail(Long sellerId, String verificationCode) {
        Optional<Seller> sellerOptional = sellerRepository.findById(sellerId);

        if (sellerOptional.isPresent()) {

            Seller seller = sellerOptional.get();
            seller.setVerificationCode(verificationCode);
            seller.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));
            return seller.getVerifyExpiredAt();
        }
        throw new CustomException(ErrorCode.NOT_FOUND_USER);

    }
}
