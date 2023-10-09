package com.zerobase.tech.user.service.seller;

import com.zerobase.tech.user.domain.SignUpForm;
import com.zerobase.tech.user.domain.model.Seller;
import com.zerobase.tech.user.domain.repository.SellerRepository;
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

    public Optional<Seller> findByIdAndEmail(Long id, String email){
        return sellerRepository.findByIdAndEmail(id,email);
    }

    public Optional<Seller> findValidSeller(String email, String password){
        return sellerRepository.findByEmailAndPasswordAndVerify(email,password,"true");
    }

    public Seller signUp(SignUpForm form){
        return sellerRepository.save(Seller.from(form));
    }

    public boolean isEmailExists(String email){
        return sellerRepository.findByEmail(email).isPresent();
    }

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
