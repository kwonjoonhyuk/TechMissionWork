package com.zerobase.tech.user.application;


import com.zerobase.tech.user.client.MailgunClient;
import com.zerobase.tech.user.client.mailgunApi.SendMailForm;
import com.zerobase.tech.user.domain.SignUpForm;
import com.zerobase.tech.user.domain.model.Customer;
import com.zerobase.tech.user.domain.model.Seller;
import com.zerobase.tech.user.exception.CustomException;
import com.zerobase.tech.user.exception.ErrorCode;
import com.zerobase.tech.user.service.customer.SignUpCustomerService;
import com.zerobase.tech.user.service.seller.SellerService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SignUpApplication {

    private final MailgunClient mailgunClient;
    private final SignUpCustomerService signUpCustomerService;
    private final SellerService sellerService;

    // 고객 이메일 코드 인증
    public void customerVerify(String email, String code){
        signUpCustomerService.verifyEmail(email,code);
    }

    // 상점(셀러) 이메일 코드 인증
    public void sellerVerify(String email, String code){
        sellerService.verifyEmail(email,code);
    }

    // 고객 회원가입 진행
    public String customerSignUp(SignUpForm form) {
        if (signUpCustomerService.isEmailExists(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
        } else {
            Customer customer = signUpCustomerService.signUp(form);

            String code = getRandomCode();
            SendMailForm sendMailForm = SendMailForm.builder()
                    .from("tester@joonhyuktester.com")
                    .to(form.getEmail())
                    .subject("Verification Email!!")
                    .text(getVerificationBody(customer.getEmail(), customer.getName(),"customer",code))
                    .build();
            mailgunClient.sendEmail(sendMailForm);
            signUpCustomerService.changeCustomerValidateEmail(customer.getId(),code);
            return "회원가입에 성공하였습니다.";
        }

    }

    // 상점(셀러) 회원가입 진행
    public String sellerSignUp(SignUpForm form){
        if (sellerService.isEmailExists(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
        } else {
            Seller seller = sellerService.signUp(form);

            String code = getRandomCode();
            SendMailForm sendMailForm = SendMailForm.builder()
                    .from("tester@joonhyuktester.com")
                    .to(form.getEmail())
                    .subject("Verification Email!!")
                    .text(getVerificationBody(seller.getEmail(), seller.getName(),"seller",code))
                    .build();
            mailgunClient.sendEmail(sendMailForm);
            sellerService.changeSellerValidateEmail(seller.getId(),code);
            return "회원가입에 성공하였습니다.";
        }
    }



    // 이메일 인증을 위한 10자리의 랜덤 코드 생성
    private String getRandomCode() {
        return RandomStringUtils.random(10, true, true);
    }

    // 이메일 인증을 위한 url 생성
    private String getVerificationBody(String email, String name,String type ,String code) {
        StringBuilder builder = new StringBuilder();
        return builder.append("Hello").append(name).append("! Please Click Link for Verification.\n")
                .append("http://localhost:8081/signup/"+type+"/verify/?email=")
                .append(email)
                .append("&code=")
                .append(code)
                .toString();
    }

}
