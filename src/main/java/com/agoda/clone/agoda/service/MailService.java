package com.agoda.clone.agoda.service;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
class MailService {

    private JavaMailSender mailSender;

    @Async
    void sendMail(String Recipient, String Subject, String Body) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("cloneagoda@email.com");
            messageHelper.setTo(Recipient);
            messageHelper.setSubject(Subject);
            messageHelper.setText(Body);
        };
        try {
            mailSender.send(messagePreparator);
            log.info("Activation email sent!!");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            log.info("Exception occurred when sending mail to " + Recipient, e);
        }
    }

}