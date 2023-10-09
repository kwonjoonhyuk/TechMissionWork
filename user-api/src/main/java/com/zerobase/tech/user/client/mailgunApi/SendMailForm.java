package com.zerobase.tech.user.client.mailgunApi;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Data
// 메일건 api 입력폼
public class SendMailForm {
    private String from;
    private String to;
    private String subject;
    private String text;

}
