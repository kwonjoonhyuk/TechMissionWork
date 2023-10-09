package com.zerobase.tech.user.client;


import com.zerobase.tech.user.client.mailgunApi.SendMailForm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name = "mailgun" , url = "https://api.mailgun.net/v3/")
@Qualifier("mailgun")
public interface MailgunClient {

    @PostMapping("sandboxd3dad8ba4f7e45bfaf1a6d5b2034e9a7.mailgun.org/messages")
    ResponseEntity<String> sendEmail(@SpringQueryMap SendMailForm form);
}
