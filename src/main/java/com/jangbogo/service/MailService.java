package com.jangbogo.service;

import com.jangbogo.payload.response.MailResponse;
import com.jangbogo.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    public void sendMail(MailResponse mailResponse, String type) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(mailResponse.getAddress()); // 메일 수신자
            mimeMessageHelper.setSubject(mailResponse.getTitle()); // 메일 제목
            mimeMessageHelper.setText(setContext(mailResponse.getMessage(), type), true); // 메일 본문 내용, HTML 여부
            mimeMessageHelper.setFrom("cart.jangbogo@gmail.com");
            mimeMessageHelper.setReplyTo("cart.jangbogo@gmail.com");
            javaMailSender.send(mimeMessage);

            log.info("Success");

        } catch (MessagingException e) {
            log.info("fail");
            throw new RuntimeException(e);
        }
    }

    // thymeleaf를 통한 html 적용
    public String setContext(String code, String type) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process(type, context);
    }

}
